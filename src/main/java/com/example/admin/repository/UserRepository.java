package com.example.admin.repository;

import com.example.admin.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, ListCrudRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.role r WHERE u.username = :username")
    Optional<User> findByUsername(String username);
}
