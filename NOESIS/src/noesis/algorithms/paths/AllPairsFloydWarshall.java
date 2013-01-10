package noesis.algorithms.paths;

import noesis.Network;

import ikor.collection.Evaluator;


public class AllPairsFloydWarshall<V,E> extends AllPairsShortestPathFinder<V, E> 
{
	
	public AllPairsFloydWarshall (Network<V, E> net, Evaluator<E> linkEvaluator) 
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
		this.distance = new double[n][n];
		
		newDistance = new double[n][n];
		
		// Initialization
		
		for (int i=0; i<n; i++)
			for (int j=0; j<n; j++)
				distance[i][j] = Double.POSITIVE_INFINITY;
		
		for (int i=0; i<n; i++) {
			distance[i][i] = 0;
			
			int[] links = network.outLinks(i);
			
			if (links!=null) {
				for (int j=0; j<links.length; j++) {
					distance[i][links[j]] = linkEvaluator.evaluate( network.get(i,links[j]) );
				}
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
