package com.java.CarMarket_backend.service.impl;

import com.java.CarMarket_backend.dto.LoginDTO;
import com.java.CarMarket_backend.dto.UserDTO;
import com.java.CarMarket_backend.exception.EmailAlreadyExistsException;
import com.java.CarMarket_backend.exception.EmptyFieldException;
import com.java.CarMarket_backend.exception.InvalidCredentialsException;
import com.java.CarMarket_backend.model.Role;
import com.java.CarMarket_backend.model.UserModel;
import com.java.CarMarket_backend.repository.UserRepository;
import com.java.CarMarket_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service // This annotation is mandatory for spring to detect it as a bean
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(UserDTO userDTO){

        // Check if email is Empty
        if(userDTO.getEmail().isEmpty()){
            throw new EmptyFieldException("Please provide email");
        }

        // Check if Email already exists
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists : " + userDTO.getEmail());
        }

        UserModel user = new UserModel();

        // Set Name & Email
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Set Role (from request body or default as BUYER
        if(userDTO.getRole() != null){
            user.setRole(userDTO.getRole());
        }else{
            user.setRole(Role.BUYER); // default role
        }

        // Save User data in DB
        UserModel savedUser = userRepository.save(user);

        // Prepare Response body
        UserDTO response = new UserDTO();

        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());

        return response;
    }

    @Override
    public UserDTO loginUser(LoginDTO loginDTO) {
        // Check if User is present or not
        UserModel user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()-> new InvalidCredentialsException("Invalid Email or Password"));

        // Match Password
        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid Email or Password");
        }

        // Prepare Login Response body
        return user.convertToUserDTO();
    }


}
