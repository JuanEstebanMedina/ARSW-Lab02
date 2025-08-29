package edu.eci.arsw.primefinder;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PrimeFinder {

    private List<PrimeFinderThread> primeFinderThreadList = new LinkedList<>();

    public void checkPrimes(int a, int b, int threadCount) {

        int primesCount = 0;

        int size = (b - a);
        int range = size / threadCount;

        for (int i = 0; i < threadCount; i++) {
            int start = i * range;
            int end = (i == threadCount - 1) ? size - 1 : (i + 1) * range - 1;
            PrimeFinderThread thread = new PrimeFinderThread(start, end);
            primeFinderThreadList.add(thread);
            thread.start();
        }

        System.out.println("Threads executing...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("5 seconds passed.");

        for (PrimeFinderThread thread : primeFinderThreadList) {
            thread.pauseThread();
            int primes = thread.getPrimes().size();
            System.out.println("Thread found: " + primes + " primes.");
            primesCount += thread.getPrimes().size();
        }

        System.out.println("Number of primes found after 5 seconds: " + primesCount);
        primesCount = 0;
        System.out.println("Threads paused. Press ENTER to continue...");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (PrimeFinderThread thread : primeFinderThreadList) {
            thread.resumeThread();
        }

        for (PrimeFinderThread thread : primeFinderThreadList) {
            try {
                thread.join();
                primesCount += thread.getPrimes().size();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Error en el hilo");
            }
        }

        System.out.println("Total primes found: " + primesCount);

    }
}
