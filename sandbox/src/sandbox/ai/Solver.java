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

}
