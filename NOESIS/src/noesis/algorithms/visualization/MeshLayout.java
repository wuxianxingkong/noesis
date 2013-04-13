package noesis.algorithms.visualization;

import noesis.Attribute;
import noesis.AttributeNetwork;

public class MeshLayout extends NetworkLayout 
{
	private int rows;
	private int columns;
	
	public MeshLayout ()
	{	
	}
	
	public MeshLayout (int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
	}

	@Override
	public void layout(AttributeNetwork network, Attribute<Double> x, Attribute<Double> y) 
	{
		int row, column;
		int width, height;
		int nodes = network.size();
		
		if (columns!=0) {
			width = columns;
			height = rows;
		} else {
			width = (int) Math.ceil ( Math.sqrt(nodes) );
			height = width;
		}

		for (int i=0; i<nodes; i++) {
			row = i/width;
			column = i%width;
			
			x.set(i, MARGIN + ((1-2*MARGIN)*column)/(width-1) );
			y.set(i, MARGIN + ((1-2*MARGIN)*row)/(height-1) );
		}		
	}

}
