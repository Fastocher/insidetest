package com.example.demo.user.impl.service;

import com.example.demo.JWTConfig.impl.CustomUserDetails;
import com.example.demo.JWTConfig.impl.JwtRequestFilter;
import com.example.demo.JWTConfig.impl.JwtTokenUtil;
import com.example.demo.JWTConfig.service.JwtUserDetailsService;
import com.example.demo.user.api.exceptions.InvalidCredentials;
import com.example.demo.user.api.models.*;
import com.example.demo.user.api.service.IUserService;
import com.example.demo.user.impl.entity.UserEntity;
import com.example.demo.user.impl.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bcryptEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;



    public UserService(UserRepository userRepository,
                       PasswordEncoder bcryptEncoder,
                       AuthenticationManager authenticationManager,
                       JwtTokenUtil jwtTokenUtil,
                       JwtUserDetailsService jwtUserDetailsService,
                       JwtRequestFilter jwtRequestFilter) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }


    //получение пользователя по ID
    @Override
    public Optional<UserEntity> getUser(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity;
    }

    //получение пользователя по имени
    public List<UserEntity> getUserByName(String name){
        ArrayList<UserEntity> userEntities = (ArrayList<UserEntity>) userRepository.findAllByFullnameContaining(name);
        return userEntities;
    }

    //получение пользователя дате рождения
    public List<UserEntity> getUserByBirthday(String date){

        //получаем дату в виде строки
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US); //устанавливаем маску
        LocalDate bday = LocalDate.parse(date); //парсим - на выходе localdate

        ArrayList<UserEntity> userEntities = (ArrayList<UserEntity>) userRepository.findAllByBirthday(bday);
        return userEntities;
    }

    //получаение пользователя по номеру телефона
    public UserEntity getUserBytel (String number){
        UserEntity userEntity = userRepository.findByNumberContaining(number.substring(1));
        return userEntity;
    }

    //Создание нового пользователя
    public void setNewUser (NewUserDto newUserDto){
        UserEntity userEntity = new UserEntity();
        userEntity.setFullname(newUserDto.getFullname());
        userEntity.setBirthday(newUserDto.getBirthday());
        userEntity.setNumber(newUserDto.getNumber());
        userEntity.setPhoto(newUserDto.getPhoto());
        userRepository.save(userEntity);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public RegisterDto registerUser(NewUserDto newUserDto) {
        UserEntity newUser = new UserEntity();
        newUser.setFullname(newUserDto.getFullname());
        newUser.setNumber(newUserDto.getNumber());
        newUser.setPhoto(newUserDto.getPhoto());
        newUser.setBirthday(newUserDto.getBirthday());
        newUser.setEmail(newUserDto.getEmail());
        newUser.setPassword(bcryptEncoder.encode(newUserDto.getPassword()));

        userRepository.save(newUser);

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setLogin(newUserDto.getFullname());
        jwtRequest.setPassword(newUserDto.getPassword());

        RegisterDto registerDto = new RegisterDto();
        registerDto.setLogin(newUserDto.getFullname());
        registerDto.setToken(loginUser(jwtRequest).getToken());
        return registerDto;

    }

    public LoginDto loginUser(JwtRequest authenticationRequest) {

        authenticate(authenticationRequest.getLogin(), authenticationRequest.getPassword());


        final CustomUserDetails customUserDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getLogin());
        final String token = jwtTokenUtil.generateToken(customUserDetails);

        LoginDto loginDto = new LoginDto();
        loginDto.setToken(new JwtResponse(token).getToken());
        loginDto.setLogin(customUserDetails.getLogin());

        return loginDto;
    }

    private void authenticate(String login, String password) throws InvalidCredentials {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        } catch (BadCredentialsException e) {
            throw new InvalidCredentials();
        }
    }

}
