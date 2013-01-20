package sandbox.np.bb;

import ikor.collection.Evaluator;

public class TSPMinEvaluator extends TSPEvaluator implements Evaluator<TSPOptimizationProblem> 
{
	float min[];
	
	public TSPMinEvaluator (TSPOptimizationProblem problem)
	{			
		int n = problem.variables();
		
		// Lower bound @ rows 
		
		min = new float[n];
		
		for (int i=0; i<n; i++) {
			min[i] = Float.MAX_VALUE;
			
			for (int j=0; j<n; j++) {
				if ((i!=j) && (problem.distance[i][j]<min[i]))
					min[i] = problem.distance[i][j];
			}
		}
	}
	
	@Override
	public double evaluate (TSPOptimizationProblem problem) 
	{
		float cost = (float) super.evaluate(problem);
		
		for (int i=1; i<min.length; i++) {
			if (!problem.used[i])
				cost += min[i];
		}
		
		return cost;
	}
}


