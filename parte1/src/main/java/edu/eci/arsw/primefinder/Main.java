package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class Main {

	private static List<Integer> primes = new LinkedList<>();

	public static void main(String[] args) {
		
		PrimeFinder primeFinder = new PrimeFinder();
		primeFinder.checkPrimes(0, 100000000, 3);
		
		
	}
	
}
