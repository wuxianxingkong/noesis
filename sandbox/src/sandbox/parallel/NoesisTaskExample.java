package sandbox.parallel;

import java.util.concurrent.ExecutionException;

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

public class NoesisTaskExample extends FutureTask<Integer>
{
	public Integer call ()
	{
		int value = (int)(1000*Math.random());
		
		try {
			Thread.sleep(100+value);
		} catch (Exception error) {
		}
		
		return 100+value;
	}

	private static int SIZE = 10;

	public static void main (String[] args) 
		throws InterruptedException, ExecutionException
	{
		Benchmark chrono = new Benchmark();

		chrono.start();
		
		Scheduler.set ( new ThreadPoolScheduler() );	
		//Scheduler.set ( new FutureScheduler(16) );	
		//Scheduler.set ( new SequentialScheduler() );
		
		Scheduler scheduler = Scheduler.get();
		
		FutureTask<Integer> task[] = new FutureTask[SIZE];
		
		int result;
		int total = 0;

		for (int i=0; i<SIZE; i++){
			task[i] = new NoesisTaskExample();
			scheduler.schedule(task[i]);
		}

		for (int i=0; i<SIZE; i++){
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