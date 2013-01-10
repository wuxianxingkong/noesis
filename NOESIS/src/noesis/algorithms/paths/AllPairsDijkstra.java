package noesis.algorithms.paths;

import noesis.Network;
import ikor.collection.Evaluator;
import ikor.parallel.*;

public class AllPairsDijkstra<V,E> extends AllPairsShortestPathFinder<V, E> 
{

	public AllPairsDijkstra (Network<V, E> net, Evaluator<E> linkEvaluator) 
	{
		super(net, linkEvaluator);
	}

	
	@Override
	public void run() 
	{
		int n = network.size();
		
		DijkstraKernel kernel = new DijkstraKernel();
		
		this.distance = new double[n][];
		
		Parallel.map(kernel, 0, n-1);
	}
	
	
	class DijkstraKernel implements Kernel
	{
		@Override
		public Object call (int index) 
		{
			DijkstraShortestPathFinder finder = new DijkstraShortestPathFinder(network,index,linkEvaluator);
			
			finder.run();
			
			distance[index] = finder.distance();

			return null;
		}
		
	}
	
}
