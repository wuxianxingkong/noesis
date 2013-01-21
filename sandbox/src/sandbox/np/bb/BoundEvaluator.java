package sandbox.np.bb;

import ikor.collection.Evaluator;

public class BoundEvaluator implements Evaluator<OptimizationProblem>
{
	@Override
	public double evaluate(OptimizationProblem object) 
	{
		return object.getBound();
	}		
}
