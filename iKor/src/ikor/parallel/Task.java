package ikor.parallel;

import ikor.parallel.threading.MockFuture;

import java.util.concurrent.Future;

public abstract class Task<T> implements ITask<T>
{
	private Future<T> future;
	
	// Callable interface
	
	public abstract T call();

	// Getter & setter

	public T getResult() 
	{
		try {
			return future.get(); // throws InterruptedException, ExecutionException
		} catch (Exception error) {
			error.printStackTrace();
			return null;
		}
	}

	public void setResult(T result) 
	{
		this.future = new MockFuture(result);
	}

	public void setFuture(Future<T> future) 
	{
		this.future = future;
	}
	
	public Future<T> getFuture ()
	{
		return this.future;
	}

}
