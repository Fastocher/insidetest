package com.example.demo.user.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterDto {

    private String name;
    private String token;


}
