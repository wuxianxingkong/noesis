package sandbox.software;



// Title:       Graph data provider
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Graph data provider
 */
public interface ElementProvider<E extends Element> {
	
	/**
	 * Provides the desired element
	 * @param proxy Proxy to the desired element
	 * @return Desired element
	 */

	public E getElement (Proxy proxy);
	
}
