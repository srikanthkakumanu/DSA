package sorting;

/**
 * Not a good sorting algorithm
 * Big O notation O(n^2)
 */
public class Bubblesort {
    public static void main(String[] args) {

        int[] numbers = {10, 8, 2, 14, 7};
        
        print(numbers);
        ascending(numbers);
        descending(numbers);
    }

    /**
     * Bubble sorting algorithm to sort some numbers in ascending order
     */
    public static void ascending(int[] numbers) {

        for(int outerIdx = 0; outerIdx < numbers.length; outerIdx++) {
            for(int innerIdx = 0; innerIdx < numbers.length-outerIdx-1; innerIdx++) {
 
                int current = innerIdx;
                int front = current + 1;
                boolean swapping = false;

                if(numbers[current] > numbers[front]) {
                    int temp = numbers[current];
                    numbers[current] = numbers[front];
                    numbers[front] = temp;
                    swapping = true;
                }

                if(!swapping)
                    continue;
            }
        }

        print(numbers);
    }

    /**
     * Bubble sorting algorithm to sort some numbers in descending order
     */
    private static void descending(int[] numbers) {

        for(int outerIdx = 0; outerIdx < numbers.length-1; outerIdx++) {
            
            
            for(int innerIdx = 0; innerIdx < numbers.length-outerIdx-1; innerIdx++) {
                
                int current = innerIdx;
                int front = current + 1;
                boolean swapping = false;

                if(numbers[current] < numbers[front]) {
                    int temp = numbers[current];
                    numbers[current] = numbers[front];
                    numbers[front] = temp;
                    swapping = true;
                }

                if(!swapping)
                    continue;
            }
        }

        print(numbers);
    }

    private static void print(int[] numbers) {
        System.out.print("[ ");
        for(int number : numbers) {
            System.out.print(" " + number + " ");
        }
        System.out.println("]");
    }
}

