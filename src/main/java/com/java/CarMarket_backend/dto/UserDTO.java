package com.java.CarMarket_backend.dto;

import com.java.CarMarket_backend.model.Role;
import com.java.CarMarket_backend.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String password;
    private Role role; // ENUM field

    public UserModel convertToUserModel(){
        return new UserModel(this.id, this.name, this.email, this.password, this.role);
    }
}
