package sandbox.parallel.scheduler;

import java.util.concurrent.*;

import sandbox.parallel.MockFuture;
import sandbox.parallel.Scheduler;
import sandbox.parallel.Task;

/**
 * Simple fixed-size thread pool scheduler.
 * WARNING: Does NOT support task composition, since all threads in the pool might eventually become blocked while waiting for Future's values.
 * NOT TO BE USED when the number of waiting threads might be larger than the thread pool size!!!
 * 
 * @author Fernando Berzal
 */

public class FutureScheduler extends Scheduler 
{
	private   ExecutorService        executor;
	protected int                    nthreads;


	public FutureScheduler (int nthreads)
	{
		this.nthreads = nthreads;

		if (nthreads>0) {
			this.executor = Executors.newFixedThreadPool(nthreads);
		}
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

