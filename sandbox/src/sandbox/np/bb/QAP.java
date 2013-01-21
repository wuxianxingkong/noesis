package sandbox.np.bb;

public class QAP extends OptimizationProblem 
{
	float   flow[][];
	float   distance[][];
	
	int     solution[];
	boolean used[];
	
	public final static int EMPTY = -1;
	
	public QAP (float flow[][], float distance[][])
	{
		int n = distance.length;
		
		this.flow     = flow;
		this.distance = distance;
		this.solution = new int[n];
		this.used     = new boolean[n];
		
		for (int i=0; i<solution.length; i++)
			solution[i] = EMPTY;
	}

	@Override
	public OptimizationProblem child (int var, int value) 
	{
		QAP result = new QAP(flow, distance);
		
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
		return (float) new QAPEvaluator().evaluate(this);
	}
	
	// toString
	
	public String toString ()
	{
		String result = "QAP (";
				
		for (int i=0; i<solution.length && solution[i]!=EMPTY; i++) {
			result += (solution[i]+1);
			if (i<solution.length-1)
				result += ",";
		}
		
		result += ") cost="+getValue();
		
		return result;
	}

	
	
}
