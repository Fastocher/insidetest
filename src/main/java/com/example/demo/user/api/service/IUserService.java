package com.example.demo.user.api.service;

import com.example.demo.user.api.models.JwtRequest;
import com.example.demo.user.api.models.LoginDto;
import com.example.demo.user.api.models.NewUserDto;
import com.example.demo.user.api.models.RegisterDto;
import com.example.demo.user.impl.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    public Optional<UserEntity> getUser(Long id);
    public List<UserEntity> getUserByName(String name);
    public List<UserEntity> getUserByBirthday(String date);
    public UserEntity getUserBytel (String number);
    public void setNewUser (NewUserDto newUserDto);
    public void deleteUser(Long id);
    public LoginDto loginUser(JwtRequest authenticationRequest);
    public RegisterDto registerUser(NewUserDto newUserDto);
}
