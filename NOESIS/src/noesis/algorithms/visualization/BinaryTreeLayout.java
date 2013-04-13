package noesis.algorithms.visualization;

import noesis.Attribute;
import noesis.AttributeNetwork;

public class BinaryTreeLayout extends NetworkLayout 
{

	@Override
	public void layout(AttributeNetwork network, Attribute<Double> x, Attribute<Double> y) 
	{
		int nodes = network.size();
		int depth = (int) Math.floor(Math.log(nodes)/Math.log(2));
		
		x.set(0, 0.5);
		y.set(0, MARGIN);
		
		for (int i=1; i<nodes; i++) {
			int level =  (int) Math.floor(Math.log(i+1)/Math.log(2));
			int levelNodes = 1<<level;
			double space = 1.0/(2*levelNodes);
			x.set(i, MARGIN + (1.0-2*MARGIN) * ( space + ((double)(i - levelNodes + 1)) / levelNodes ) );
			y.set(i, MARGIN + ((1.0-2*MARGIN)*level)/depth );
		}
	}

}
