package noesis;

//Title:       Network helper class
//Version:     1.0
//Copyright:   2011
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

/**
 * Network ADT: Network link.
 */

public class Link<E> 
{
	private int source;
	private int destination;
	private E   content;
	
	public Link (int source, int destination, E content)
	{
		this.source = source;
		this.destination = destination;
		this.content = content;
	}
	
	public final int getSource ()
	{
		return source;
	}
	
	public final int getDestination ()
	{
		return destination;
	}
	
	public final E getContent ()
	{
		return content;
	}
	
	// toString

	public String toString ()
	{
		return getSource() + " -> " + getDestination() + " (" + getContent() + ")";
	}

}
