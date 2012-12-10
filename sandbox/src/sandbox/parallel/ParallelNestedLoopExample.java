package sandbox.parallel;

import java.util.concurrent.ExecutionException;

import ikor.util.Benchmark;

public class ParallelNestedLoopExample extends TaskKernel
{
	public static final int WIDTH = 2;
	public static final int DEPTH = 4;
	
	int width = WIDTH;
	int depth = DEPTH;
	int result[];
	
	public ParallelNestedLoopExample (int width, int depth)
	{
		this.width = width;
		this.depth = depth;
		this.result = new int[width];
	}
	
	public void call (int index) 
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
    }
	
	private void decomposeTask (int index) 
		throws InterruptedException, ExecutionException
	{
		ParallelNestedLoopExample task = new ParallelNestedLoopExample(width,depth-1);
		
		Parallel.map(0, width-1, task);
		
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
		throws InterruptedException, ExecutionException
	{
		Benchmark chrono = new Benchmark();

		chrono.start();
		
		Scheduler.set ( new ThreadPoolScheduler() );	
		//Scheduler.set ( new FutureScheduler(64) );	
		//Scheduler.set ( new SequentialScheduler() );
		
		Scheduler scheduler = Scheduler.get();
		
		ParallelNestedLoopExample task = new ParallelNestedLoopExample(WIDTH,DEPTH);
		
		Parallel.map (0, WIDTH-1, task);
		
		int result = 0;
		
		for (int i=0; i<WIDTH; i++)
            result += task.result[i];
		
		System.out.println("Result: "+result);
	
		scheduler.shutdown();

		chrono.stop();

		System.out.println("Time: "+chrono);
	}
}             