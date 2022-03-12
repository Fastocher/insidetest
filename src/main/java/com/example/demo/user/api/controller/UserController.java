package com.example.demo.user.api.controller;

import com.example.demo.user.api.models.JwtRequest;
import com.example.demo.user.api.models.MessageDto;
import com.example.demo.user.api.models.NewUserDto;
import com.example.demo.user.api.service.IUserService;
import com.example.demo.user.impl.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Validated
@RestController
@CrossOrigin
@RequestMapping(path = "/user")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest) throws Exception {
        return ResponseEntity.ok(userService.loginUser(authenticationRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@Valid @RequestBody NewUserDto newUserDto) throws Exception {

        return ResponseEntity.ok(userService.registerUser(newUserDto));
    }

    @PostMapping("/msg")
    public ResponseEntity<?> saveMessage(@Valid @RequestBody MessageDto messageDto) throws Exception {
        if(messageDto.getMessage()!= null && messageDto.getMessage().startsWith("history ")){
            return ResponseEntity.ok(userService.printMessages(messageDto));
        } else{
            userService.doMessage(messageDto);
            return ResponseEntity.ok("Message saved");
        }
    }
}
