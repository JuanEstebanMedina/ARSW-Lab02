package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread {

	int start;
	int end;

	private List<Integer> primes = new LinkedList<>();
	private boolean isPaused = false;

	public PrimeFinderThread(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	public void run() {
		for (int i = start; i <= end; i++) {
			synchronized (this) {
				while (isPaused) {
					try {
						wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
			if (isPrime(i)) {
				primes.add(i);
			}
		}
	}

	boolean isPrime(int n) {
		if (n % 2 == 0)
			return false;
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	public List<Integer> getPrimes() {
		return primes;
	}

	public synchronized void pauseThread() {
		isPaused = true;
	}

	public synchronized void resumeThread() {
		isPaused = false;
		notifyAll();
	}
}
