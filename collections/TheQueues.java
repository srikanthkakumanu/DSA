package collections;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class TheQueues {
    public static void main(String[] args) {
        customQueue();
    }

    private static void customQueue() {
        Queue<Integer> queue = new CustomQueue<>();
        queue.add(6); queue.add(38);
        
        int first = queue.poll();
        int second = queue.poll();

        System.out.print(first + " : " + second);
    }
}

/**
 * Mandatory methods to override.
 * iterator(), size(), offer(), peek(), poll()
 */
class CustomQueue<T> extends AbstractQueue<T> {

    private LinkedList<T> elements;

    public CustomQueue() { this.elements = new LinkedList<>(); }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

    @Override
    public int size() {
        return elements.size();
    }

    /**
     * Inserts new element.
     */
    @Override
    public boolean offer(T e) {
        if(e == null)
            return false;
        
        elements.add(e);

        return true;
    }

    /**
     * Removes an element.
     */
    @Override
    public T poll() {
        Iterator<T> iterator = elements.iterator();
        T t = iterator.next();
        if(t != null) {
            iterator.remove();
            return t;
        }
        return null;
    }

    /**
     * Returns/inspects an element at front of the queue without removing it.
     */
    @Override
    public T peek() {
        return elements.getFirst();
    }


    
}
