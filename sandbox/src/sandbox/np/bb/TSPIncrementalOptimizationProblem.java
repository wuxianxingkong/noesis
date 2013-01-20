package sandbox.np.bb;

public class TSPIncrementalOptimizationProblem extends OptimizationProblem
{
	int k;
	int value;
	TSPIncrementalOptimizationProblem parent;

	public final static int EMPTY = -1;
	
	private static boolean used[];
	private static TSPIncrementalEvaluator evaluator;
		
	public TSPIncrementalOptimizationProblem (float distance[][])
	{
		int n = distance.length;
		
		this.parent = null;
		this.k = 0;
		this.value = 0;
		
		used = new boolean[n];
		evaluator = new TSPIncrementalEvaluator(distance);
	}

	public TSPIncrementalOptimizationProblem (TSPIncrementalOptimizationProblem parent, int k, int value)
	{
		if (k!=parent.k+1)
			throw new UnsupportedOperationException();
			
		this.parent = parent;
		this.k = k;
		this.value = value;
	}

	@Override
	public OptimizationProblem child (int var, int value) 
	{
		return new TSPIncrementalOptimizationProblem(this,var,value);
	}

	@Override
	public int variables() 
	{
		return used.length;
	}

	@Override
	public boolean isUnassigned(int var) 
	{
		return ( var > k );
	}

	@Override
	public void set(int var, int value) 
	{
		if (var!=k)
			throw new UnsupportedOperationException();
		
		this.value = value;
	}

	@Override
	public void clear(int var) 
	{
		if (var!=k)
			throw new UnsupportedOperationException();

		value = EMPTY;
	}
	
	
	private void resetUsed ()
	{
		for (int i=0; i<used.length; i++)
			used[i] = false;
		
	}
	private void fillUsed ()
	{
		if (parent!=null)
			parent.fillUsed();
		
		if (k!=EMPTY)
			used[value] = true;
	}
	

	@Override
	public int[] values (int var) 
	{
		int n = variables();
		int result[] = new int [n-k-1];
		int current;
		
		resetUsed();
		fillUsed();
		current = 0;
		
		for (int i=0; i<n; i++) {
			if (!used[i]) {
				result[current] = i;
				current++;
			}
		}

		return result;
	}

	@Override
	public boolean check() 
	{
		return true;
	}

	@Override
	public boolean check(int var) 
	{
		return true;
	}

	@Override
	public float getValue() 
	{
		return (float) evaluator.evaluate(this);
	}
	
	// toString
	
	private String toStringRoute ()
	{
		String route = "";
		
		if (parent!=null) {
			route += parent.toStringRoute()+",";
		}
		
		route += value;
		
		return route;
	}
	
	public String toString ()
	{
		return "TSP tour {"+ toStringRoute() + ",0} cost="+getValue();
	}

	
}
