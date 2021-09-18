package com.testing.mockito;


import java.util.ArrayList;
import java.util.List;

/**
 * This is SUI - System Under Test i.e. Mockito terminology
 * ToDoServiceImpl - SUT
 * ToDoService - Dependency
 */
public class ToDoServiceImpl {
	private ToDoService todoService;

	public ToDoServiceImpl(ToDoService todoService) {
		this.todoService = todoService;
	}

	public List<String> retrieveTodosRelatedToSpring(String user) {
		List<String> filteredTodos = new ArrayList<String>();
		List<String> allTodos = todoService.retrieveTodos(user);
		for (String todo : allTodos) {
			if (todo.contains("Spring")) {
				filteredTodos.add(todo);
			}
		}
		return filteredTodos;
	}
}