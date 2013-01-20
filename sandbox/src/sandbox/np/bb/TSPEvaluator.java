package sandbox.np.bb;

import ikor.collection.Evaluator;

// Evaluator

public class TSPEvaluator implements Evaluator<TSPOptimizationProblem> 
{
	static int counter = 0;
	
	@Override
	public double evaluate(TSPOptimizationProblem problem) 
	{
		float cost = 0;
		int   n = problem.variables();
		int   solution[] = problem.solution;
		
		for (int i=1; i<n && solution[i]!=TSPOptimizationProblem.EMPTY; i++) {
			cost += problem.distance[solution[i-1]][solution[i]];
		}
		
		if (solution[n-1]!=TSPOptimizationProblem.EMPTY)
			cost += problem.distance[solution[n-1]][solution[0]];

		counter++;
		// System.out.println(counter+" evals");
		
		return cost;
	}
}
