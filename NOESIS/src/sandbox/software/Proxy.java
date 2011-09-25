package sandbox.software;

import ikor.util.xml.XmlWriter;

// Title:       Graph element proxy (lazy load)
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Graph element proxy for lazy load (ref. PoEAA, page 200)
 */

public class Proxy extends Element {
	
	private Element element;
	
	public Proxy (Element element)
	{		
		this.element = element;
	}
	
	public Proxy ()
	{
	}

	public Element getElement() {
		return element;
	}
	
	public void setElement (Element element)
	{
		this.element = element;
	}
	
	@Override
	public void output (XmlWriter writer)
	{
		if (element!=null)
			element.output(writer);
	}

	@Override
	public Proxy getProxy() 
	{
		return this;
	}
}
