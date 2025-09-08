package com.java.CarMarket_backend.service;

import com.java.CarMarket_backend.dto.LoginDTO;
import com.java.CarMarket_backend.dto.UserDTO;

public interface UserService {
    UserDTO registerUser(UserDTO userDto);

    UserDTO loginUser(LoginDTO loginDTO);
}
