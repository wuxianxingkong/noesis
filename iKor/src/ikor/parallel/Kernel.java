package ikor.parallel;

public interface Kernel<T>
{
	// Callable-like interface
	
	public abstract T call (int index);

}
