package noesis.io.graphics;

import java.awt.Color;

/**
 * Abstract color map
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public abstract class ColorMap 
{
	public abstract int size();
	
	public abstract int red (int index);
	
	public abstract int green (int index);
	
	public abstract int blue (int index);
	
	public final int rgb (int index)
	{
		return ((red(index)&0xFF)<<16) | ((green(index)&0xFF)<<8) | (blue(index)&0xFF);
	}
	
	// Color cache
	
	private Color cache[];
	
	public final Color getColor (int index)
	{
		if (cache==null)
			cache = new Color[size()];
		
		if (cache[index]==null)
			cache[index] = new Color (rgb(index));
		
		return cache[index];
	}
}
