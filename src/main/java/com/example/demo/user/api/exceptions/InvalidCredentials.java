package com.example.demo.user.api.exceptions;

public class InvalidCredentials extends RuntimeException{
    public InvalidCredentials() {
        super("Invalid login or password");
    }
}
