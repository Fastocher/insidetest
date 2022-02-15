package com.example.demo.user.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDto {

    private String fullname;

    private String password;

    private String email;

    private LocalDate birthday;

    private String number;

    private String photo;
}
