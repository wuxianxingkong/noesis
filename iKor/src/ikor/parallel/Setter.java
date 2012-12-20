package ikor.parallel;

/**
 * Setter interface for collective parallel operations
 *  
 * @author Fernando Berzal
 *
 * @param <T> Base type
 */
public interface Setter<T> 
{	
	public void set (int index, T object);
}
