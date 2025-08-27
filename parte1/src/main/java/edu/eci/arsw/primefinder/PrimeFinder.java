package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinder {
    
    private List<PrimeFinderThread> primeFinderThreadList = new LinkedList<>();

    public void checkPrimes(int a, int b, int threadCount){
        
        int primesCount = 0;

        int size = (b-a);
		int range = size / threadCount;
		
        for (int i = 0; i < threadCount; i++) {
            int start = i * range;
            int end = (i == threadCount - 1) ? size - 1 : (i + 1) * range - 1;
            PrimeFinderThread thread = new PrimeFinderThread(start, end);
            primeFinderThreadList.add(thread);
            thread.start();
            try {
                thread.sleep(5000); 
            } catch (InterruptedException e) {
                // Maneja la excepción si el hilo es interrumpido durante la espera
                System.out.println("El hilo fue interrumpido.");// Restablece el estado de interrupción
            }
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

        System.out.println("Cantidad de primos encontrados: " + primesCount);



    }
}
