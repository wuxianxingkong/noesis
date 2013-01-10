package ikor.parallel.threading;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;

import ikor.parallel.ITask;

public class WorkStealingTask<T> extends ForkJoinTask<T> implements ITask<T>
{
	private ITask<T> task;
	private T        result;
	
	public WorkStealingTask (ITask task)
	{
		this.task = task;
	}

	@Override
	public T call() 
	{
		result = task.call();
		
		return result;
	}
	
	@Override
	protected boolean exec() 
	{
		result = task.call();
		return true;
	}

	@Override
	public T getRawResult() 
	{
		return result;
	}

	@Override
	protected void setRawResult (T value) 
	{
		result = value;
	}


	@Override
	public T getResult() 
	{
		return result;
	}

	@Override
	public void setResult (T result) 
	{
		this.result = result;		
	}

	@Override
	public void setFuture (Future<T> future) 
	{
		// Nothing to do: A ForkJoinTask is already a Future
	}

	@Override
	public Future<T> getFuture() 
	{
		return this;
	}
	
}

