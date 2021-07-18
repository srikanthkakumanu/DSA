package basics;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Arrays;

/**
 * This Program demonstrates usage of:
 * 1. stream()
 * 2. filter(), combining predicates
 * 3. collect(), Collectors util class, collectAndThen(), counting, averaging, summing, grouping, partitioning, teeing
 * 4. map()
 * 5. reduce()
 */
public class StreamsAndLambdas {
    public static void main(String[] args) {
        filter();
        collect();
        map();
        reduce();
    }

    /**
     * Different ways of using filter() method
     */
    private static void filter() {
        List<String> cats = Arrays.asList("tiger", "cat", "TIGER", "Tiger", "leopard");
        String search = "tiger";

        String tigers = cats.stream().filter(name -> name.equalsIgnoreCase(search)).collect(Collectors.joining(", "));
        System.out.println(tigers);

        Predicate<String> predicate = value -> value.equalsIgnoreCase(search);
        Predicate<String> combined = predicate.or(value -> value.equals("leopard"));
        String pride = cats.stream().filter(combined).collect(Collectors.joining(", "));
        System.out.println(pride);
    }

    /**
     * Different ways of using collect() method and Collectors util class:
     * predicate
     * collectingAndThen()
     * averaging Double/Long/Int
     * counting
     * summing Double/Long/Int
     * groupingBy
     * partitioningBy
     * teeing
     */
    private static void collect() {
        List<String> cats = Arrays.asList("tiger", "cat", "rat", "bat", "TIGER", "Tiger", "leopard", "leopard");
        String search = "tiger";

        // String tigers = cats.stream().filter(name -> name.equalsIgnoreCase(search)).collect(Collectors.joining(", "));

        Predicate<String> predicate = value -> value.equalsIgnoreCase(search);
        Predicate<String> combined = predicate.or(value -> value.equals("leopard"));
        
        // collect to string
        String pride = cats.stream().filter(combined).collect(Collectors.joining(", "));
        System.out.println(pride);        

        // collect to list
        List<String> list = cats.stream().filter(combined).collect(Collectors.toList());
        // Java 10, to unmodifiable list i.e. changes to unmList throws UnsupportedOperationException
        List<String> unmList = cats.stream().filter(combined).collect(Collectors.toUnmodifiableList());
        // usage of collectingAndThen()
        List<String> unmList2 = cats.stream().filter(combined).collect(Collectors.collectingAndThen(Collectors.toList(), List::copyOf));
        // usage of counting
        Long count = cats.stream().collect(Collectors.counting());
        // usage of averaging Double/Long/Int
        Double average = cats.stream().collect(Collectors.averagingDouble(String::length));
        // usage of summing Double/Long/Int
        Double sum = cats.stream().collect(Collectors.summingDouble(String::length));
        // usage of groupingBy
        Map<Integer, Set<String>> grouped = cats.stream().collect(Collectors.groupingBy(String::length, Collectors.toSet()));
        // usage of partitioningBy
        Map<Boolean, List<String>> partition = cats.stream().collect(Collectors.partitioningBy(value -> value.length() > 3));

        // collect to set
        Set<String> set = cats.stream().filter(combined).collect(Collectors.toSet());
        // Java 10, to unmodifiable set i.e. changes to unmSet throws UnsupportedOperationException
        Set<String> unmSet = cats.stream().filter(combined).collect(Collectors.toUnmodifiableSet());
        // usage of collectingAndThen()
        Set<String> unmSet2 = cats.stream().filter(combined).collect(Collectors.collectingAndThen(Collectors.toSet(), Set::copyOf));

        // collect to map. But it doesn't filter duplicates hence throws IllegalStateException (it's called key collision)
        // Map<String, Integer> map = cats.stream().filter(combined).collect(Collectors.toMap(Function.identity(), String::length));

        /* 
        collect to map. alternative to above approach but filter duplicates (handles key collision).
        toMap() 3rd parameter is BinaryOperator where we can specify how we want collisions to be handled.
        
        Here we just picked any of these two colliding values because we know that the same strings will 
        always have the same lengths, too.
        */
        Map<String, Integer> maptoFilterDuplicates = cats.stream()
                                                            .filter(combined)
                                                            .collect(
                                                                Collectors.toMap(
                                                                    Function.identity(), 
                                                                    String::length, 
                                                                    (item, identicalItem) -> item)
                                                                    );

        // Java 10, collect to unmodifiable map i.e. changes to unmMap throws UnsupportedOperationException.
        Map<String, Integer> unmMap = cats.stream()
                                                            .filter(combined)
                                                            .collect(
                                                                Collectors.toMap(
                                                                    Function.identity(), 
                                                                    String::length, 
                                                                    (item, identicalItem) -> item)
                                                                    );        

        /*
        teeing means if a collector tees the given stream towards two different directions, it's called teeing.

        teeing before Java 12:
        find the max and min numbers from a given stream.
        using two different collectors and then combining the result of those two to create something meaningful. 
        Before Java 12, in order to cover such use cases, we had to operate on the given Stream twice, store the 
        intermediate results into temporary variables and then combine those results afterward.
        */
        List<Integer> numbers = Arrays.asList(42, 4, 2, 24);
        // Optional<Integer> min = numbers.stream().collect(Collectors.minBy(Integer::compareTo));
        // Optional<Integer> max = numbers.stream().collect(Collectors.maxBy(Integer::compareTo));
        
        /* 
        teeing from Java 12:
        Java 12 offers a built-in collector that takes care of these steps on our behalf: all we have to do is 
        provide the two collectors and the combiner function.
        */
        Range range = numbers.stream()
            .collect(Collectors.teeing(Collectors.minBy(Integer::compareTo), 
                            Collectors.maxBy(Integer::compareTo), (min, max) -> new Range(min.orElse(null), max.orElse(null))));

    }

    /**
     * Different ways of using map() method
     */
    private static void map() {
        List<String> cats = Arrays.asList("tiger", "cat", "rat", "bat", "TIGER", "Tiger", "leopard", "leopard");
        List<Integer> namesLength = cats.stream().map(String::length).collect(Collectors.toList());
        List<String> changedCats = cats.stream().map(value -> "A " + value).collect(Collectors.toList());
        System.out.println(namesLength);
        System.out.println(changedCats);
    }


    /**
     * Different ways of using reduce() method. reduce referred as fold, aggregation.
     */
    private static void reduce() {
        Stream<Integer> primes = Stream.of(2, 3, 5, 7, 11, 13, 17, 19, 23);
        Integer sumPrimes = primes.reduce(0, (x, y) -> {return x + y;});
        System.out.println(sumPrimes);
    }

    /**
     * Represents a closed range of numbers between min and max, both inclusive.
     */
    private static class Range {

        private final Integer min;

        private final Integer max;

        Range(Integer min, Integer max) {
            this.min = min;
            this.max = max;
        }

        Integer getMin() {
            return min;
        }

        Integer getMax() {
            return max;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Range range = (Range) o;
            return Objects.equals(getMin(), range.getMin()) && Objects.equals(getMax(), range.getMax());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getMin(), getMax());
        }

        @Override
        public String toString() {
            return "Range{" + "min=" + min + ", max=" + max + '}';
        }
    }    
}

