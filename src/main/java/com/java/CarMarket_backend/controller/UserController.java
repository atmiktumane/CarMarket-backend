package com.java.CarMarket_backend.controller;

import com.java.CarMarket_backend.dto.LoginDTO;
import com.java.CarMarket_backend.dto.ResponseDTO;
import com.java.CarMarket_backend.dto.UserDTO;
import com.java.CarMarket_backend.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    // POST - Register
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.registerUser((userDTO)));
    }

    // POST - Login
    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(userService.loginUser(loginDTO));
    }

    // POST - Send OTP
    @PostMapping("/sendOtp/{email}")
    public ResponseEntity<ResponseDTO> sendOtp(@PathVariable @Email(message = "Email is invalid.") String email) throws Exception{
        return new ResponseEntity<>(userService.sendOtp(email), HttpStatus.OK);
    }

    // GET - Verify OTP
    @GetMapping("/verifyOtp/{email}/{otp}")
    public ResponseEntity<ResponseDTO> verifyOtp(@PathVariable @Email(message = "Email is invalid.") String email, @PathVariable @Pattern(regexp = "^[0-9]{6}$", message = "OTP is invalid.") String otp) throws Exception{
        return new ResponseEntity<>(userService.verifyOtp(email, otp), HttpStatus.OK);
    }

    // POST - Change Password
    @PostMapping("/changePassword")
    public ResponseEntity<ResponseDTO> changePassword(@RequestBody LoginDTO loginDTO){
        return new ResponseEntity<>(userService.changePassword(loginDTO), HttpStatus.OK);
    }
}
