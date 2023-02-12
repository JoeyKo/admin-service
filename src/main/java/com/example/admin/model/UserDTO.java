package com.example.admin.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;

    private String email;

    private ERole role;


    public UserDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole().getName();
    }
}
