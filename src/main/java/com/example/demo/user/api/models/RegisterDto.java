package com.example.demo.user.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterDto {

    private String login;
    private String token;


}
