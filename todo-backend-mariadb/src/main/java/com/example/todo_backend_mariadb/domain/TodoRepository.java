package com.example.todo_backend_mariadb.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "todos", path = "todos")
public interface TodoRepository extends CrudRepository<Todo, Long> {
    
    // CrudRepository를 상속받기 때문에 기본적인 데이터베이스 작업은 이미 구현된 건
    // ex) save(), findById(), findAll(), deleteById() 등
}
