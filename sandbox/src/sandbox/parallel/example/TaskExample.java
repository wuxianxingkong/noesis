package sandbox.parallel.example;

import sandbox.parallel.Scheduler;
import sandbox.parallel.Task;

import sandbox.parallel.scheduler.*;

import ikor.util.Benchmark;

/*
Tiempo de ejecución secuencial 
  t(1) = s + p
Tiempo de ejecución paralelo
  t(n) = s + p/n

Dados dos tiempos:
  t(1) = s + p
  t(n) = s + p/n

	 t(1)-t(n) = (n-1)/n · p => p = n ( t(1) - t(n) ) / (n-1)
	                         => s = t(1) - p
*/

public class TaskExample extends Task<Integer>
{
	private static final int TASKS = 16; // 16, 32, 64, 128, 256, 1024, 2048, 4096, 8192, 16384, 32768, 65536 OK
	
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
		//Scheduler.set ( new WorkStealingScheduler(16) );	// Not suitable for this example!!!
		//Scheduler.set ( new SequentialScheduler() );
		
		Scheduler scheduler = Scheduler.get();
		
		Task<Integer> task[] = new Task[TASKS];
		
		int result;
		int total = 0;

		for (int i=0; i<TASKS; i++){
			task[i] = new TaskExample();
			scheduler.schedule(task[i]);
		}

		for (int i=0; i<TASKS; i++){
			result = task[i].getResult();
			total += result;
			System.out.println("Task "+i+": "+result);
		}

		scheduler.shutdown();

		chrono.stop();

		System.out.println("Total: "+total);
		System.out.println("Time: "+chrono);
	}
}             