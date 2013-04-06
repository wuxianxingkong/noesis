package sandbox.mdsd.graphics.styles;

import java.awt.Color;
import java.awt.Font;

import sandbox.mdsd.graphics.Style;


public class FontStyle extends Style
{
	private Font font;
	private int  angle;
	
	public FontStyle (Color color, Font font)
	{
		super(color);
		
		this.font = font;
		this.angle = 0;
	}

	public FontStyle (Color color, Font font, int angle)
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
	
	
	public int getAngle() 
	{
		return angle;
	}

	public void setAngle(int angle) 
	{
		this.angle = angle;
	}
	

	public String toString ()
	{
		return String.format("f<%s>c%08xr%d", getFont().toString(), getColor().getRGB(), angle );
	}
	
	
}
