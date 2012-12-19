package sandbox.parallel.scheduler;

import sandbox.parallel.Scheduler;
import sandbox.parallel.Task;


public class SequentialScheduler extends Scheduler 
{
	public SequentialScheduler ()
	{
	}

	
	/* (non-Javadoc)
	 * @see sandbox.parallel.Scheduler#schedule(sandbox.parallel.Task)
	 */
	@Override
	public void schedule (Task task)
	{
		Object result = task.call();
				
		task.setResult(result);				
	}


    /* (non-Javadoc)
	 * @see sandbox.parallel.Scheduler#shutdown()
	 */
    @Override
	public void shutdown() 
	{
		// Nothing to do 
	}

 
}

