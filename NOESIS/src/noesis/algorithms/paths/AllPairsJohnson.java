package noesis.algorithms.paths;

import noesis.Network;
import noesis.AugmentedNetwork;
import noesis.LinkEvaluator;

import ikor.parallel.*;

public class AllPairsJohnson<V,E> extends AllPairsShortestPathFinder<V, E> 
{
	private boolean negativeCycles;
	
	LinkEvaluator augmentedEvaluator;
	LinkEvaluator dijkstraEvaluator;

	double weight[];
	
	
	public AllPairsJohnson (Network<V, E> net, LinkEvaluator linkEvaluator) 
	{
		super(net, linkEvaluator);
		negativeCycles = false;
	}

	
	@Override
	public void run() 
	{
		int n = network.size();
		
		// Augmented network, O(n)
		
		Network original = this.network();
		AugmentedNetwork augmented = new AugmentedNetwork(original);
		
		augmented.add(n);
		
		for (int i=0; i<n; i++)
			augmented.add(n,i);
		
		augmentedEvaluator = new AugmentedEvaluator (n, linkEvaluator);
		
		// Single-source shortest paths (Bellman-Ford's algorithm), O(mn)
		
		BellmanFordShortestPathFinder finder = new BellmanFordShortestPathFinder(augmented,n,augmentedEvaluator);
		
		finder.run();
		
		negativeCycles = finder.negativeCycleDetected();
		
		
		if (!negativeCycles ){
			
			weight = finder.distance();

			// Reweighting, O(1)
			
			dijkstraEvaluator = new DijkstraEvaluator (weight, linkEvaluator);
		
			// All-pairs shortest paths (Dijkstra's algorithm), O(nm log n)
		
			DijkstraKernel kernel = new DijkstraKernel();
		
			this.distance = new double[n][];
		
			Parallel.map(kernel, 0, n-1);
			
			weight = null;
		}
	}
	
	
	class DijkstraKernel implements Kernel
	{
		@Override
		public Object call (int index) 
		{
			// Dijkstra's single-source shortest path algorithm
			
			DijkstraShortestPathFinder finder = new DijkstraShortestPathFinder(network,index,dijkstraEvaluator);
			
			finder.run();
			
			distance[index] = finder.distance();
			
			// Reweighting
			
			for (int n=0; n<network.size(); n++)
				distance[index][n] += weight[n] - weight[index];

			return null;
		}
		
	}
	

	class DijkstraEvaluator implements LinkEvaluator
	{
		private double weight[];
		private LinkEvaluator evaluator;
		
		public DijkstraEvaluator (double weight[], LinkEvaluator evaluator)
		{
			this.weight = weight;
			this.evaluator = evaluator;
		}

		@Override
		public double evaluate(int source, int destination) 
		{
			return evaluator.evaluate(source,destination) + weight[source] - weight[destination];
		}
	}
	
	class AugmentedEvaluator implements LinkEvaluator
	{
		private int n;
		private LinkEvaluator evaluator;
		
		public AugmentedEvaluator (int n, LinkEvaluator evaluator)
		{
			this.n = n;
			this.evaluator = evaluator;
		}
		
		@Override
		public double evaluate(int source, int destination) 
		{
			if (source!=n)
				return evaluator.evaluate(source,destination);
			else
				return 0;
		}
	}
	
}
