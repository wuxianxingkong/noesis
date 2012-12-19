package sandbox.parallel.example;

import sandbox.parallel.Parallel;

import sandbox.parallel.Task;
import sandbox.parallel.Scheduler;
import sandbox.parallel.scheduler.*;

import ikor.util.Benchmark;


public class ForkJoinExample extends Task<Integer>
{
	private static final int TASKS = 65536; // 16, 32, 64, 128, 256, 1024, 2048, 4096, 8192, 16384, 32768, 65536 OK
	
	public Integer call ()
	{
		int value = (int)(1000*Math.random());
		
		try {
			Thread.sleep(100+value);
		} catch (Exception error) {
		}
		
		return 100+value;
	}


	public static void main (String[] args) 
	{
		Benchmark chrono = new Benchmark();

		chrono.start();
		
		Scheduler.set ( new ThreadPoolScheduler() );	
		//Scheduler.set ( new FutureScheduler(16) );	
		//Scheduler.set ( new SequentialScheduler() );
		
		Parallel.setDecompositionDepth(6); // 64k tasks @ i5: [linear] 8-13s [0-4] 8-13s [6] 5-15s [8] 4.6-17.3s [10] 5.2-86s !!!
		Parallel.setTileWidth(16); // vs. 1024
		
		Task<Integer> task[] = new Task[TASKS];
		
		int result;
		int total = 0;

		for (int i=0; i<TASKS; i++){
			task[i] = new TaskExample();
		}

		Parallel.forkjoin(task);

		for (int i=0; i<TASKS; i++){
			result = task[i].getResult();
			total += result;
			System.out.println("Task "+i+": "+result);
		}

		Scheduler.get().shutdown();

		chrono.stop();

		System.out.println("Total: "+total);
		System.out.println("Time: "+chrono);
	}
}             