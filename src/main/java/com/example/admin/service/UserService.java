package com.example.admin.service;

import com.example.admin.model.UserDTO;
import com.example.admin.repository.UserRepository;
import com.example.admin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public Page<UserDTO> getAllUsers(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo - 1, pageSize);
        Page<User> users = repository.findAll(paging);

        return users.map(UserDTO::new);
    }

    public User createUser(User user) {
        return repository.save(user);
    }

}
