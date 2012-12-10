package sandbox.parallel;


public class SequentialScheduler<T extends Task> extends Scheduler<T> 
{
	public SequentialScheduler ()
	{
	}

	
	/* (non-Javadoc)
	 * @see sandbox.parallel.Scheduler#schedule(sandbox.parallel.Task)
	 */
	@Override
	public void schedule (T task)
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

