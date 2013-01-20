package sandbox.ai;

//Title:       Sudoku
//Version:     1.0
//Copyright:   2011
//Author:      Fernando Berzal Galiano
//E-mail:      berzal@acm.org

/**
 * Implementación base para la resolución de un Sudoku.
 * 
 * @author Fernando Berzal
 */

public abstract class Solver 
{
	protected Problem problem;
	
	
	/**
	 * Constructor
	 * @param problem CSP problem
	 */
	public Solver (Problem problem)
	{
		this.problem = problem;
	}
	
	
	public boolean check ()
	{
		return problem.check();
	}

	public boolean isSolved ()
	{
		return problem.isSolved();
	}

	
	/**
	 * Resolución de problemas de satisfacción de restricciones
	 */
	public abstract void solve();
	
	
	/**
     * Representación del problema como una cadena de caracteres
	 * @see java.lang.Object#toString()
	 */
	public String toString ()
	{
		return problem.toString();
	}

	
	
	// Variable selection
	
	protected int firstVariable (Problem problem)
	{		
		for (int v=0; v<problem.variables(); v++) 		
			if (problem.isUnassigned(v))
				return v;
		
		return -1;
	}
		
	protected int mrvVariable (Problem problem)
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
