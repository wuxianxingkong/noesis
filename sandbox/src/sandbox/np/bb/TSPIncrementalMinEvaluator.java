package sandbox.np.bb;

import ikor.collection.Evaluator;

public class TSPIncrementalMinEvaluator extends TSPIncrementalEvaluator implements Evaluator<TSPIncrementalOptimizationProblem> 
{
	float min[];

	public TSPIncrementalMinEvaluator (float distance[][])
	{			
		super(distance);
		
		int n = distance.length;

		// Lower bound @ rows 

		min = new float[n];

		for (int i=0; i<n; i++) {
			min[i] = Float.MAX_VALUE;

			for (int j=0; j<n; j++) {
				if ((i!=j) && (distance[i][j]<min[i]))
					min[i] = distance[i][j];
			}
		}
	}

	@Override
	public double evaluate (TSPIncrementalOptimizationProblem problem) 
	{
		float cost = (float) super.evaluate(problem);
		boolean used[] = problem.usedVariables();
			
		for (int i=1; i<min.length; i++) { // 0 is always used @ beginning/end of circuit
			if (!used[i])
				cost += min[i];
		}

		return cost;
	}
}


