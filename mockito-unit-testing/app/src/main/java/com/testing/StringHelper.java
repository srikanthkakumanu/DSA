package com.testing;

public class StringHelper {

    public String truncateAInFirst2Positions(String text) {
        if(text.length() <= 2)
            return text.replaceAll("A", "");

        String ftwo = text.substring(0, 2), remaining = text.substring(2);
        return ftwo.replaceAll("A", "") + remaining;
    }

    public boolean isFirstAndLastTwoCharsAreSame(String text) {
        if(text.length() <= 1) return false;
        else if(text.length() == 2) return true;

        String ftwo = text.substring(0, 2);
        String ltwo = text.substring(text.length() - 2);

        return ftwo.equals(ltwo);
    }
}