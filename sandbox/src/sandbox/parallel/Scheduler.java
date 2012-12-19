package sandbox.parallel;

import sandbox.parallel.scheduler.ThreadPoolScheduler;


public abstract class Scheduler 
{
	private static Scheduler scheduler = new ThreadPoolScheduler();
	
	// Current scheduler
	
	public static Scheduler get ()
	{
		return scheduler;
	}
	
	public static void set (Scheduler newScheduler)
	{
		scheduler = newScheduler;
	}
	
	
	// Abstract interface
	
	public abstract void schedule(Task task);

	public abstract void shutdown();
}