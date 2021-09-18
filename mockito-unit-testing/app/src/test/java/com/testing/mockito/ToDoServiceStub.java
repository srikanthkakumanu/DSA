package com.testing.mockito;

import java.util.Arrays;
import java.util.List;

/**
 * Stub:
 * In Mockito, The Stub is nothing but a dummy implementation of a service that we want mock.
 * Disadvantage of stubs is static and dummy implementation.
 * They cannot be suited for dynamic conditions. 
 * 
 * Thats why we use mocks using Mockito.
 */
public class ToDoServiceStub implements ToDoService {

    @Override
    public List<String> retrieveTodos(String user) {
        return Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Live");
    }
    
}

