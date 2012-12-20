package ikor.parallel.scheduler;

import java.util.concurrent.*;

import ikor.parallel.Scheduler;
import ikor.parallel.Task;

/**
 * Simple thread pool scheduler.
 * WARNING: Might suffer from oversubscription (each use of parallelism might create new threads)
 * 
 * @author Fernando Berzal
 */

public class WorkStealingScheduler extends Scheduler 
{
	private ForkJoinPool pool;


	public WorkStealingScheduler (int parallelism)
	{
		this.pool = new ForkJoinPool(parallelism);
	}


	/* (non-Javadoc)
	 * @see sandbox.parallel.Scheduler#schedule(sandbox.parallel.Task)
	 */
	@Override
	public void schedule (Task task)
	{
		Future future = pool.submit(task);
		
		task.setFuture(future);
	}


	/* (non-Javadoc)
	 * @see sandbox.parallel.Scheduler#shutdown()
	 */
	@Override
	public void shutdown() 
	{
		if (pool!=null)
			pool.shutdown(); 
	}

	
}

