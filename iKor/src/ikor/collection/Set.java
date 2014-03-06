package ikor.collection;

//Title:       iKor Collection Framework
//Version:     1.0
//Copyright:   2014
//Author:      Fernando Berzal Galiano
//E-mail:      berzal@acm.org

/**
 * Generic set interface.
 * 
 * @author Fernando Berzal
 */
public interface Set<T> extends Collection<T> 
{
	public Set<T> union (Set<T> other);
	
	public Set<T> intersection (Set<T> other);
	
	public Set<T> difference (Set<T> other);
}
