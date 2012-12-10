package sandbox.parallel;


public abstract class Scheduler<T extends Task> 
{
	private static Scheduler scheduler;
	
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
	
	public abstract void schedule(T task);

	public abstract void shutdown();
}