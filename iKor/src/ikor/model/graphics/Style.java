package ikor.model.graphics;

import java.awt.Color;

public class Style 
{
	public static final int   DEFAULT_WIDTH = 3;
	public static final Color DEFAULT_COLOR = Color.black;
	
	private Color color;
	private int   width;
	
	public Style ()
	{
		this (DEFAULT_COLOR, DEFAULT_WIDTH);
	}
	
	public Style (int width)
	{
		this (DEFAULT_COLOR, width);
	}
	
	public Style (Color color)
	{
		this (color, DEFAULT_WIDTH);
	}

	public Style (Color color, int width)
	{
		this.color = color;
		this.width = width;
	}
	
	public Color getColor() 
	{
		return color;
	}

	public void setColor(Color color) 
	{
		this.color = color;
	}
	
	public int getWidth() 
	{
		return width;
	}

	public void setWidth(int width) 
	{
		this.width = width;
	}
	
	public String toString ()
	{
		return String.format("s%08xw%d",color.getRGB(), width);
	}
}
