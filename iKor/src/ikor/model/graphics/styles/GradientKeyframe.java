package ikor.model.graphics.styles;

import java.awt.Color;

public class GradientKeyframe 
{
	private float value;
	private Color color;
	
	public GradientKeyframe (float value, Color color)
	{
		this.value = value;
		this.color = color;		
	}


	public float getValue() 
	{
		return value;
	}

	public void setValue(float value) 
	{
		this.value = value;
	}

	public Color getColor() 
	{
		return color;
	}

	public void setColor(Color color) 
	{
		this.color = color;
	}
	
	public String toString ()
	{
		return String.format("%08x@%.2f", color.getRGB(), value);
	}
}
