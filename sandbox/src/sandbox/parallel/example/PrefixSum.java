package sandbox.parallel.example;

import sandbox.parallel.*;
import sandbox.parallel.scheduler.*;
import sandbox.parallel.combiner.*;
	
import ikor.util.Benchmark;


public class PrefixSum implements Getter<Double>, Setter<Double>
{
	private static final int SIZE = 16777216; // 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432 OK (ms)
	                                          // 67108864 -> OutOfMemoryError: Java heap space

	private double n[];
	private double s[];
		
	public PrefixSum (double n[])
	{
		this.n = n;
		this.s = new double[n.length];
	}
	
	public Double get (int index) 
	{
		return n[index];
	}	

	public void set (int index, Double sum) 
	{
		s[index] = sum;
	}	
	
	public void compute ()
	{
		Scan.scan(this, new AddCombiner(), this, 0, n.length-1 );
	}

	// Test program
	
	public static void main (String[] args) 
	{
		
		Scheduler.set ( new WorkStealingScheduler(8) ); // 4 (i5) vs. 8 (i7)
		//Scheduler.set ( new ThreadPoolScheduler() );	
		//Scheduler.set ( new FutureScheduler(16) );	
		//Scheduler.set ( new SequentialScheduler() );
		
		Parallel.setDecompositionDepth(4);
		Parallel.setTileWidth(16);
		
		
		// Input data
		
		double x[] = new double[SIZE];
				
		for (int i=0; i<SIZE; i++) {
			x[i] = i; // 1; // Math.random();
		}
		
		
		// Computation

		Benchmark chrono = new Benchmark();

		chrono.start();
		
		PrefixSum sum = new PrefixSum(x);

		sum.compute();
		
		chrono.stop();


		// Result
		
		int errors = 0;
		
		for (int i=0; i<SIZE; i++){
			//System.out.println("sum["+i+"] = "+sum.s[i]);		
			if (sum.s[i] != (i/2.0)*(i+1)) {
				errors ++;
			}
		}
		
		if (errors>0)
			System.err.println(errors+" errors in scan computation.");
		else
			System.out.println("OK");

		System.out.println("Time: "+chrono);
		
		Scheduler.get().shutdown();
	}

}             