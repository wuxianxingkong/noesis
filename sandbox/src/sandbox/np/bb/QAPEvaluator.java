package sandbox.np.bb;

import ikor.collection.Evaluator;

// Evaluator

public class QAPEvaluator implements Evaluator<QAP> 
{
	static int counter = 0;
	
	@Override
	public double evaluate (QAP problem) 
	{
		float cost = 0;
		int   n = problem.variables();
		int   solution[] = problem.solution;
		
		for (int i=0; i<n; i++) {
			if (solution[i]!=QAP.EMPTY)
				for (int j=0; j<n; j++) {
					if (solution[j]!=QAP.EMPTY)
						cost += problem.flow[i][j] * problem.distance[solution[i]][solution[j]];
				}
		}
		
		counter++;
		// System.out.println(counter+" evals");
		
		return cost;
	}
}
