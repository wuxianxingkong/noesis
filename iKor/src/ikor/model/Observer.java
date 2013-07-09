package ikor.model;

/**
 * Observer design pattern (http://en.wikipedia.org/wiki/Observer_pattern), to be used with the Subject class.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public interface Observer<T>
{
	public void update (Subject<T> subject, T object);
}
