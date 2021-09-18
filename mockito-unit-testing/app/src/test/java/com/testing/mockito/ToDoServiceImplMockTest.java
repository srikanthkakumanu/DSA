package com.testing.mockito;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * What is Mocking?
 * 
 * Mocking is a way of creating objects that simulate the behaviour of real objects.
 * Unlike stubs, mocks can be dynamically created from code - at runtime.
 * Mocks offer more functionality than stubbing.
 */
public class ToDoServiceImplMockTest {
    @Test
    public void testRetriveToDosRelatedToSpring_UsingMock() {

        ToDoService todoServiceMock = mock(ToDoService.class); // This is called mocking a dependency
        when(todoServiceMock.retrieveTodos("Dummy"))
            .thenReturn(Arrays.asList(
                                "Learn Spring MVC", 
                                "Learn Spring", 
                                "Learn to Live")
                        );
        
        ToDoServiceImpl todo = new ToDoServiceImpl(todoServiceMock);

        assertEquals(2, todo.retrieveTodosRelatedToSpring("Dummy").size()); 
    }

    @Test
    public void testRetriveToDosRelatedToSpring_WithEmptyList() {

        ToDoService todoServiceMock = mock(ToDoService.class); // This is called mocking a dependency
        when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(Arrays.asList());
        
        ToDoServiceImpl todo = new ToDoServiceImpl(todoServiceMock);

        assertEquals(0, todo.retrieveTodosRelatedToSpring("Dummy").size()); 
    }

}
