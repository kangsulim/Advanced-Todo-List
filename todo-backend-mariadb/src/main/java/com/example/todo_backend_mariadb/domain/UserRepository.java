package com.example.todo_backend_mariadb.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
