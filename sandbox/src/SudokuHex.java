// Title:       Sudoku
// Version:     0.1
// Copyright:   2012
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import sandbox.ai.*;

/**
 * Implementación base para la resolución de un Sudoku hexadecimal.
 * 
 * @author Fernando Berzal
 */

public class SudokuHex 
{
	
	BacktrackingSolver solver;
	
	/**
	 * Constructor
	 * @param template Cadena representando una plantilla de sudoku
	 */
	public SudokuHex (String template)
	{
		solver = new BacktrackingSolver (new sandbox.ai.SudokuHex(template));
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
 
                
 
