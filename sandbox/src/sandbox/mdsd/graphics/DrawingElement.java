package sandbox.mdsd.graphics;

import java.util.UUID;

public abstract class DrawingElement 
{
	private String       id;
	private Style style;
	private Style border;

	public DrawingElement ()
	{
		this.id = UUID.randomUUID().toString();
	}

	public DrawingElement (Style style)
	{
		this.id = UUID.randomUUID().toString();
		this.style = style;
	}

	public DrawingElement (String id, Style style)
	{
		this.id = id;
		this.style = style;
	}

	public DrawingElement (String id, Style style, Style border)
	{
		this.id = id;
		this.style = style;
		this.border = border;
	}
	
	public String getId() 
	{
		return id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public Style getStyle() 
	{
		return style;
	}

	public void setStyle(Style style) 
	{
		this.style = style;
	}
	
	public Style getBorder() 
	{
		return border;
	}

	public void setBorder(Style border) 
	{
		this.border = border;
	}
	
	public abstract int getX ();
	
	public abstract int getY ();
	
	public abstract int getWidth ();
	
	public abstract int getHeight();
}
