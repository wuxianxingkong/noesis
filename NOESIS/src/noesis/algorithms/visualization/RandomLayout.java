package noesis.algorithms.visualization;

import ikor.math.random.Random;

import noesis.Attribute;
import noesis.AttributeNetwork;

public class RandomLayout extends NetworkLayout {

	@Override
	public void layout(AttributeNetwork network, Attribute<Double> x, Attribute<Double> y) 
	{
		for (int i=0; i<network.size(); i++) {
			x.set(i, MARGIN + (1-2*MARGIN)*Random.random());
			y.set(i, MARGIN + (1-2*MARGIN)*Random.random());
		}
	}

}
