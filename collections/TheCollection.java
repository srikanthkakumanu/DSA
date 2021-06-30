package collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/*

1. java.util.Collection<E> is a generic interface and is the root of Collection hierarchy (but it extends Iterable<E>). 

2. A collection represents group of objects known as its elements. Java platform doesn't provide direct impelementations 
of this interface but provide implementations to its sub interfaces.

Aggregate-operations OR stream-operations:
-----------------------------------------
Aggregate operations do process elements from a stream, not directly from collection. They support 'behavior' as parameters 
i.e. we can specify 'lambda expressions' as parameters for most aggregate operations. These operations introduced in Java 8. 
These operations are 'not mutative', meaning they do not modify underlying collection.

Ex:
list.stream().filter(e -> e.getColor() == Color.RED)
            .forEach(e -> System.out.println(e.getName()));

String joined = elements.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));


bulk-operations:
----------------
These operations operate on entire collections Ex: containsAll(), addAll(), removeAll(), retainAll(), clear() etc. 
But key difference is that these old or legecy operations are all 'mutative' meaning they all modify underlying 
collection.

Note: collection created from Arrays.asList() is immutable. It throws UnsupportedOperationException if any attempt
to modify it.

Ex:
Collection<Integer> collection = Arrays.asList(...);
collection.add(39); // throws UnsupportedOperationException

*/
public class TheCollection {
    public static void main(String[] args) {
        Integer[] one_dm_matrix = {1, 2, 3, 4, 5};
        Collection<Integer> collection = Arrays.asList(one_dm_matrix);
        Collection<Integer> list = new ArrayList<>(collection);

        collection.forEach(System.out::println);
        print(collection.contains(60));
        print((collection.containsAll(collection)));
        print(collection.equals(collection));
        print(collection.hashCode());
        Iterator<Integer> iterator = collection.iterator();
        iterator.forEachRemaining(value -> System.out.println(value + 20));
        Integer sum = collection.parallelStream()
                                    .map(value -> {
                                        value += 100;
                                        System.out.println("MAP -> value=" + value);
                                        return value;
                                    })
                                    //.reduce(0, Integer::sum);
                                    .reduce(0, (a, b) -> { 
                                        System.out.println("REDUCE -> a=" + a + ", b=" + b);
                                        return a + b; 
                                    });
        //Integer sum = collection.parallelStream().map(value -> value += 100).reduce(0, (a, b) -> a + b);                                   
        print("SUM=" + sum);                            
        // below operations on collection throws UnSupportedOperationException
        // because collection (created from Arrays.asList()) is immutable.
        //collection.add(60);
        //collection.clear();
        //collection.addAll(collection);
        //collection.remove(2);

        list.add(60);
        list.addAll(collection);
        System.out.println("After Adding");
        list.forEach(System.out::println);
        list.remove(2);
        System.out.println("After removing 2");
        list.forEach(System.out::println);
        print(collection.equals(list));
    }

    private static <V> void print(V value) {
        System.out.println(value);
    }
}