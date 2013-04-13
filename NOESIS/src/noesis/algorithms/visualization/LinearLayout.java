package noesis.algorithms.visualization;

import noesis.Attribute;
import noesis.AttributeNetwork;

public class LinearLayout extends NetworkLayout 
{

	@Override
	public void layout(AttributeNetwork network, Attribute<Double> x, Attribute<Double> y) 
	{
		int nodes = network.size();
		
		for (int i=0; i<nodes; i++) {
			x.set(i, MARGIN + ((1-2*MARGIN)*i)/(nodes-1) );
			y.set(i, 0.5 );
		}		
	}

}
