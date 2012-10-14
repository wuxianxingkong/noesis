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
		backtrack(problem);
	}
	
	private boolean backtrack (Problem problem)
	{
		int     variable;
		boolean fin = problem.isSolved();
		
		if (!fin) {
			
			// Choose an unassigned variable
			
			//variable = firstVariable(problem);
			variable = mrvVariable(problem);

			// Assign different values to the chosen variable
			
			int candidates[] = problem.values(variable);
			
			for (int v=0; (v<candidates.length) && !fin; v++) {
				problem.set(variable, candidates[v]);

				if (problem.check())
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
		int candidates[];
		
		for (int var=0; var<problem.variables(); var++) 		
			if (problem.isUnassigned(var)) {
				candidates = problem.values(var);
				
				if (candidates.length<values) {
					best = var;
					values = candidates.length;
				}
			}
		
		return best;
	}

}
