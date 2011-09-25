package sandbox.software;

import ikor.collection.Collection;
import ikor.util.xml.XmlWriter;

//Title:       Generic graph element
//Version:     1.0
//Copyright:   2006
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

/**
 * Base component
 * 
 * @author Fernando Berzal
 * @version 1.0 December'2006
 */

public abstract class Element {

	// TODO Reflective inspection of properties (Dictionary<String,Object>)
	
	public Element ()
	{
	}
	
	
	// Output
	
	public abstract void output (XmlWriter writer);
	
	protected final void outputCollection (XmlWriter writer, Collection<? extends Element> collection)
	{
		for (Element e: collection) {
			e.output(writer);
		}
	}	
	
	// Proxy
	// e.g. return new XxxProxy(this);
	
	public abstract Proxy getProxy ();
}
