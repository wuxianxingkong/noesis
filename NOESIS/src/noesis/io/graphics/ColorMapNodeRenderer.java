package noesis.io.graphics;

import ikor.model.graphics.Circle;
import ikor.model.graphics.Style;

import java.awt.Color;

public class ColorMapNodeRenderer extends NodeRenderer 
{
	public static final int DEFAULT_COLORS = 16;
	
	public static final ColorMap DEFAULT_COLOR_MAP = new JetColorMap(DEFAULT_COLORS);
	public static final Indexer<Integer> DEFAULT_COLOR_INDEXER =  new CyclicIndexer(DEFAULT_COLORS);
	public static final Style DEFAULT_BORDER = new Style ( new Color(0x00,0x00,0x00,0xFF), DEFAULT_WIDTH);
	
	
	public ColorMapNodeRenderer ()
	{
		setColorMap(DEFAULT_COLOR_MAP);
		setColorIndexer(DEFAULT_COLOR_INDEXER);
	}

	public ColorMapNodeRenderer (ColorMap colors)
	{
		setColorMap(colors);
		setColorIndexer(DEFAULT_COLOR_INDEXER);
	}
	
	
	
	
	@Override
	public Style getStyle(int node) 
	{
		return new Style ( getColor(node), DEFAULT_WIDTH);
	}
	
		
	
	@Override
	public void render(NetworkRenderer drawing, int node) 
	{
		int x = drawing.getX(node);
		int y = drawing.getY(node);
			
		drawing.add ( new Circle ( drawing.getNodeId(node), getStyle(node), DEFAULT_BORDER, x, y, getSize(node)));
	}

	@Override
	public void update(NetworkRenderer drawing, int node) 
	{
		int x = drawing.getX(node);
		int y = drawing.getY(node);
			
		Circle circle = (Circle) drawing.getDrawingElement( drawing.getNodeId(node) );

		if (circle!=null) {
			circle.setCenterX(x);
			circle.setCenterY(y);
			circle.setRadius( getSize(node) );
			circle.setStyle( getStyle(node) );
		}		
	}


}