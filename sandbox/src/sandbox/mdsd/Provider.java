package sandbox.mdsd;

/**
 * Generic interface for data providers.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 *
 * @param <K> Key. 
 * @param <D> Provided data type.
 */
public interface Provider<K,D> 
{
	public D get (K key);
}
