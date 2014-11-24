package ikor.model.graphics.charts;

import ikor.math.Vector;

public class Series 
{
	private String id;
	private Vector x;
	private Vector y;
	
	public Series (String id, Vector x, Vector y)
	{
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public Series (Vector x, Vector y)
	{
		this(null,x,y);
	}
	
	public Series (String id, Vector data)
	{
		this(id,null,data);
	}
	
	public Series (Vector data)
	{
		this(null,null,data);
	}

	public String id ()
	{
		return id;
	}
	
	public int size ()
	{
		return y.size();
	}
	
	public Vector getX ()
	{
		return x;
	}
	
	public double getX (int i)
	{
		return (x!=null)? x.get(i): i;
	}
	
	public Vector getY ()
	{
		return y;
	}
	
	public double getY (int i)
	{
		return y.get(i);
	}
}
