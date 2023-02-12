package com.example.admin.repository;

import java.util.Optional;

import com.example.admin.model.ERole;
import com.example.admin.model.Role;
import org.springframework.data.repository.CrudRepository;


public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
