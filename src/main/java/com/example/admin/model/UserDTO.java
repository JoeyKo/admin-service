package com.example.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userName;

    private String email;

    public UserDTO(User user) {
        this.userName = user.getUserName();
        this.email = user.getEmail();
    }
}
