package com.raxrot.todo.mapper;

import com.raxrot.todo.dto.TodoDto;
import com.raxrot.todo.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AutoTodoMapper {
    AutoTodoMapper MAPPER= Mappers.getMapper(AutoTodoMapper.class);

    TodoDto mapToTodoDto(Todo todo);
    Todo mapToTodo(TodoDto todoDto);
    List<TodoDto>mapToTodoDtoList(List<Todo>todos);
}
