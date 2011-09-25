package adt;

// Title:       Collection interface
// Version:     2.0
// Copyright:   2006
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org


// Collection 
// ----------

// Version history
// 1.0 - May'2004 - Set of values
// 2.0 - Dec'2006 - Generics

/**
 * Collection ADT interface
 *
 * @author Fernando Berzal
 */
public interface Collection<T> 
{
	int size();

    T get(int n);
}
