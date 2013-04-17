package noesis.io.graphics;

import ikor.model.graphics.Circle;
import ikor.model.graphics.Style;

import java.awt.Color;

public class ColorNodeRenderer extends NodeRenderer 
{
	
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

	private Style colors[];
	private Style borders[];
	
	
	public ColorNodeRenderer ()
	{
		this.colors = DEFAULT_COLORS;
		this.borders = DEFAULT_BORDERS;
	}
		
	public ColorNodeRenderer (Style colors[], Style borders[])
	{
		this.colors = colors;
		this.borders = borders;
	}
	

	@Override
	public Style getStyle (int node) 
	{
		int pos = node%colors.length;
		
		if (getColorIndexer()!=null)
			pos = (int) Math.round ( (colors.length-1) * ( (double) getColorIndex(node) / (double) getColorIndexer().max() ) );
			
		return colors[pos];
	}
	
	public Style getBorder (int node)
	{
		return borders[node%borders.length];
	}
	
	
		
	
	@Override
	public void render(NetworkRenderer drawing, int node) 
	{
		int x = drawing.getX(node);
		int y = drawing.getY(node);
				
		drawing.add ( new Circle ( drawing.getNodeId(node), getStyle(node), getBorder(node), x, y, getSize(node) ));
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
			circle.setBorder( getBorder(node) ); 
		}		
	}


}