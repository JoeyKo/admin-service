package com.example.admin.service;

import com.example.admin.jwt.AuthenticationRegisterRequest;
import com.example.admin.jwt.AuthenticationRequest;
import com.example.admin.jwt.AuthenticationResponse;
import com.example.admin.model.Role;
import com.example.admin.model.UserDTO;
import com.example.admin.repository.RoleRepository;
import com.example.admin.repository.UserRepository;
import com.example.admin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import com.example.admin.jwt.JwtProvider;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Page<UserDTO> getAllUsers(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo - 1, pageSize);
        Page<User> users = userRepository.findAll(paging);

        return users.map(UserDTO::new);
    }

    public AuthenticationResponse register(AuthenticationRegisterRequest authenticationRegisterRequest) throws Exception {
        Role role = roleRepository.findByName(authenticationRegisterRequest.getRole())
                .orElseThrow(()-> new Exception("用户角色不存在，需要去创建"));
        User nUser = new User();
        nUser.setUsername(authenticationRegisterRequest.getUsername());
        nUser.setPassword(passwordEncoder.encode(authenticationRegisterRequest.getPassword()));
        nUser.setRole(role);

        userRepository.save(nUser);

        String token = JwtProvider.generateTokenJWT(nUser.getUsername());

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = JwtProvider.generateTokenJWT(authRequest.getUsername());

        return new AuthenticationResponse(token);
    }


}
