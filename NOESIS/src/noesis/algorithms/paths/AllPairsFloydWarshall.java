package noesis.algorithms.paths;

import noesis.Network;
import noesis.LinkEvaluator;


public class AllPairsFloydWarshall<V,E> extends AllPairsShortestPathFinder<V, E> 
{
	
	public AllPairsFloydWarshall (Network<V, E> net, LinkEvaluator linkEvaluator) 
	{
		super(net, linkEvaluator);
	}

	
	@Override
	public boolean negativeCycleDetected ()
	{
		for (int i=0; i<network.size(); i++)
			if (distance[i][i]<0)
				return true;
		
		return false;
	}

	
	@Override
	public void run() 
	{
		double newDistance[][];
		double tmp[][];

		int n = network.size();
		int degree;
		int target;

		this.distance = new double[n][n];
		
		newDistance = new double[n][n];
		
		// Initialization
		
		for (int i=0; i<n; i++)
			for (int j=0; j<n; j++)
				distance[i][j] = Double.POSITIVE_INFINITY;
		
		for (int i=0; i<n; i++) {
			distance[i][i] = 0;
			
			degree = network.outDegree(i);
			
			for (int j=0; j<degree; j++) {
				target = network.outLink(i,j);
				distance[i][target] = linkEvaluator.evaluate(i,target);
			}
		}
	
		// Dynamic programming algorithm

		for (int k=0; k<n; k++) {	
			for (int i=0; i<n; i++) {
				for (int j=0; j<n; j++) {
				
					if (distance[i][j] < distance[i][k]+distance[k][j])
						newDistance[i][j] = distance[i][j];
					else
						newDistance[i][j] = distance[i][k] + distance[k][j];
	        	}
			}
			
			tmp = distance;
			distance = newDistance;
			newDistance = tmp;
		}		
	}
	
}
