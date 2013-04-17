package noesis.io.graphics;

import java.awt.Color;

public class Colorer<T> 
{
	public static final int DEFAULT_TRANSPARENCY = 0xFF;
	
	private int transparency = DEFAULT_TRANSPARENCY;

	private ColorMap colormap;
	private Indexer<T> indexer;

	
	public Colorer (ColorMap map, Indexer<T> indexer)
	{
		this.colormap = map;
		this.indexer = indexer;
	}
	
	
	public ColorMap getColorMap ()
	{
		return colormap;
	}
	
	public void setColorMap (ColorMap map)
	{
		this.colormap = map;
	}

	public int getTransparency ()
	{
		return transparency;
	}
	
	public void setTransparency (int alpha)
	{
		this.transparency = alpha;
	}
	

	public Color getColor(T value) 
	{
		ColorMap map = getColorMap();
		int      index = indexer.index(value);
		
		return new Color ( map.red(index), map.green(index), map.blue(index), getTransparency() );
	}
	
}
