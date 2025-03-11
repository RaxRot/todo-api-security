package com.raxrot.todo.service.impl;

import com.raxrot.todo.dto.TodoDto;
import com.raxrot.todo.entity.Todo;
import com.raxrot.todo.exception.ResourceNotFoundException;
import com.raxrot.todo.mapper.AutoTodoMapper;
import com.raxrot.todo.repository.TodoRepository;
import com.raxrot.todo.service.TodoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImplementation implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImplementation(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        Todo todo= AutoTodoMapper.MAPPER.mapToTodo(todoDto);
        todo=todoRepository.save(todo);
        return AutoTodoMapper.MAPPER.mapToTodoDto(todo);
    }

    @Override
    public List<TodoDto> getAllTodos() {
        return AutoTodoMapper.MAPPER.mapToTodoDtoList(todoRepository.findAll());
    }

    @Override
    public TodoDto getTodoById(Long id) {
        Todo todo=todoRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Todo not found with id:"+id));
        return AutoTodoMapper.MAPPER.mapToTodoDto(todo);
    }

    @Override
    public TodoDto updateTodo(Long id, TodoDto todoDto) {
        Todo todo=todoRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Todo not found with id:"+id));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());
        Todo updatedTodo = todoRepository.save(todo);
        return AutoTodoMapper.MAPPER.mapToTodoDto(updatedTodo);
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo=todoRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Todo not found with id:"+id));
        todoRepository.deleteById(todo.getId());
    }

    @Override
    public TodoDto completeTodo(Long id) {
        Todo todo=todoRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Todo not found with id:"+id));
        todo.setCompleted(Boolean.TRUE);
       Todo updatedTodo = todoRepository.save(todo);
       return AutoTodoMapper.MAPPER.mapToTodoDto(updatedTodo);
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {
        Todo todo=todoRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Todo not found with id:"+id));
        todo.setCompleted(Boolean.FALSE);
        Todo updatedTodo = todoRepository.save(todo);
        return AutoTodoMapper.MAPPER.mapToTodoDto(updatedTodo);
    }

}
