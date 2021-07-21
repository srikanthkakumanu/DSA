package basics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1. Java's regular expression syntax is similar to Perl programming language.
 * A regular expression that works in one programming language may not work in
 * another. 
 * 2. java.lang.String also has in-built regex support. 
 * 3. Pattern object is a compiled regex. It provides static methods. 
 * 4. Meta characters: <([{\^-=$!|]})?*+.>
 */
public class RegExp {

    public static void main(String[] args) {

        // exact text match
        print(eval("foo", "foo"));

        // exact text match no. of times
        print(eval("foo", "foofoofoo"));

        // dot (.) meta characters
        print(eval(".", "foo"));

        // below expression consider . as another character if it is part of text
        // therefore it returns 1 only.
        print(eval("foo.", "foofoo")); // it returns only 1

        // set match with in []
        print(eval("[abc]", "b"));
        print(eval("[abc]", "cab"));
        print(eval("[bcr]at", "bat cat rat")); // alternating the first letter with each element of set and they all are
                                               // matched.

        // caret (^) is negation (NOR): matches beginning of input
        print(eval("^a", "ba"));
        print(eval("^a", "ab"));
        print(eval("[^abc]", "g"));
        print(eval("[^abc]", "a"));
        print(eval("[^bcr]at", "sat mat eat"));
        print(eval("[^bcr]at", "bat cat rat"));
        print(eval("[^bcr]at", "bat cat eat"));

        // Range match
        print(eval("[A-Z]", "Two Uppercase alphabets 34 overall")); // upper case
        print(eval("[a-z]", "Two Uppercase alphabets 34 overall")); // lower case
        print(eval("[A-Za-z]", "Two Uppercase alphabets 34 overall")); // both cases
        print(eval("[1-5]", "Two Uppercase alphabets 34 overall")); // numbers
        print(eval("[30-35]", "Two Uppercase alphabets 34 overall")); // numbers
        print(eval("[6-8]", "Two Uppercase alphabets 34 overall")); // numbers
        print(eval("[a-zA-Z0-9]", "Two Uppercase alphabets 34 overall")); // upper, lower, numbers

        // union: combine two or more sets
        print(eval("[1-3[7-9]]", "123456789")); // skips 4,5,6. It only match 6 out of 9 integers i.e. 123789
        print(eval("[1-3[7[9]]]", "123456789")); // skips 4,5,6,8. It only match 5 out of 9 integers i.e. 12379

        // intersection: common numbers between both sets
        print(eval("[1-6&&[3-9]]", "123456789")); // skips 1,2,7,8,9. It only match 4 out of 9 integers i.e. 3456
        print(eval("[1-6&&[3[5-9]]]", "123456789")); // skips 1,2,4,7,8,9. It only match 3 out of 9 integers i.e. 356

        // substraction
        print(eval("[0-9&&[^2468]]", "123456789")); // skips 2,4,6,8. It only match 5 out of 9 integers i.e. 1,3,5,7,9

       // Pre-defined character classes
       print(eval("\\d", "123")); // Matching digits equilent to 0-9 i.e. 3
       print(eval("\\D", "a6c")); // Matching non-digits [^0-9] i.e. 2
       print(eval("\\s", "a c")); // Matching white-space i.e. 1
       print(eval("\\S", "a c")); // Matching non white-space i.e. 2
       print(eval("\\w", "hi!")); // Matching word character equilent to [a-zA-Z_0-9] i.e. 2
       print(eval("\\W", "hi!")); // Matching non-word character equilent to [a-zA-Z_0-9] i.e. 1

       // Quantifiers = specify no. of occurrences. ? is the operator.
       print(eval("\\a?", "hi")); // match text 0 or 1 time
       print(eval("\\a{0,1}", "hi")); // alternative to above stmt approach
    }

    private static void print(Integer output) {
        System.out.println(output);
    }

    /**
     * Evaluates a regular expression on given text and returns no. of occurrences.
     * @param regex regular expression
     * @param text  input text
     * @return No. of occurrences of matched expression in given input text
     */
    private static int eval(String regex, String text) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        // m.find();

        int matches = 0;
        while (m.find())
            matches++;

        return matches;
    }
}
