package com.java.CarMarket_backend.service.impl;

import com.java.CarMarket_backend.dto.UserDTO;
import com.java.CarMarket_backend.model.UserModel;
import com.java.CarMarket_backend.repository.UserRepository;
import com.java.CarMarket_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // This annotation is mandatory for spring to detect it as a bean
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO registerUser(UserDTO userDTO){
        UserModel user = userDTO.convertToUserModel();

        UserModel savedUser = userRepository.save(user);

        return savedUser.convertToUserDTO();
    }


}
