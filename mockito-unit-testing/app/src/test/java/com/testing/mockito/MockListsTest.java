package com.testing.mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import java.util.List;

public class MockListsTest {

    @Test public void mockList_Size_Method() {
        List aList = mock(List.class);
        when(aList.size()).thenReturn(2);
        assertEquals(2, aList.size());
    }

    @Test public void mockList_Size_Method_ReturnMultipleValues() {
        List aList = mock(List.class);
        when(aList.size()).thenReturn(2).thenReturn(3);
        assertEquals(2, aList.size());
        assertEquals(3, aList.size());
    }

    @Test public void mockList_Get_Method() {
        List aList = mock(List.class);
        when(aList.get(0)).thenReturn("Hello World");
        assertEquals("Hello World", aList.get(0));
        assertEquals(null, aList.get(1));
    }

    // test argument matchers
    @Test public void mockList_ArgumentMatchers() {
        List aList = mock(List.class);
        when(aList.get(anyInt())).thenReturn("Hello World");
        assertEquals("Hello World", aList.get(0));
        assertEquals("Hello World", aList.get(1));
    }

    @Test(expected=RuntimeException.class) 
    public void mockList_Throw_Exception() {
        List aList = mock(List.class);
        when(aList.get(anyInt())).thenThrow(new RuntimeException("Runtime Error"));
        aList.get(0);
    }   
}
