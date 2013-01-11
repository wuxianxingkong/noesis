package noesis.algorithms.paths;

import noesis.Network;
import noesis.LinkEvaluator;

import ikor.parallel.*;

public class AllPairsBellmanFord<V,E> extends AllPairsShortestPathFinder<V, E> 
{

	private boolean negativeCycles;

	
	public AllPairsBellmanFord (Network<V, E> net, LinkEvaluator linkEvaluator) 
	{
		super(net, linkEvaluator);
	}

	
	@Override
	public boolean negativeCycleDetected ()
	{
		return negativeCycles;
	}

	
	@Override
	public void run() 
	{
		int n = network.size();
		
		BellmanFordKernel kernel = new BellmanFordKernel();
		
		this.distance = new double[n][];
		this.negativeCycles = false;
		
		Parallel.map(kernel, 0, n-1);
	}
	
	
	class BellmanFordKernel implements Kernel
	{
		@Override
		public Object call (int index) 
		{
			if (!negativeCycles) {
				
				BellmanFordShortestPathFinder finder = new BellmanFordShortestPathFinder(network,index,linkEvaluator);

				finder.run();

				distance[index] = finder.distance();

				if (finder.negativeCycleDetected())
					negativeCycles = true;
			}

			return null;
		}
		
	}
	
}
