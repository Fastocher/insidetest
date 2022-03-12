package com.example.demo.user.api.service;

import com.example.demo.user.api.models.*;
import com.example.demo.user.impl.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    public LoginDto loginUser(JwtRequest authenticationRequest);
    public RegisterDto registerUser(NewUserDto newUserDto);
    public void doMessage(MessageDto messageDto);
    public List<String> printMessages(MessageDto messageDto);
}
