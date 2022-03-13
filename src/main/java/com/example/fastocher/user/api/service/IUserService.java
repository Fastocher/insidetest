package com.example.fastocher.user.api.service;

import com.example.fastocher.user.api.models.*;

import java.util.List;

public interface IUserService {
    public LoginDto loginUser(JwtRequest authenticationRequest);
    public RegisterDto registerUser(NewUserDto newUserDto);
    public void doMessage(MessageDto messageDto);
    public List<String> printMessages(MessageDto messageDto);
}
