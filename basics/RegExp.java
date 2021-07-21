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
       // even if the input is empty, it will return one zero-length match. 
       // Thats why we get 3 matches despite having a String of length two.
       // ? : match text 0 or 1 time (remember, it returns one zero-length match atleast)
       // * : match text 0 or limitless times (remember, it returns one zero-length match atleast)
       // + : it has a matching threshold of 1. If the required String does not occur at all, there will be no match, not even a zero-length String.
       print(eval("\\a?", "hi")); // match text 0 or 1 time 
       print(eval("\\a{0,1}", "hi")); // alternative to above stmt approach
       print(eval("\\a*", "hi")); // match text 0 or limitless times
       print(eval("\\a{0,}", "hi")); // alternative to above stmt approach
       print(eval("\\a+", "hi"));
       print(eval("\\a{1,}", "hi")); // alternative to above stmt approach
       print(eval("a{3}", "aaaaaa")); // given text no. of times i.e. 'aaa' appears 3 times in a row. That is 2.
       print(eval("a{3}", "aa"));
       print(eval("a{2,3}", "aaaa")); // at least 2 occurrences but not exceeding 3. It sees a single 'aaa' and 'a' can't be matched.
       print(eval("a{2,3}?", "aaaa")); // matching 2 occurrences as 'aa' and 'aa'.

       // Capturing Groups: It treats multiple characters as single unit.
       print(eval("(\\d\\d)", "12")); // match input text contains two digits next to each other.
       print(eval("(\\d\\d)", "1212"));
       print(eval("((\\d\\d)\\1)", "1212")); // have one match but propagating the same regex match to span the entire length of the input using back referencing
       print(eval("(\\d\\d)(\\d\\d)", "1212")); // without back referencing
       print(eval("(\\d\\d)\\1\\1\\1", "12121212"));
       print(eval("(\\d\\d)\\1", "1213")); // match fails even if we change one digit of the pattern. We changed last digit to 3.

       // Boundary matching
       // caret ^: to match only when the required regex is true at the beginning of the text.
       // $ : To match only when the required regex is true at the end of the text, we use the dollar character $.
       // \\b : If we want a match only when the required text is found at a word boundary, we use \\b regex at the beginning and end of the regex:
       print(eval("^dog", "dogs are friendly")); 
       print(eval("^dog", "are dogs are friendly?")); 
       print(eval("dog$", "Man's best friend is a dog"));
       print(eval("dog$", "is a dog man's best friend?"));
       print(eval("\\bdog\\b", "a dog is friendly"));

       // Flags
       /*
       CANON_EQ: enables canonical equivalence. When specified, two characters will be considered 
                to match if, and only if, their full canonical decompositions match.
       CASE_INSENSITIVE: matching regardless of case.
       DOTALL: we are matching every character in the input String until we encounter a new line character.
       */
      print(eval("\u00E9", "\u0065\u0301")); // match fails
      print(eval("\u00E9", "\u0065\u0301", Pattern.CANON_EQ)); // match successful
      
    }

    private static void print(Integer output) {
        System.out.println(output);
    }

    /**
     * Evaluates a regular expression on given text based on flag and returns no. of occurrences.
     * @param regex regular expression
     * @param text  input text
     * @param flag  supported flags which effects how patterns are matched
     * @return No. of occurrences of matched expression in given input text
     */
    private static int eval(String regex, String text, int flag) {
        Pattern p = Pattern.compile(regex, flag);
        Matcher m = p.matcher(text);
        // m.find();

        int matches = 0;
        while (m.find())
            matches++;

        return matches;
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
