package com.testing.mockito;

import java.util.List;

// External Service - Lets say this comes from WunderList
public interface ToDoService {
	public List<String> retrieveTodos(String user);
}
