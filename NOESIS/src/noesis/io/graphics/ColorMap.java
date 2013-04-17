package noesis.io.graphics;

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
}
