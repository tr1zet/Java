package src.Tasks;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Task2 {
    public static void execute() {
        System.out.println("\n   ЗАДАНИЕ 2    ");

        PrimesGenerator generator = new PrimesGenerator(20);
        PrimesGeneratorTest test = new PrimesGeneratorTest();
        test.testGenerator(generator);
    }
}

class PrimesGenerator implements Iterator<Integer> {
    private int count;
    private int generated;
    private int current = 2;

    public PrimesGenerator(int count) {
        this.count = count;
        this.generated = 0;
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    @Override
    public boolean hasNext() {
        return generated < count;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Достигнут лимит простых чисел");
        }
        while (!isPrime(current)) {
            current++;
        }
        int prime = current;
        current++;
        generated++;
        return prime;
    }
}

class PrimesGeneratorTest {
    public void testGenerator(PrimesGenerator generator) {
        System.out.println("Простые числа в прямом порядке:");
        while (generator.hasNext()) {
            System.out.print(generator.next() + " ");
        }
        System.out.println();

        // Для обратного порядка нужно сохранить числа
        PrimesGenerator gen2 = new PrimesGenerator(20);
        java.util.List<Integer> primes = new java.util.ArrayList<>();
        while (gen2.hasNext()) {
            primes.add(gen2.next());
        }

        System.out.println("Простые числа в обратном порядке:");
        for (int i = primes.size() - 1; i >= 0; i--) {
            System.out.print(primes.get(i) + " ");
        }
        System.out.println();
    }
}