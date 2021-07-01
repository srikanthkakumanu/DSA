package algorithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrequencyOfWords {
    public static void main(String[] args) {
        String sentence = "if it is to be it is up to me to delegate";
        frequencyOfWords(sentence);
    }

    /**
     * Generate Frequency table of words
     * @param sentence sentence
     */
    private static void frequencyOfWords(String sentence) {
        Map<String, Integer> map = new HashMap<>();
        List<String> words = Arrays.asList(sentence.split(" "));
        words.parallelStream().forEach(word -> {
            Integer frequency = map.get(word);
            map.put(word, (frequency == null) ? 1 : (frequency + 1));
        });
        System.out.println("Total " + map.size() + " words: " + map);
    }
}
