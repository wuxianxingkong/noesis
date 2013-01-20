package sandbox.np.bb;

public class TSPOptimizationProblem extends OptimizationProblem 
{
	float   distance[][];
	
	int     solution[];
	boolean used[];
	
	public final static int EMPTY = -1;
	
	public TSPOptimizationProblem (float distance[][])
	{
		int n = distance.length;
		
		this.distance = distance;
		this.solution = new int[n];
		this.used     = new boolean[n];
		
		solution[0] = 0;
		used[0] = true;
		
		for (int i=1; i<solution.length; i++)
			solution[i] = EMPTY;
	}

	@Override
	public OptimizationProblem child (int var, int value) 
	{
		TSPOptimizationProblem result = new TSPOptimizationProblem(distance);
		
		for (int i=0; i<solution.length; i++) {
			result.solution[i] = solution[i];
			
			if (solution[i]!=EMPTY)
				result.used[solution[i]] = true;
		}
		
		result.solution[var] = value;
		result.used[value] = true;
		
		return result;
	}

	@Override
	public int variables() 
	{
		return solution.length;
	}

	@Override
	public boolean isUnassigned(int var) 
	{
		return ( solution[var]==EMPTY );
	}

	@Override
	public void set(int var, int value) 
	{
		solution[var] = value;
	}

	@Override
	public void clear(int var) 
	{
		solution[var] = EMPTY;
	}

	@Override
	public int[] values (int var) 
	{
		int n = variables();
		int count;
		int current;
		int result[];
		
		count = 0;
		
		for (int i=0; i<n; i++) {
			if (!used[i]) {
				count++;
			}
		}
		
		result = new int[count];
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
		return (float) new TSPEvaluator().evaluate(this);
	}
	
	// toString
	
	public String toString ()
	{
		String result = "TSP tour {0";
				
		for (int i=1; i<solution.length && solution[i]!=EMPTY; i++)
			result += ","+solution[i];
		
		result += ",0} cost="+getValue();
		
		return result;
	}

	
	
}
