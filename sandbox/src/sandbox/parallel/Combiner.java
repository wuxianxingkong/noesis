package sandbox.parallel;

/**
 * Combiner interface for collective operations (reduce & scan), e.g. addition, multiplication, and, or, xor, max, min...
 *  
 * @author Fernando Berzal
 *
 * @param <T> Base type
 */
public interface Combiner<T> 
{	
	public abstract T identity ();
	
	public abstract T combine (T first, T second);
}
