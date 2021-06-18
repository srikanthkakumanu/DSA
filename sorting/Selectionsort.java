package sorting;

/**
 * Selection sort is basic sorting algorithm among all other sorting algorithms
 * 1. find minimum index
 * 2. swap the values
 * 
 * O(n^2)
 * 
 */
public class Selectionsort {

    static int[] numbers = {22, 13, 1, 15, 56, 3, 2};
    public static void main(String[] args) {

        ascending(numbers);
        print(numbers);
        descending(numbers);
        print(numbers);
    }

    /**
     * selection sorting of numbers in descending order
     * @param numbers
     */
    private static void descending(int[] numbers) {

        for(int counter = 0; counter < numbers.length; counter++) {
            int max = max(numbers, counter);
            swap(numbers, counter, max);
        }
    }

    /**
     * selection sorting the numbers in ascending order
     * @param numbers
     */
    private static void ascending(int[] numbers) {
 
        for(int counter = 0; counter < numbers.length; counter++) {
            int min = min(numbers, counter);
            swap(numbers, counter, min);
        }
    }

    /**
     * swaps the values in the array with from and to index positions
     * @param numbers
     * @param from
     * @param to
     */
    private static void swap(int[] numbers, int from, int to) {
        int holder = numbers[from];
        numbers[from] = numbers[to];
        numbers[to] = holder; 
    }

    /**
     * Returns minimum data element index from an array
     * @param numbers
     * @param start
     * @return minIndex
    */
    private static int min(int[] numbers, int start) {
        int minIndex = start;
        int minValue = numbers[minIndex];

        for(int counter = start + 1; counter < numbers.length; counter++) {
            
            if(numbers[counter] < minValue) {
                minValue = numbers[counter];
                minIndex = counter;
            }
        }
        return minIndex;
    }

    /**
     * Returns maximum data element index from an array
     * @param numbers
     * @param start
     * @return maxIndex
     */
    private static int max(int[] numbers, int start) {
        int maxIndex = start;
        int maxValue = numbers[maxIndex];

        for(int counter = start + 1; counter < numbers.length; counter++) {
            
            if(numbers[counter] > maxValue) {
                maxValue = numbers[counter];
                maxIndex = counter;
            }
        }
        return maxIndex;
    }
    
    /**
     * prints array of numbers
     * @param numbers
     */
    private static void print(int[] numbers) {
        System.out.print("[");

        for(int number : numbers) 
            System.out.print(" " + number + " ");
        
        System.out.println("]");
    }
}

