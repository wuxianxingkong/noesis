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

public class SudokuSolver extends sandbox.ai.BacktrackingSolver 
{
	
	/**
	 * Constructor
	 * @param template Cadena representando una plantilla de sudoku
	 */
	public SudokuSolver (String template)
	{
		super(new sandbox.ai.SudokuX(template));
	}
	
	
	/**
	 * Resolución de sudokus
	 */
	public void solve()
	{
		super.solve();
	}
	
	
	/**
     * Representación del sudoku como una cadena de caracteres
	 * @see java.lang.Object#toString()
	 */
	public String toString ()
	{
		return super.toString();
	}

}
