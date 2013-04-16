package noesis.algorithms.visualization;

import noesis.Attribute;
import noesis.AttributeNetwork;

public class NormalizedLayout extends NetworkLayout 
{
	@Override
	public void layout(AttributeNetwork network, Attribute<Double> x, Attribute<Double> y) 
	{
		// Normalize coordinates...
		
		double minX = Double.MAX_VALUE;
		double maxX = -Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxY = -Double.MAX_VALUE;
		double cx, cy;
		
		for (int i=0; i<network.size(); i++) {
			
			cx = x.get(i);
			cy = y.get(i);
			
			if (cx<minX)
				minX = cx;
			
			if (cx>maxX)
				maxX = cx;
			
			if (cy<minY)
				minY = cy;
			
			if (cy>maxY)
				maxY = cy;
		}
		
		if ((minX<0) || (maxX>1) || (minY<0) || (maxY>1)) {
			
			for (int i=0; i<network.size(); i++) {
				x.set(i, MARGIN + (1-2*MARGIN)*(x.get(i)-minX)/(maxX-minX) );
				y.set(i, MARGIN + (1-2*MARGIN)*(y.get(i)-minY)/(maxY-minY) );
			}
		}
	}

}
