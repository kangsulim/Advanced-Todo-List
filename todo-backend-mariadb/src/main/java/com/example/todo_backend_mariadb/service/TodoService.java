package com.example.todo_backend_mariadb.service;

import com.example.todo_backend_mariadb.domain.*;
import com.example.todo_backend_mariadb.dto.TodoDtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    /*
    JpaRepository를 extends 하고 있는 인터페이스를
    @Service 애너테이션이 있는 클래스에서
    private final TodoRepository todoRepository; 처럼 선언해주면
    Spring이 자동으로 구현체를 생성하고 주입해준다.

    그러므로 의존성 주입(Dependency Injection) 이 완성된다.

    ---
    전체 흐름

    1. TodoRepository 인터페이스 선언 (JpaRepository 상속)
    2. TodoService에서 private final TodoRepository 선언
    3. @RequiredArgsConstructor + @Service에 의해 생성자 주입 발생
    4. Spring이 자동으로 구현체를 만들어 주입
    5. TodoService는 todoRepository를 자유롭게 사용 가능
    
     */

    @Transactional
    public TodoResponseDto createTodo(TodoCreateRequestDto requestDto, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseGet(() -> userRepository.save(User.builder()
                                .name(userEmail.split("@")[0])
                                .email(userEmail)
                                .role(Role.USER)
                        .build()));

        Todo todo = Todo.builder()
                .text(requestDto.text())
                .completed(false)
                .user(user)
                .build();

        return new TodoResponseDto(todoRepository.save(todo));
    }

    public List<TodoResponseDto> findMyTodos(String userEmail) {
        User user = findUserByEmail(userEmail);
        return todoRepository.findByUserOrderByIdAsc(user).stream()
                .map(TodoResponseDto::new)
                .collect(Collectors.toList());
    }

    public TodoResponseDto toggleTodo(Long id, String userEmail) {
        Todo todo = findByIdAndUserEmail(id, userEmail);
        todo.setCompleted(!todo.isCompleted());
        return new TodoResponseDto(todo);
    }

    public void deleteDto(Long id, String userEmail) {
        Todo todo = findByIdAndUserEmail(id, userEmail);
        todoRepository.delete(todo);
    }



    private User findUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail)
                .orElseGet(() -> userRepository.save(User.builder()
                                .name(userEmail.split("@")[0])
                                .email(userEmail)
                                .role(Role.USER)
                        .build()));
    }

    private Todo findByIdAndUserEmail(Long id, String userEmail) {
        return todoRepository.findById(id)
                .filter(todo -> todo.getUser().getEmail().equals(userEmail))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 권한이 없는 Todo입니다."));
    }
}

