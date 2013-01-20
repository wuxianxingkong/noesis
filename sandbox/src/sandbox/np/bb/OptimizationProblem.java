package sandbox.np.bb;

import sandbox.ai.Problem;

public abstract class OptimizationProblem extends Problem 
{
	private float bound;

	// Lower bound for minimization problems, upper bound for maximization problems
	
	public float getBound ()
	{
		return bound;
	}

	public void setBound (float bound)
	{
		this.bound = bound;
	}

	
	public abstract float getValue ();

	public abstract OptimizationProblem child (int var, int value);

}
