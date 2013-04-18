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
	
	public final Color getColor (int index)
	{
		return new Color( ((red(index)&0xFF)<<16) | ((green(index)&0xFF)<<8) | (blue(index)&0xFF) );
	}
}
