package dsa;

/**
 * Stack is a linear data structure. This implementation is using array for stack.
 * Stack with array implementation is bounded in size i.e. fixed size or static
 * push: O(1)
 * pop: O(1)
 * peek: O(1)
 * print/access/search: O(n)
 * empty: O(1)
 */
public class StackArray {
    

    public static void main(String[] args) {
        Stack stack = new Stack();

        stack.push(5);
        stack.push(15);
        stack.push(20);
        stack.push(25);
        stack.push(30);

        stack.printAll();

        stack.pop();
        stack.pop();
        stack.peek();
        stack.printAll();
    }

}

class Stack {

    int[] stack = new int[10]; // stack for 10 int values
    int top = -1; 

    /**
     * Returns true/false 
     * O(1)
     * @return boolean true/false
     */
    public boolean isEmpty() {
        if(top < 0)
            return true;
        else
            return false;
    }

    /**
     * checks if stack is overflow
     * O(1)
     * @return boolean true/false
     */
    public boolean isOverflow() {
        if(top >= 10) {
            System.err.println("Stack is overflow!");
            return true;
        } else 
            return false;
    }

    /**
     * checks if stack is underflow
     * O(1)
     * @return boolean true/false
     */
    public boolean isUnderflow() {
        if(isEmpty()) {
            System.err.println("Stack is underflow!");
            return true;
        } else
            return false;
    }

    /**
     * inserts data into stack in FILO order (First-In & Last-Out)
     * O(1)
     * @param value int data value
     */
    public void push(int value) {
        if(isOverflow())
            System.err.println("Stack is full!!");
        else 
            stack[++top] = value;
    }

    /**
     * deletes a value from stack in FILO order (First-In & Last-Out)
     * O(1)
     */
    public void pop() {
        if(isUnderflow())
            System.err.println("Stack is Empty!!");
        else { 
            int deleted = stack[top--];
            System.out.println(deleted + " is deleted from stack");
        }
    }

    /**
     * peek: prints top data element of stack
     */
    public void peek() {
        if(!isEmpty())
            System.out.println("Stack top element is : " + stack[top]);
    }

    /**
     * prints all stack data elements
     * O(n)
     */
    public void printAll() {
        System.out.print("[ ");
        for(int index = 0; index <= top; index++)
            System.out.print(stack[index] + " ");
        System.out.print(" ]\n");
    }
}

