package sandbox.parallel;

import java.util.concurrent.*;

/**
 * Simple thread pool scheduler.
 * WARNING: Might suffer from oversubscription (each use of parallelism might create new threads)
 * 
 * @author Fernando Berzal
 */

public class ThreadPoolScheduler<T extends FutureTask> extends Scheduler<T> 
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
	public void schedule (T task)
	{
		Future future;

		if (executor!=null)
			future = executor.submit(task);
		else
			future = new DummyFuture(task.call());

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

