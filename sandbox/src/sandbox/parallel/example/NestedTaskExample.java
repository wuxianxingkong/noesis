package sandbox.parallel.example;

import sandbox.parallel.Task;
import sandbox.parallel.Scheduler;

import sandbox.parallel.scheduler.*;

import ikor.util.Benchmark;

public class NestedTaskExample extends Task<Integer>
{
	public static final int WIDTH = 2;
	public static final int DEPTH = 10;  // width^depth tasks
	
	int width = WIDTH;
	int depth = DEPTH;
	
	public NestedTaskExample (int width, int depth)
	{
		this.width = width;
		this.depth = depth;
	}
	
	public Integer call () 
	{
		if (depth>1) {
			
			try {
				return decomposeTask();
			} catch (Exception error) {
				error.printStackTrace();
				return -1;
			}
			
		} else {
			return doSomething();
		}
    }
	
	private int decomposeTask () 
	{
		Scheduler scheduler = Scheduler.get();

		System.out.println("+ Spawning tasks");

		NestedTaskExample task[] = new NestedTaskExample[width];
		
		for (int i=0; i<width; i++){
			task[i] = new NestedTaskExample(width,depth-1);
			scheduler.schedule(task[i]);
		}

		// Asynchronous join

		JoinerTask joiner = new JoinerTask();
		joiner.setTasks(task);
		scheduler.schedule(joiner);

		return joiner.getResult();		
	}

	private int doSomething()
	{
		int value = (int)(1000*Math.random());
		
		try {
			Thread.sleep(100+value);
		} catch (Exception error) {
			error.printStackTrace();
		}

		return width;
	}
	
	class JoinerTask extends Task<Integer>
	{
		Task<Integer> tasks[];
		
		public void setTasks (Task<Integer> tasks[])
		{
			this.tasks = tasks;
		}
		
		public Integer call ()
		{
			System.out.println("- Joining tasks");
			
			int total = 0;
			int result;
			
			try {

				for (int i=0; i<width; i++){
					result = tasks[i].getResult();
					total += result;
				}

			} catch (Exception error) {
			    error.printStackTrace();
			}
			
			return total;
		}
	}

	
	public static void main (String[] args) 
	{
		Benchmark chrono = new Benchmark();

		chrono.start();
		
		Scheduler.set ( new ThreadPoolScheduler() );	
		//Scheduler.set ( new FutureScheduler(64) );	
		//Scheduler.set ( new WorkStealingScheduler(16) );	// Not suitable for this example!!!
		//Scheduler.set ( new SequentialScheduler() );
		
		Scheduler scheduler = Scheduler.get();
		
		NestedTaskExample task = new NestedTaskExample(WIDTH,DEPTH);
		scheduler.schedule(task);
		
		int result = task.getResult();

		System.out.println("Result: "+result);
	
		scheduler.shutdown();

		chrono.stop();

		System.out.println("Time: "+chrono);
	}
}             