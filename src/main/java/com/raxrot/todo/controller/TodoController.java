package com.raxrot.todo.controller;

import com.raxrot.todo.dto.TodoDto;
import com.raxrot.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//http://localhost:8080/swagger-ui/index.html for work

@Tag(name = "Todo Management", description = "CRUD operations for managing Todo tasks")
@RestController
@RequestMapping("api/todos")
public class TodoController {

    private TodoService todoService;
    public TodoController(TodoService todoService){
        this.todoService=todoService;
    }

    @Operation(summary = "Create a new Todo", description = "Accessible only for ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Todo successfully created"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto>createTodo(@Valid @RequestBody TodoDto todoDto){
        TodoDto savedTodo=todoService.addTodo(todoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTodo);
    }

    @Operation(summary = "Get a Todo by ID", description = "Accessible for ADMIN and USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo found"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto>getTodoById(@PathVariable("id")Long todoId){
        TodoDto foundedDto=todoService.getTodoById(todoId);
        return (foundedDto!=null)?
                ResponseEntity.ok(foundedDto):
                ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get all Todos", description = "Accessible for ADMIN and USER")
    @ApiResponse(responseCode = "200", description = "List of todos retrieved successfully")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>>getTodos(){
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @Operation(summary = "Update a Todo", description = "Accessible only for ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo updated successfully"),
            @ApiResponse(responseCode = "404", description = "Todo not found"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto>updateTodo(@PathVariable("id")Long todoId,@Valid @RequestBody TodoDto todoDto){
        TodoDto updatedTodo=todoService.updateTodo(todoId,todoDto);
        return ResponseEntity.ok(updatedTodo);
    }

    @Operation(summary = "Delete a Todo", description = "Accessible only for ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Todo deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteTodo(@PathVariable("id") Long todoId){
        todoService.deleteTodo(todoId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Mark Todo as completed", description = "Accessible for ADMIN and USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo marked as completed"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping({"/{id}/complete"})
    public ResponseEntity<TodoDto>completeTodo(@PathVariable("id") Long todoId){
        TodoDto updatedTodo=todoService.completeTodo(todoId);
        return ResponseEntity.ok(updatedTodo);
    }

    @Operation(summary = "Mark Todo as incomplete", description = "Accessible for ADMIN and USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo marked as incomplete"),
            @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("/{id}/incomplete")
    public ResponseEntity<TodoDto>inCompleteTodo(@PathVariable("id")Long id){
        TodoDto updatedTodo=todoService.inCompleteTodo(id);
        return ResponseEntity.ok(updatedTodo);
    }
}
