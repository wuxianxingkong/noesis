package ikor.model.graphics.styles;

import ikor.model.graphics.Style;

import java.awt.Color;
import java.awt.Font;



public class FontStyle extends Style
{
	private Font   font;
	private double angle;
	
	public FontStyle (Color color, Font font)
	{
		super(color);
		
		this.font = font;
		this.angle = 0;
	}

	public FontStyle (Color color, Font font, double angle)
	{
		super(color);
		
		this.font  = font;
		this.angle = angle;
	}
	
	public Font getFont() 
	{
		return font;
	}

	public void setFont (Font font) 
	{
		this.font = font;
	}
	
	
	public double getAngle() 
	{
		return angle;
	}

	public void setAngle(double angle) 
	{
		this.angle = angle;
	}
	

	public String toString ()
	{
		return String.format("f<%s>c%08xr%d", getFont().toString(), getColor().getRGB(), (int)(180*angle/Math.PI) );
	}
	
	
}
