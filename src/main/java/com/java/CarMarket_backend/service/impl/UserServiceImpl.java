package com.java.CarMarket_backend.service.impl;

import com.java.CarMarket_backend.dto.LoginDTO;
import com.java.CarMarket_backend.dto.ResponseDTO;
import com.java.CarMarket_backend.dto.UserDTO;
import com.java.CarMarket_backend.exception.EmailAlreadyExistsException;
import com.java.CarMarket_backend.exception.EmptyFieldException;
import com.java.CarMarket_backend.exception.InvalidCredentialsException;
import com.java.CarMarket_backend.model.OtpModel;
import com.java.CarMarket_backend.model.Role;
import com.java.CarMarket_backend.model.UserModel;
import com.java.CarMarket_backend.repository.OtpRepository;
import com.java.CarMarket_backend.repository.UserRepository;
import com.java.CarMarket_backend.service.UserService;
import com.java.CarMarket_backend.utility.Data;
import com.java.CarMarket_backend.utility.Utilities;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service // This annotation is mandatory for spring to detect it as a bean
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

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

    @Override
    public ResponseDTO sendOtp(String email) throws Exception {
//         // Testing
//        System.out.println("Email in API request params : "+ email);

        // Check if Email is present or not
        UserModel user = userRepository.findByEmail(email).orElseThrow(()-> new InvalidCredentialsException("Invalid email"));

        // MimeMessage : return HTML Body in Message
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mailMessage, true);

        message.setTo(email); // Set Receiver Email
        message.setSubject("Your OTP Code"); // Set Email Subject

        // Generate OTP from Utilities
        String generatedOtp = Utilities.generateOTP();

        // Create otp data object with Given Data (i.e., email, otpCode, currentTime)
        OtpModel otp = new OtpModel(email, generatedOtp, LocalDateTime.now());

        // Save otp data in Database
        otpRepository.save(otp);

        // Username of receiver
        String username = user.getName();

        // Email Body - Call "Data" Utility Class
        message.setText(Data.otpEmailBody(generatedOtp, username), true);

        mailSender.send(mailMessage);

        return new ResponseDTO("OTP sent successfully");
    }


}
