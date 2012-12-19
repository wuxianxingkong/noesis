package sandbox.parallel.example;

import sandbox.parallel.Parallel;

import sandbox.parallel.Kernel;
import sandbox.parallel.Scheduler;
import sandbox.parallel.scheduler.*;

import ikor.util.Benchmark;


public class SAXPY implements Kernel<Double>
{
	private static final int SIZE = 16777216; // 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216 OK (ms)
	                                          // 33554432 -> OutOfMemoryError: Java heap space

	private double a;
	private double x[];
	private double y[];
		
	public SAXPY (double a, double x[], double y[])
	{
		this.x = x;
		this.y = y;
		this.a = a;
	}
	
	@Override
	public Double call(int index) 
	{
		y[index] = a*x[index] + y[index];
		
		return y[index];
	}	


	public static void main (String[] args) 
	{
		
		Scheduler.set ( new ThreadPoolScheduler() );	
		//Scheduler.set ( new FutureScheduler(16) );	
		//Scheduler.set ( new SequentialScheduler() );
		
		Parallel.setDecompositionDepth(4);
		Parallel.setTileWidth(16);
		
		
		// Input data
		
		double a;
		double x[] = new double[SIZE];
		double y[] = new double[SIZE];
		double r[] = new double[SIZE];
		
		a = Math.random();
		
		for (int i=0; i<SIZE; i++) {
			x[i] = Math.random();
			y[i] = Math.random();
			r[i] = a*x[i] + y[i];
		}
		
		
		// Computation

		Benchmark chrono = new Benchmark();

		chrono.start();
		
		SAXPY saxpy = new SAXPY(a,x,y);
		
		Parallel.map(saxpy, 0, SIZE-1);
		
		chrono.stop();


		// Result
		
		int errors = 0;
		
		for (int i=0; i<SIZE; i++){
			// System.out.println("y["+i+"] = "+saxpy.y[i]);		
			if (saxpy.y[i] != r[i])
				errors ++;
		}
		
		if (errors>0)
			System.err.println(errors+" errors in SAXPY computation.");
		else
			System.out.println("OK");

		System.out.println("Time: "+chrono);
		
		Scheduler.get().shutdown();
	}

}             