package algorithms;

import java.util.Arrays;
import java.util.LinkedList;

public class Primes {
    public static void main(String[] args) {
        var primes = sieveOfEratosthenes(50);
        print(primes);
    }

    /**
     * Generate prime numbers using Sieve of Eratosthenes algorithm which is efficient
     * Time complexity: O(n log n)
     */
    static LinkedList<Integer> sieveOfEratosthenes(int n) {
        boolean prime[] = new boolean[n + 1];
        Arrays.fill(prime, true);

        for (int p = 2; p * p <= n; p++) {
            if (prime[p]) {
                for (int i = p * 2; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }

        var primeNumbers = new LinkedList<Integer>();
        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }

    static void print(LinkedList<Integer> primes) {
        for (int prime : primes) 
            System.out.print(prime + " ");
    }
}
