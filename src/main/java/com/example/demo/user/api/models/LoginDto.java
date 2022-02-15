package com.example.demo.user.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {

    private String login;

    private String token;
}
