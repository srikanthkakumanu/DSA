package com.testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Sample JUnit test suit with specific test classes to be included.
 */
@RunWith(Suite.class)
@SuiteClasses({AppTest.class, StringHelperTest.class, StringHelperParameterizedTest.class})
public class SimpleTestSuite {
    
}
