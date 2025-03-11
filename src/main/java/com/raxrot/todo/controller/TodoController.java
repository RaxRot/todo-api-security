package com.raxrot.todo.controller;

import com.raxrot.todo.dto.TodoDto;
import com.raxrot.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todos")
public class TodoController {

    private TodoService todoService;
    public TodoController(TodoService todoService){
        this.todoService=todoService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto>createTodo(@Valid @RequestBody TodoDto todoDto){
        TodoDto savedTodo=todoService.addTodo(todoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTodo);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto>getTodoById(@PathVariable("id")Long todoId){
        TodoDto foundedDto=todoService.getTodoById(todoId);
        return (foundedDto!=null)?
                ResponseEntity.ok(foundedDto):
                ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>>getTodos(){
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto>updateTodo(@PathVariable("id")Long todoId,@Valid @RequestBody TodoDto todoDto){
        TodoDto updatedTodo=todoService.updateTodo(todoId,todoDto);
        return ResponseEntity.ok(updatedTodo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteTodo(@PathVariable("id") Long todoId){
        todoService.deleteTodo(todoId);
        return ResponseEntity.noContent().build();
    }
    
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping({"/{id}/complete"})
    public ResponseEntity<TodoDto>completeTodo(@PathVariable("id") Long todoId){
        TodoDto updatedTodo=todoService.completeTodo(todoId);
        return ResponseEntity.ok(updatedTodo);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("/{id}/incomplete")
    public ResponseEntity<TodoDto>inCompleteTodo(@PathVariable("id")Long id){
        TodoDto updatedTodo=todoService.inCompleteTodo(id);
        return ResponseEntity.ok(updatedTodo);
    }
}
