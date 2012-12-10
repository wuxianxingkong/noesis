package sandbox.parallel;

import java.util.concurrent.Callable;


public abstract class Task<T> implements Callable<T>
{
	T result;
	
	// Callable interface
	
	public abstract T call();

	// Getter & setter
	
	protected void setResult (T result)
	{
		this.result = result;
	}

	protected T getResult () 
	{
		return result;
	}
}
