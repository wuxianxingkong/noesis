package ikor.parallel;

/**
 * Combiner interface for collective operations (reduce & scan), e.g. addition, multiplication, and, or, xor, max, min...
 *  
 * @author Fernando Berzal
 *
 * @param <T> Base type
 */
public interface Combiner<T> 
{	
	public T identity ();
	
	public T combine (T first, T second);
}
