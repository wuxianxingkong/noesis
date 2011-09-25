package ikor.util.xml;

// Title:       Graph data provider
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
* Graph data writer
*/
public interface XmlWriter {

	public void close();
	
	// Graph data
	
	public void write (Object object);
	
	public void write (String id, Object object);
	
	// Nesting
	
	public void start (String id);
	
	public void end (String id);
	
	// Comments
	
	public void comment (String comment);
	
	
}
