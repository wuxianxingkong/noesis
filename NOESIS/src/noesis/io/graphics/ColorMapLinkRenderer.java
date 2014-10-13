package noesis.io.graphics;

import ikor.model.graphics.Line;
import ikor.model.graphics.Style;
import ikor.model.graphics.colors.ColorMap;
import ikor.util.indexer.Indexer;

import java.awt.Color;

public class ColorMapLinkRenderer extends LinkRenderer 
{
	public static final int DEFAULT_COLORS = 16;
	public static final Color DEFAULT_COLOR = new Color(0x70, 0x70, 0x70, 0xFF);

	private Style[][] cache;

	@Override
	public void setColorMap (ColorMap colorMap)
	{
		super.setColorMap(colorMap);
		cache = null;
	}
	
	@Override
	public void setWidth (int width)
	{
		super.setWidth(width);
		cache = null;
	}
	
	@Override
	public void setWidthIndexer (Indexer<Long> widthIndex)
	{
		super.setWidthIndexer(widthIndex);
		cache = null;
	}
	
	@Override
	public Style getStyle(int source, int target)
	{	
		// Create cache
		if(cache==null) {
			int colorMapSize = 1;
			if(getColorIndexer()!=null) {
				colorMapSize = getColorMap().size();
			}
			
			int widthSize = 1;
			if(getWidthIndexer()!=null) {
				widthSize = getWidthIndexer().range()+1;
			}
			
			cache = new Style[colorMapSize][widthSize];
		}
		
		
		// Get style
		Color color;
		int colorIndex;
		if (getColorIndexer() == null) {
			colorIndex = 0;
			color = DEFAULT_COLOR;
		} else {
			colorIndex = getColorIndex(source, target);
			color = getColor(source, target);
		}

		int width;
		int widthIndex;
		if (getWidthIndexer() == null) {
			widthIndex = 0;
			width = DEFAULT_WIDTH + getWidth();
		} else {
			widthIndex = getWidthIndex(source, target);
			width = getWidth(source, target);
		}

		if (cache[colorIndex][widthIndex] == null) {
			cache[colorIndex][widthIndex] = new Style(color, width);
		}

		return cache[colorIndex][widthIndex];
	}
	
	@Override
	public void render(NetworkRenderer drawing, int source, int target) 
	{
		drawing.add( 
			new Line ( 
				drawing.getLinkId(source, target), 
				getStyle(source, target),
				drawing.getX(source), drawing.getY(source), 
				drawing.getX(target), drawing.getY(target) 
			) );
	}

	@Override
	public void update(NetworkRenderer drawing, int source, int target) 
	{
		Line link = (Line) drawing.getDrawingElement( drawing.getLinkId(source, target) );
		
		if (link!=null) {			
			link.setStartX( drawing.getX(source) );
			link.setStartY( drawing.getY(source) );
			link.setEndX( drawing.getX(target) );
			link.setEndY( drawing.getY(target) );
			link.setStyle( getStyle(source, target) );
		}

	}

}
