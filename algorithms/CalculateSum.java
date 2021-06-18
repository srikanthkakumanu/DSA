package algorithms;
public class CalculateSum {
    
    static int total = 0;
    static int nextVal = 0;
    public static void main(String[] args) {
        System.out.println(sumTotal1(Integer.parseInt(args[0])));
        System.out.println(sumTotal3(Integer.parseInt(args[0])));
        sumTotal2(Integer.parseInt(args[0]));
        System.out.println(total);
    }

    public static int sumTotal1(int n) {
        int total = 0;
        for(int i = 0; i <= n; i++) {
            total = total + i;
        }
        return total;
    }

    /**
     * Using Carl Guass Mathematical forumala for calculating paired consecutive numbers sum
     * @param n
     * @return
     */
    public static int sumTotal3(int n) {
        return n * (n + 1) / 2;
    }

    /**
     * Recursion
     * @param value
     */
    public static void sumTotal2(int value) {
        if(nextVal < value) {
            nextVal = nextVal + 1;
            total = total + nextVal;
            sumTotal2(value);
        }
    }
}
