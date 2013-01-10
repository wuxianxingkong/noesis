package ikor.parallel.scheduler;

import java.util.concurrent.*;

import ikor.parallel.Scheduler;
import ikor.parallel.ITask;

/**
 * Work-stealing scheduler based on the Java Fork/Join framework.
 * 
 * Warning!!! http://coopsoft.com/ar/CalamityArticle.html
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
	public void schedule (ITask task)
	{
        Future future = pool.submit(task);
        
        task.setFuture(future);
        
        // TODO WorkStealingTask.fork() vs. ForkJoinPool.submit()
        // 
        // e.g.
        //
        // if (current task is a ForkJoinTask) 
		//    WorkStealingTask.fork();
        // else
        // 	  ForkJoinTask wt = new WorkStealingTask(task);
		//	  pool.submit(wt);
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
		
	// TODO Direct task invocation
	// 
	// @Override
	// public void invoke (ITask task)
	// {		
    // 	  if (current task is a ForkJoinTask) 
	//    	 WorkStealingTask.invoke();
    //    else
    // 	     ForkJoinTask wt = new WorkStealingTask(task);
	//	     pool.invoke(wt);
	// }

}

