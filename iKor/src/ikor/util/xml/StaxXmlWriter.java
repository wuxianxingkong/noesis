package ikor.util.xml;

import java.io.*;
import javax.xml.stream.*;

/**
 * StAX (Streaming API for XML processing)
 * 
 * @author Fernando Berzal
 */

public class StaxXmlWriter implements XmlWriter {

	private OutputStream    out;
	private XMLStreamWriter writer;
	
	/// Constructors
	
	public StaxXmlWriter (OutputStream out) 
		throws IOException, XMLStreamException
	{
		XMLOutputFactory factory = XMLOutputFactory.newInstance();

		this.out = out;
		
		writer = factory.createXMLStreamWriter(out, "ISO-8859-1");		
		writer.writeStartDocument("ISO-8859-1", "1.0");
	}
	
	public StaxXmlWriter (String filename)
		throws IOException, XMLStreamException
	{
		this ( new FileOutputStream(filename) );
	}
	
	
	// Ancillary routines
	
	public String normalize (String original)
	{
		if (original!=null)
			return original.toLowerCase();
		else
			return null;
	}
	
	// Element writer
	
	
	public void close() 
	{
		try {
			writer.writeEndDocument();
			writer.flush();
			writer.close();
			out.close();
		} catch (Exception error) {
			System.err.println("StAX Error - "+ error);
			error.printStackTrace();
		}
	}

	public void end(String id)
	{
		try {
			writer.writeEndElement();
		} catch (Exception error) {
			System.err.println("StAX Error - "+ error);
			error.printStackTrace();
		}
	}

	public void start(String id) 
	{
		try {
			writer.writeStartElement(normalize(id));
		} catch (Exception error) {
			System.err.println("StAX Error - "+ error);
			error.printStackTrace();
		}
	}

	public void write(Object object) 
	{	
		try {
			writer.writeCharacters(object.toString());  // WriteCData ???
		} catch (Exception error) {
			System.err.println("StAX Error - "+ error);
			error.printStackTrace();
		}
	}

	public void write(String id, Object object) 
	{
		try {
			writer.writeAttribute(normalize(id), object.toString());
		} catch (Exception error) {
			System.err.println("StAX Error - "+ error);
			error.printStackTrace();
		}
	}
	
	public void comment(String comment) 
	{
		try {
			writer.writeComment(comment);
		} catch (Exception error) {
			System.err.println("StAX Error - "+ error);
			error.printStackTrace();
		}
	}

}
