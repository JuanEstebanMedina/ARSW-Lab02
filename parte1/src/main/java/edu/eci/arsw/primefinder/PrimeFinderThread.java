package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

	int a,b;
	
	private List<Integer> primes=new LinkedList<>();
	private boolean isPaused = false;
	
	public PrimeFinderThread(int a, int b) {
		super();
		this.a = a;
		this.b = b;
	}

    @Override
	public void run(){
		for (int i=a;i<=b;i++){
			synchronized (this){
				while (isPaused){
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if (isPrime(i)){
				primes.add(i);
				// System.out.print(i + " ");
			}
		}
		
		
	}
	
	boolean isPrime(int n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {

	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

	public List<Integer> getPrimes() {
		return primes;
	}
	
	public synchronized void pauseThread(){
		isPaused = true;
	}
	
	public synchronized void resumeThread(){
		
		isPaused = false;
		notifyAll();
	}
	
}
