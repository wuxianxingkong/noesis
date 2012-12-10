package sandbox.parallel;

import java.util.concurrent.Future;


public abstract class FutureTask<T> extends Task<T>
{
	private Future<T> future;
	
	// Callable interface
	
	public abstract T call();
	
	// Getter & setter
	
	@Override
	protected void setResult (T result)
	{
		this.future = new DummyFuture(result);
	}
	
	protected void setFuture (Future<T> future)
	{
		this.future = future;
	}

	@Override
	protected T getResult () 
	{
		try {
			return future.get(); // throws InterruptedException, ExecutionException
		} catch (Exception error) {
			error.printStackTrace();
			return null;
		}
	}
}
