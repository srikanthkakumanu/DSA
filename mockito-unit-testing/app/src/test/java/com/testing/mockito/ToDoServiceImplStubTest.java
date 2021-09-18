package com.testing.mockito;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Stub:
 * In Mockito, The Stub is nothing but a dummy implementation of a service that we want mock.
 * Disadvantage of stubs is static and dummy implementation.
 * They cannot be suited for dynamic conditions. 
 * 
 * Thats why we use mocks using Mockito.
 */
public class ToDoServiceImplStubTest {
    
    @Test
    public void test() {
        ToDoService stub = new ToDoServiceStub();
        ToDoServiceImpl todo = new ToDoServiceImpl(stub);

        assertEquals(2, todo.retrieveTodosRelatedToSpring("Dummy").size()); 
    }
}
