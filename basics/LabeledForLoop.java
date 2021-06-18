package basics;

public class LabeledForLoop {
    public static void main(String[] args) {
        outer:for(int outer = 0; outer < 10; outer++) {
            System.out.println("outer = " + outer);
            inner:for(int inner = 0; inner < 10; inner++) {
                System.out.println("inner = " + inner);
                if (inner == 5)
                    break outer;
            }
        }
    }
}
