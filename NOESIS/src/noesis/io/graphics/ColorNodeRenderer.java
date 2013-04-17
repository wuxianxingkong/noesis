package noesis.io.graphics;

import ikor.model.graphics.Circle;
import ikor.model.graphics.Style;

import java.awt.Color;

public class ColorNodeRenderer implements NodeRenderer 
{
	public static final int DEFAULT_SIZE = 16;
	public static final int DEFAULT_WIDTH = 2;
	
	public static final Style[] DEFAULT_COLORS = new Style[] {
		new Style ( new Color(0xB0, 0x00, 0x00, 0xFF), DEFAULT_WIDTH),
		new Style ( new Color(0x00, 0xB0, 0x00, 0xFF), DEFAULT_WIDTH),
		new Style ( new Color(0x00, 0x00, 0xB0, 0xFF), DEFAULT_WIDTH)
	};		
	
	public static final Style[] DEFAULT_BORDERS = new Style[] {
		new Style ( new Color(0x33, 0x00, 0x00, 0xFF), DEFAULT_WIDTH),
		new Style ( new Color(0x00, 0x33, 0x00, 0xFF), DEFAULT_WIDTH),
		new Style ( new Color(0x00, 0x00, 0x33, 0xFF), DEFAULT_WIDTH)				
	};

	
	private int size;
	private Style colors[];
	private Style borders[];
	
	
	public ColorNodeRenderer ()
	{
		this.colors = DEFAULT_COLORS;
		this.borders = DEFAULT_BORDERS;
		this.size = DEFAULT_SIZE;
	}
		
	public ColorNodeRenderer (Style colors[], Style borders[])
	{
		this.colors = colors;
		this.borders = borders;
		this.size = DEFAULT_SIZE;
	}
	
	
	public int getSize()
	{
		return size;
	}
	
	public void setSize (int size)
	{
		if (size>=0)
			this.size = size;
	}
		
	
	@Override
	public void render(NetworkRenderer drawing, int node) 
	{
		int x = drawing.getX(node);
		int y = drawing.getY(node);
				
		drawing.add ( new Circle ( drawing.getNodeId(node), colors[node%colors.length], borders[node%borders.length], x, y, size));
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
		}		
	}

}