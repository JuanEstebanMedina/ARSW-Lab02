package edu.eci.arsw.primefinder;

public class Main {

	public static void main(String[] args) {

		PrimeFinder primeFinder = new PrimeFinder();
		primeFinder.checkPrimes(0, 300000000, 3);
	}
}
