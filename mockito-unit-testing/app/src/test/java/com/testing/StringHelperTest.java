package com.testing;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;

public class StringHelperTest {
    
    StringHelper helper;

    @BeforeClass public static void load() { System.out.println("class loaded"); }
    @AfterClass public static void unload() { System.out.println("class unloaded"); }
    
    @Before public void setup() {
        helper = new StringHelper();
        System.out.println("setup()");
    }
    
    @After public void teardown() { 
        helper = null;
        System.out.println("teardown()"); 
    }

    // AACD => CD, ACD => CD, CDEF => CDEF, CDAA => CDAA
    @Test public void testTruncateAInFirst2Positions_Condition_AInFirst2Positions() {
        assertEquals("CD", helper.truncateAInFirst2Positions("AACD"));
    }

    @Test public void testTruncateAInFirst2Positions_Condition_AInFirstPosition() {
        assertEquals("CD", helper.truncateAInFirst2Positions("ACD"));
    }

    @Test public void testTruncateAInFirst2Positions_Condition_ANotInFirst2Positions() {
        assertEquals("CDEF", helper.truncateAInFirst2Positions("CDEF"));
    }

    @Test public void testTruncateAInFirst2Positions_Condition_AInLastTwoPositions() {
        assertEquals("CDAA", helper.truncateAInFirst2Positions("CDAA"));
    }

    // ABCD => false, ABAB => true, AB => true, A => false
    @Test public void testIsFirstAndLastTwoCharsAreSame_NegativeCondition_NonMatchingChars_ShouldReturnFalse() {
        // assertEquals(false, helper.isFirstAndLastTwoCharsAreSame("ABCD"));
        // old approach: assertFalse("False because [ABCD] is not [ABAB]", helper.isFirstAndLastTwoCharsAreSame("ABCD"));
        assertFalse(helper.isFirstAndLastTwoCharsAreSame("ABCD"));
    }

    @Test public void testIsFirstAndLastTwoCharsAreSame_PositiveCondition_MatchingChars_ShouldReturnTrue() {
        assertTrue(helper.isFirstAndLastTwoCharsAreSame("ABAB"));
    }

    @Test public void testIsFirstAndLastTwoCharsAreSame_PositiveCondition_OnlyTwoChars_ShouldReturnTrue() {
        assertTrue(helper.isFirstAndLastTwoCharsAreSame("AB"));
    }

    @Test public void testIsFirstAndLastTwoCharsAreSame_NegativeCondition_SingleChar_ShouldReturnFalse() {
        assertFalse(helper.isFirstAndLastTwoCharsAreSame("A"));
    }

}
