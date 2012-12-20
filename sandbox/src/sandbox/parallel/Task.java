package sandbox.parallel;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import sandbox.parallel.threading.MockFuture;


public abstract class Task<T> implements Callable<T>, Serializable
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

}
