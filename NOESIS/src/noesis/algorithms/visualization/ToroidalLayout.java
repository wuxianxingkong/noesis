package noesis.algorithms.visualization;

import noesis.Attribute;
import noesis.AttributeNetwork;

public class ToroidalLayout extends NetworkLayout 
{

	private int rows;
	private int columns;
	
	public ToroidalLayout ()
	{	
	}
	
	public ToroidalLayout (int rows, int columns)
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
			
			x.set(i, MARGIN + ((1-3*MARGIN)*column)/(width-1) + MARGIN*Math.sin(row*Math.PI/width) );
			y.set(i, MARGIN + ((1-3*MARGIN)*row)/(height-1) + MARGIN*Math.sin(column*Math.PI/height) );
		}		
	}

}
