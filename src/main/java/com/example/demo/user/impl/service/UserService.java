package com.example.demo.user.impl.service;

import com.example.demo.JWTConfig.impl.CustomUserDetails;
import com.example.demo.JWTConfig.impl.JwtRequestFilter;
import com.example.demo.JWTConfig.impl.JwtTokenUtil;
import com.example.demo.JWTConfig.service.JwtUserDetailsService;
import com.example.demo.user.api.exceptions.InvalidCredentials;
import com.example.demo.user.api.models.*;
import com.example.demo.user.api.service.IUserService;
import com.example.demo.user.impl.entity.MsgEntity;
import com.example.demo.user.impl.entity.UserEntity;
import com.example.demo.user.impl.repository.MsgRepository;
import com.example.demo.user.impl.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bcryptEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final MsgRepository msgRepository;



    public UserService(UserRepository userRepository,
                       PasswordEncoder bcryptEncoder,
                       AuthenticationManager authenticationManager,
                       JwtTokenUtil jwtTokenUtil,
                       JwtUserDetailsService jwtUserDetailsService,
                       MsgRepository msgRepository) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.msgRepository = msgRepository;
    }


    //Создание нового пользователя
    public void setNewUser (NewUserDto newUserDto){
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(bcryptEncoder.encode(newUserDto.getPassword()));
        userRepository.save(userEntity);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public RegisterDto registerUser(NewUserDto newUserDto) {
        UserEntity newUser = new UserEntity();
        newUser.setName(newUserDto.getName());
        newUser.setPassword(bcryptEncoder.encode(newUserDto.getPassword()));

        userRepository.save(newUser);

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setLogin(newUserDto.getName());
        jwtRequest.setPassword(newUserDto.getPassword());

        RegisterDto registerDto = new RegisterDto();
        registerDto.setName(newUserDto.getName());
        registerDto.setToken(loginUser(jwtRequest).getToken());
        return registerDto;

    }

    public LoginDto loginUser(JwtRequest authenticationRequest) {

        authenticate(authenticationRequest.getLogin(), authenticationRequest.getPassword());


        final CustomUserDetails customUserDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getLogin());
        final String token = jwtTokenUtil.generateToken(customUserDetails);

        LoginDto loginDto = new LoginDto();
        loginDto.setToken(new JwtResponse(token).getToken());
        loginDto.setName(customUserDetails.getLogin());

        return loginDto;
    }

    private void authenticate(String login, String password) throws InvalidCredentials {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        } catch (BadCredentialsException e) {
            throw new InvalidCredentials();
        }
    }

    public void doMessage(MessageDto messageDto)
    {
            MsgEntity msgEntity = new MsgEntity();
            msgEntity.setMessage(messageDto.getMessage());
            msgEntity.setUser(userRepository.findByName(messageDto.getName()));
            msgRepository.save(msgEntity);
    }

    public List<String> printMessages(MessageDto messageDto){

        UserEntity user = userRepository.findByName(messageDto.getName());
        int count = Integer.parseInt(messageDto.getMessage().substring(8));

        LinkedList<String> messageList = new LinkedList<>();
        for(int i = 0; (i < count) && (i < user.getMessages().size()); i++){
                messageList.add(user.getMessages().get(i).getMessage());

        }
        return messageList;
    }

}
