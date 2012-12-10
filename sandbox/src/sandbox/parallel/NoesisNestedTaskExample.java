package sandbox.parallel;

import java.util.concurrent.ExecutionException;

import ikor.util.Benchmark;

public class NoesisNestedTaskExample extends FutureTask<Integer>
{
	public static final int WIDTH = 2;
	public static final int DEPTH = 6;
	
	int width = WIDTH;
	int depth = DEPTH;
	
	public NoesisNestedTaskExample (int width, int depth)
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
		throws InterruptedException, ExecutionException
	{
		Scheduler scheduler = Scheduler.get();

		System.out.println("+ Spawning tasks");

		NoesisNestedTaskExample task[] = new NoesisNestedTaskExample[width];
		
		for (int i=0; i<width; i++){
			task[i] = new NoesisNestedTaskExample(width,depth-1);
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
	
	class JoinerTask extends FutureTask<Integer>
	{
		FutureTask<Integer> tasks[];
		
		public void setTasks (FutureTask<Integer> tasks[])
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
		throws InterruptedException, ExecutionException
	{
		Benchmark chrono = new Benchmark();

		chrono.start();
		
		Scheduler.set ( new FutureScheduler(64) );	
		//Scheduler.set ( new SequentialScheduler() );
		
		Scheduler scheduler = Scheduler.get();
		
		NoesisNestedTaskExample task = new NoesisNestedTaskExample(WIDTH,DEPTH);
		scheduler.schedule(task);
		
		int result = task.getResult();

		System.out.println("Result: "+result);
	
		scheduler.shutdown();

		chrono.stop();

		System.out.println("Time: "+chrono);
	}
}             