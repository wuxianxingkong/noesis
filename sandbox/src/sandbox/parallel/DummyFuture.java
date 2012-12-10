package sandbox.parallel;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class DummyFuture<V> implements Future<V>
{
	private V value;

	public DummyFuture (V value)
	{
		this.value = value;
	}

	public boolean cancel (boolean mayInterruptIfRunning) 
	{
		return false;
	}

	public V get ()
	{
		return value;
	}

	public V get(long timeout, TimeUnit unit)
	{
		return value;
	}

	public boolean isCancelled() 
	{
		return false;
	}
	
	public boolean isDone() 
	{
		return true;
	}
}
