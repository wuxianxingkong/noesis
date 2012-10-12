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

public abstract class SudokuSolver 
{
	protected Sudoku sudoku;
	
	
	/**
	 * Constructor
	 * @param template Cadena representando una plantilla de sudoku
	 */
	public SudokuSolver (Sudoku sudoku)
	{
		this.sudoku = sudoku;
	}
	
	
	public boolean check ()
	{
		return sudoku.check();
	}

	public boolean isSolved ()
	{
		return sudoku.isSolved();
	}

	
	/**
	 * Resolución de sudokus
	 */
	public abstract void solve();
	
	
	/**
     * Representación del sudoku como una cadena de caracteres
	 * @see java.lang.Object#toString()
	 */
	public String toString ()
	{
		return sudoku.toString();
	}

}
