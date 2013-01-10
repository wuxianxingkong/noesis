package ikor.parallel;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface ITask<T> extends Callable<T>, Serializable
{
	// Callable interface
	
	public abstract T call();

	// Getter & setter

	public abstract T getResult(); 

	public abstract void setResult (T result); 
	
	public abstract Future<T> getFuture ();
	
	public abstract void setFuture (Future<T> future);	
	
}
