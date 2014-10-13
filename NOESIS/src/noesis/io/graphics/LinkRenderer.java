package noesis.io.graphics;

import ikor.model.graphics.Style;
import ikor.model.graphics.colors.ColorMap;
import ikor.util.indexer.Indexer;

import java.awt.Color;

public abstract class LinkRenderer 
{
	public static final int DEFAULT_WIDTH = 1;
	
	private int width = DEFAULT_WIDTH;
	private Indexer<Long> widthIndex;
	private Indexer<Long> colorIndex;
	private ColorMap colorMap;
	
	// Link width
	
	public int getWidth() 
	{
		return width;
	}

	public void setWidth (int width) 
	{
		if (width>=0) {
			this.width = width;
		}
	}
	
	public final Indexer<Long> getWidthIndexer ()
	{
		return widthIndex;
	}
	
	public void setWidthIndexer (Indexer<Long> widthIndex)
	{
		this.widthIndex = widthIndex;
	}
	
	
	public final int getWidth (int source, int target)
	{
		if (widthIndex==null)
			return width;
		else
			return width + widthIndex.index((((long)source)<<32) | target);
	}
	
	public final int getWidthIndex (int source, int target) {
		if (widthIndex==null)
			return -1;
		else
			return widthIndex.index((((long)source)<<32) | target);
	}
	
	// Link colors

	public final Indexer<Long> getColorIndexer ()
	{
		return colorIndex;
	}
	
	public final void setColorIndexer (Indexer<Long> colorIndex)
	{
		this.colorIndex = colorIndex;
	}
	
	public final ColorMap getColorMap ()
	{
		return colorMap;
	}
	
	public void setColorMap (ColorMap colorMap)
	{
		this.colorMap = colorMap;
	}
	
	public final int getColorIndex (int source, int target)
	{
		int index = 0;
		
		if (colorIndex!=null) {
			index = colorIndex.index((((long)source)<<32) | target);
			
			if (index<0)
				index = 0;
			else if (index>=colorMap.size())
				index = colorMap.size()-1;
		}
		
		return index;
	}
	
	public final Color getColor (int source, int target)
	{
		return colorMap.getColor( getColorIndex(source, target) );
	}
	
	public abstract Style getStyle (int source, int target);
	
	// Link rendering

	public abstract void render (NetworkRenderer drawing, int source, int target);
	
	public abstract void update (NetworkRenderer drawing, int source, int target);
	
}