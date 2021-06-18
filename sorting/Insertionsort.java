package sorting;

/**
 * This is a straight insertion sorting algorithm to sort array of numbers
 * Better than bubble and selection sort and it is best suited for small set of data
 * O(n^2)
 */
public class Insertionsort {

    public static void main(String[] args) {
    
        int[] numbers = {32, 45, 12, 4, 14};
        print(numbers);
        ascending(numbers);
        print(numbers);
    }

    /**
     * Insertion sorting algorithm to sort some numbers in ascending order
     * @param numbers
     */
    private static void ascending(int[] numbers) {
        int key, innerIdx;

        for(int outerIdx = 1; outerIdx < numbers.length; outerIdx++) {
            key = numbers[outerIdx];
            innerIdx = outerIdx - 1;

            while(innerIdx >= 0 && numbers[innerIdx] > key) {
                numbers[innerIdx + 1] = numbers[innerIdx];
                innerIdx = innerIdx - 1;
            }
            numbers[innerIdx + 1] = key;
        }
    }


    /**
     * prints array of numbers
     * @param numbers
     */
    private static void print(int[] numbers) {
        System.out.print("[ ");
        for(int number : numbers) 
            System.out.print(" " + number + " ");
        
        System.out.println("] ");

        
    }

}