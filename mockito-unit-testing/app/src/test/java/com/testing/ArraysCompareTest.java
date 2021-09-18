package com.testing;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.Assert.*;

public class ArraysCompareTest {

    @Test public void testArraysSortCompare_PositiveCondition() {
        int[] numbers = {12, 3, 4, 1};
        int[] expected = {1, 3, 4, 12};
        Arrays.sort(numbers);
        assertArrayEquals(expected, numbers);
    }

    /**
     * Expected behaviour: test should fail when expected and numbers are not same.
     */
    @Test public void testArraysSortCompare_NegativeCondition() {
        int[] numbers = {12, 3, 4, 1};
        int[] expected = {1, 4, 3, 12};
        Arrays.sort(numbers);
        assertArrayEquals(expected, numbers);
    }

    /**
     * Expected behaviour: numbers array should throw an NullPointerException and test should
     * catch that exception.
     */
    @Test(expected=NullPointerException.class) 
    public void testArraysSortCompare_Condition_NullArray() {
        int[] numbers = null;
        int[] expected = {1, 4, 3, 12};
        Arrays.sort(numbers);
        assertArrayEquals(expected, numbers);
    }

    /**
     * Expected fail bahaviour: sort 1 million arrays in 10 millisecs should timeout before completion
     */
    @Test(timeout=10)
    public void testArraySortPerformance() {
        int[] numbers = {1, 4, 3, 12};
        for(int i = 1; i <= 1000000; i++) {
            numbers[0] = i;
            Arrays.sort(numbers);
        }
    }
}
