package sandbox.np.bb;

import ikor.collection.Evaluator;

public class TSPIncrementalEvaluator implements Evaluator<TSPIncrementalOptimizationProblem> 
{
	static int counter = 0;

	float distance[][];
	
	public TSPIncrementalEvaluator (float distance[][])
	{
		this.distance = distance;
	}
	
	
	public double recursiveValue (TSPIncrementalOptimizationProblem problem)
	{
		float cost = 0;
		int   n = distance.length;
		
		if (problem.parent!=null)
			cost += recursiveValue(problem.parent);
		
		if (problem.k>0)
			cost += distance[problem.parent.value][problem.value];

		if (problem.k==n-1)
			cost += distance[problem.value][0];

		return cost;		
	}
	
	@Override
	public double evaluate(TSPIncrementalOptimizationProblem problem) 
	{
		counter++;
		// System.out.println(counter+" evals");

		return recursiveValue(problem);
	}
}
