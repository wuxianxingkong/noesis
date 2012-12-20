package sandbox.parallel.example;

import sandbox.parallel.Parallel;
import sandbox.parallel.Kernel;
import sandbox.parallel.Scheduler;
import sandbox.parallel.scheduler.*;

import ikor.util.Benchmark;

public class NestedMapExample implements Kernel
{
	public static final int WIDTH = 16;
	public static final int DEPTH = 2; // width^depth leaves 
	
	// Tests @ i5
	
	// Tile width = 1
	// 2^4 [1s], 2^10 [1s], 2^12 [1.7s], 2^14 [2.4-5.5s], 2^16 [5.5-20.3s, 1.3GB RAM, 64k leaves]
	// 16^1 [1s], 16^2 [1s], 16^3 [1.7s], 16^4 [5.4-10.4s, 1.4GB, 64k leaves] 

	// Tile width = 16
	// 2^0 [2s], 2^1 [2s], 2^2 [2.5s], 2^3 [5s], 2^4 [8s], 2^4 [10s]
	// 16^0 [9s], 16^1 [10s], 16^2
	
	
	int width = WIDTH;
	int depth = DEPTH;
	int result[];
	
	public NestedMapExample (int width, int depth)
	{
		this.width = width;
		this.depth = depth;
		this.result = new int[width];
	}
	
	public Object call (int index) 
	{
		if (depth>1) {
			
			try {
				decomposeTask(index);
			} catch (Exception error) {
				error.printStackTrace();
			}
			
		} else {
			doSomething(index);
		}
		
		return null;
    }
	
	private void decomposeTask (int index) 
	{
		NestedMapExample task = new NestedMapExample(width,depth-1);
		
		Parallel.map (task, 0, width-1);
		
		result[index] = 0;
		
		for (int i=0; i<width; i++)
            result[index] += task.result[i];
	}

	private void doSomething (int index)
	{
		int value = (int)(1000*Math.random());
		
		try {
			Thread.sleep(100+value);
		} catch (Exception error) {
			error.printStackTrace();
		}

		result[index] = 1;
	}
	

	
	public static void main (String[] args) 
	{
		Benchmark chrono = new Benchmark();

		chrono.start();
		
		Scheduler.set ( new ThreadPoolScheduler() );	
		//Scheduler.set ( new FutureScheduler(64) );	
		//Scheduler.set ( new WorkStealingScheduler(16) );	// Not suitable for this example!!!
		//Scheduler.set ( new SequentialScheduler() );
		
		Parallel.setTileWidth(1); // vs. 16
		
		Scheduler scheduler = Scheduler.get();
		
		NestedMapExample task = new NestedMapExample(WIDTH,DEPTH);
		
		Parallel.map (task, 0, WIDTH-1);
		
		int result = 0;
		
		for (int i=0; i<WIDTH; i++)
            result += task.result[i];
		
		System.out.println("Result: "+result);
	
		scheduler.shutdown();

		chrono.stop();

		System.out.println("Time: "+chrono);
	}
}             