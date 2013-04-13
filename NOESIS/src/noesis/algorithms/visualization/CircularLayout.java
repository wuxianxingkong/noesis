package noesis.algorithms.visualization;

import noesis.Attribute;
import noesis.AttributeNetwork;

public class CircularLayout extends NetworkLayout 
{

	@Override
	public void layout(AttributeNetwork network, Attribute<Double> x, Attribute<Double> y) 
	{
		int nodes = network.size();
		
		for (int i=0; i<nodes; i++) {
			x.set(i, 0.5 + (0.5-MARGIN)*Math.cos(i*2*Math.PI/nodes));
			y.set(i, 0.5 + (0.5-MARGIN)*Math.sin(i*2*Math.PI/nodes));
		}
	}

}
