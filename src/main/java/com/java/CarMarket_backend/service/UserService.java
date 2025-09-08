package com.java.CarMarket_backend.service;

import com.java.CarMarket_backend.dto.LoginDTO;
import com.java.CarMarket_backend.dto.ResponseDTO;
import com.java.CarMarket_backend.dto.UserDTO;

public interface UserService {
    UserDTO registerUser(UserDTO userDto);

    UserDTO loginUser(LoginDTO loginDTO);

    ResponseDTO sendOtp(String email) throws Exception;

    ResponseDTO verifyOtp(String email, String otp) throws Exception;
}
