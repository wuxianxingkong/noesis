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
	
	@Override
	public double evaluate(TSPIncrementalOptimizationProblem problem) 
	{
		float cost = 0;
		
		if (problem.parent!=null)
			cost += evaluate(problem.parent);
		
		if (problem.k>0)
			cost += distance[problem.parent.value][problem.value];

		if (problem.k==problem.variables()-1)
			cost += distance[problem.value][0];

		counter++;
		// System.out.println(counter+" evals");

		return cost;
	}
}
