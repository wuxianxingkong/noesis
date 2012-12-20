package sandbox.parallel;

/**
 * Getter interface for collective parallel operations
 *  
 * @author Fernando Berzal
 *
 * @param <T> Base type
 */
public interface Getter<T> 
{	
	public T get (int index);
}
