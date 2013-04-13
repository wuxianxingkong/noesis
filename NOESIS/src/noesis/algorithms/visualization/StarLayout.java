package noesis.algorithms.visualization;

import noesis.Attribute;
import noesis.AttributeNetwork;

public class StarLayout extends NetworkLayout 
{

	@Override
	public void layout(AttributeNetwork network, Attribute<Double> x, Attribute<Double> y) 
	{
		int nodes = network.size();
		
		x.set(0, 0.5);
		y.set(0, 0.5);
		
		for (int i=1; i<nodes; i++) {
			x.set(i, 0.5 + (0.5-MARGIN)*Math.cos(i*2*Math.PI/(nodes-1)));
			y.set(i, 0.5 + (0.5-MARGIN)*Math.sin(i*2*Math.PI/(nodes-1)));
		}
	}

}
