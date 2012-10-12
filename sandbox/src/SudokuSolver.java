//Title:       Sudoku
//Version:     0.1
//Copyright:   2011
//Author:      Fernando Berzal Galiano
//E-mail:      berzal@acm.org


/**
 * Implementación base para la resolución de un Sudoku.
 * 
 * @author Fernando Berzal
 */

public class SudokuSolver 
{
	sandbox.ai.Solver solver;
	
	/**
	 * Constructor
	 * @param template Cadena representando una plantilla de sudoku
	 */
	public SudokuSolver (String template)
	{
		solver = new sandbox.ai.BacktrackingSolver(new sandbox.ai.SudokuX(template));  	
	}
	
	
	/**
	 * Resolución de sudokus
	 */
	public void solve()
	{
		solver.solve();
	}
	
	
	/**
     * Representación del sudoku como una cadena de caracteres
	 * @see java.lang.Object#toString()
	 */
	public String toString ()
	{
		return solver.toString();
	}

}
 
                
 
