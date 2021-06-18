package dsa;
/**
 * Stack is a linear data structure. This implementation is using linked list i.e. Node instead of array for Stack.
 * Stack with Linked List is dynamic in size hence no overflow possible.
 * push: 
 * pop:
 * peek:
 * empty:
 * print/access/search: 
 */
public class StackList {
    public static void main(String[] args) {
        StackLinkedList list = new StackLinkedList();

        list.push(15);
        list.push(25);
        list.push(35);
        list.push(45);
        list.push(55);

        list.printAll();

        list.pop();

        list.peek();

        list.printAll();
    }
}

class StackNode {
    int data;
    StackNode next;
}

class StackLinkedList {
    StackNode top;
    
    /**
     * push: inserts new data element into Stack Linked List
     * O(1)
     * @param data
     */
    public void push(int data) {
        // create a node for new data
        StackNode node = new StackNode();
        node.data = data;

        if(isEmpty())
            node.next = null;
        else
            node.next = top;
        
        top = node;
     }

     /**
      * pop: removes a data element from Stack Linked List
      * O(1)
      */
     public void pop() {
        if(!isUnderflow()) { 
            StackNode node = top;
            top = top.next;
            node.next = null;
            System.out.println(node.data + " is deleted from stack.");
        }
     }

     /**
      * peek: prints top/front element of stack
      * O(1)
      */
     public void peek() {
         if(!isEmpty())
            System.out.println(top.data);
     }

     /**
      * checks whether stack is empty or not 
      * O(1)
      * @return true/false
      */
     public boolean isUnderflow() {
         if(isEmpty()) {
             System.err.println("Stack is underflow!!");
             return true;
         } else 
            return false;
     }

     /**
      * Returns true/false if stack is empty or not
      * O(1)
      * @return true/false
      */
     public boolean isEmpty() {
          return (top == null);
     }

     /**
      * Prints/accesses all stack data elements
      * O(n)
      */
     public void printAll() {

        if(!isEmpty()) {
            System.out.print("[");
            StackNode node = top;
            while(node != null) {
                System.out.print(" " + node.data + " ");
                node = node.next;
            }
            System.out.println("]");
        }
     }
}
