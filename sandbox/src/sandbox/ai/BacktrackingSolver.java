package sandbox.ai;

/**
 * Resolución de problemas usando backtracking.
 * 
 * @author Fernando Berzal
 */

public class BacktrackingSolver extends Solver 
{

	public BacktrackingSolver (Problem problem) 
	{
		super(problem);
	}
	
	public void solve()
	{
		if (problem.check())
			backtrack(problem);
	}
	
	private boolean backtrack (Problem problem)
	{
		int     variable;
		boolean fin = problem.isComplete(); // problem.isSolved();
		
		if (!fin) {
			
			// Choose an unassigned variable
			
			//variable = firstVariable(problem);
			variable = mrvVariable(problem);

			// Assign different values to the chosen variable
			
			int candidates[] = problem.values(variable);
			
			for (int v=0; (v<candidates.length) && !fin; v++) {
				problem.set(variable, candidates[v]);

				if (problem.check(variable))
					fin = backtrack(problem);
			}
						
			if (!fin)
				problem.clear(variable);
		}
		
		return fin;
	}
	
	
	// Variable selection
	
	private int firstVariable (Problem problem)
	{		
		for (int v=0; v<problem.variables(); v++) 		
			if (problem.isUnassigned(v))
				return v;
		
		return -1;
	}
		
	private int mrvVariable (Problem problem)
	{		
		int best = -1;
		int values = Integer.MAX_VALUE;
		int candidates;
		
		for (int var=0; var<problem.variables(); var++) 		
			if (problem.isUnassigned(var)) {
				candidates = problem.valueCount(var);
				
				if (candidates<values) {
					best = var;
					values = candidates;
				}
			}
		
		return best;
	}

}
