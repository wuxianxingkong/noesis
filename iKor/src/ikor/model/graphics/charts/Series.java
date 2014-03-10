package ikor.model.graphics.charts;

import ikor.math.Vector;

public class Series 
{
	Vector x;
	Vector y;
	
	
	public Series (Vector x, Vector y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Series (Vector data)
	{
		this.x = null;
		this.y = data;
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
