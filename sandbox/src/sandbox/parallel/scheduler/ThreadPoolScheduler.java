package sandbox.parallel.scheduler;

import java.util.concurrent.*;

import sandbox.parallel.MockFuture;
import sandbox.parallel.Scheduler;
import sandbox.parallel.Task;

/**
 * Simple thread pool scheduler.
 * WARNING: Might suffer from oversubscription (each use of parallelism might create new threads)
 * 
 * @author Fernando Berzal
 */

public class ThreadPoolScheduler extends Scheduler 
{
	private ExecutorService executor;


	public ThreadPoolScheduler ()
	{
		this.executor = Executors.newCachedThreadPool();
	}


	/* (non-Javadoc)
	 * @see sandbox.parallel.Scheduler#schedule(sandbox.parallel.Task)
	 */
	@Override
	public void schedule (Task task)
	{
		Future future;

		if (executor!=null)
			future = executor.submit(task);
		else
			future = new MockFuture(task.call());

		task.setFuture(future);
	}


	/* (non-Javadoc)
	 * @see sandbox.parallel.Scheduler#shutdown()
	 */
	@Override
	public void shutdown() 
	{
		if (executor!=null)
			executor.shutdown(); 
	}
	
}

