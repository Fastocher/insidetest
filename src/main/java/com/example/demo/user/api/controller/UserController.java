package com.example.demo.user.api.controller;

import com.example.demo.user.api.models.JwtRequest;
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

    @GetMapping("/id")
    public ResponseEntity<?> getUser (@RequestParam Long id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/name")
    public ResponseEntity<?> getUserByID (@RequestParam String name){
        return ResponseEntity.ok(userService.getUserByName(name));
    }

    @GetMapping("/bday")
    public ResponseEntity<?> getUserByBirthday (@RequestParam String date){
        return ResponseEntity.ok(userService.getUserByBirthday(date));
    }

    @GetMapping("/tel")
    public ResponseEntity<?> getUserBytel (@RequestParam String number){
        return ResponseEntity.ok(userService.getUserBytel(number));
    }

    @PostMapping("/new")
    public ResponseEntity<?> setNewUser(@RequestBody NewUserDto newUserDto){
        userService.setNewUser(newUserDto);
        return ResponseEntity.ok().body("User successfully added!");
    }

    @DeleteMapping("/del")
    public ResponseEntity<?> deleteBooking(@RequestParam Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().body("Successfully delete user");
    }

}
