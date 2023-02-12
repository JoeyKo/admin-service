package com.example.admin.jwt;

import com.example.admin.model.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRegisterRequest {
        private String username;
        private ERole role;
        private String password;

}
