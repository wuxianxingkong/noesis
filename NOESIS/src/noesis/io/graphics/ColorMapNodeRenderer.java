package noesis.io.graphics;

import ikor.model.graphics.Circle;
import ikor.model.graphics.Style;

import java.awt.Color;

public class ColorMapNodeRenderer implements NodeRenderer 
{
	public static final int DEFAULT_SIZE = 16;
	public static final int DEFAULT_WIDTH = 2;
	public static final int DEFAULT_COLORS = 16;
	
	public static final Colorer<Integer> DEFAULT_COLORER = new Colorer( new JetColorMap(DEFAULT_COLORS), new CyclicIndexer(DEFAULT_COLORS) );
	public static final Style DEFAULT_BORDER = new Style ( new Color(0x00,0x00,0x00,0xFF), DEFAULT_WIDTH);
	
	private int size;
	private Colorer<Integer> colorer;
	
	
	public ColorMapNodeRenderer ()
	{
		this.colorer = DEFAULT_COLORER;
		this.size = DEFAULT_SIZE;
	}

	public ColorMapNodeRenderer (ColorMap colors)
	{
		this.colorer = new Colorer( colors, new CyclicIndexer(colors.size()) );
		this.size = DEFAULT_SIZE;
	}
	
	public ColorMapNodeRenderer (Colorer colorer)
	{
		this.colorer = colorer;
		this.size = DEFAULT_SIZE;
	}
	
	
	public Colorer<Integer> getColorer ()
	{
		return colorer;
	}
	
	public void setColorer (Colorer<Integer> colorer)
	{
		this.colorer = colorer;
	}
	
	
	@Override
	public int getSize()
	{
		return size;
	}
	
	@Override
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
		
		Style color = new Style ( colorer.getColor(node), DEFAULT_WIDTH);
				
		drawing.add ( new Circle ( drawing.getNodeId(node), color, DEFAULT_BORDER, x, y, size));
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