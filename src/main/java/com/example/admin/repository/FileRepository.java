package com.example.admin.repository;

import com.example.admin.model.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends PagingAndSortingRepository<User, Long>, ListCrudRepository<User, Long> {}

