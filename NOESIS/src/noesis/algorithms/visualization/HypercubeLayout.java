package noesis.algorithms.visualization;

import noesis.Attribute;
import noesis.AttributeNetwork;

public class HypercubeLayout extends NetworkLayout 
{

	@Override
	public void layout(AttributeNetwork network, Attribute<Double> x, Attribute<Double> y) 
	{
		int nodes = network.size();
		int dimension = (int) Math.ceil( Math.log(nodes)/Math.log(2) );
		
		double maxX = xcoord((1<<dimension)-1, dimension);
		double maxY = ycoord((1<<dimension)-1, dimension);
		
		for (int i=0; i<nodes; i++) {
			x.set(i, MARGIN + (1-2*MARGIN)*xcoord(i,dimension)/maxX );
			y.set(i, MARGIN + (1-2*MARGIN)*ycoord(i,dimension)/maxY );
		}
	}

	
	public double xcoord (int i, int d)
	{
		return alternativeBits(i,d) + ((d%2==1)?0:msb(i,d));
	}
	
	public double ycoord (int i, int d)
	{
		return alternativeBits(i>>1,d) + ((d%2==0)?0:msb(i,d));
	}
	
	private int alternativeBits (int i, int d)
	{
		if (i==0)
			return 0;
		else
			return (i%2)*(1<<(d/2)) + alternativeBits(i>>2,d-2);
	}
	
	private int msb (int i, int d)
	{
		return i>>(d-1);
	}
	
}
