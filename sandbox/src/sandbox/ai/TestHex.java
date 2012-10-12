package sandbox.ai;

// Title:       Sudoku
// Version:     0.1
// Copyright:   2012
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import java.util.Date;

/**
* Prueba básica de un Sudoku hexadecimal.
* 
* @author Fernando Berzal
*/

public class TestHex
{
	public static final String SUDOKU_POR_DEFECTO = "a0....3.fd...c.....d459.b.c..2f.be.cd.280...1.7.5..2.e0..834..b."
		     + "....2.83...f.061......1.e3..4f..78..6df4.....e..4.d..c..1.26.b87"
			 + "ca7.f3.d..0..5.4..f.....649a..0b..04..b7.5......dbe.a...27.3...."
			 + ".c..e4d..f6.b..a.d.b...27e.10.cf.7a..1.b.0dc8.....5...7f.2....ed";

		
	public static void main (String args[])
	{
		String       plantilla;
		SudokuHex    sudoku;
		SudokuSolver solver;
		
		long   inicio;
		long   fin;
		long   tiempo;
		
		// Argumentos de entrada
		
		if (args.length==0) {
			
			System.err.println("Por favor, indique su sudoku como argumento de este programa.");
			System.err.println("Asumiendo sudoku por defecto...");
			plantilla = SUDOKU_POR_DEFECTO;
			
		} else {
			
			plantilla = "";
			
			for (int i=0;i<args.length; i++)
				plantilla += args[i];
			
		}
		
	
		// Resolución del sudoku

		inicio = (new Date()).getTime();

		sudoku = new SudokuHex (plantilla);

		solver = new BacktrackingSudokuSolver (sudoku);
		
		solver.solve();
	
		
		fin = (new Date()).getTime();
		
		tiempo = fin - inicio;
		
		
		// Resultado
		
		System.out.println("Solución:");
		System.out.println(sudoku.toString());
		
		if (!sudoku.isSolved())
			System.out.println("ERROR: SUDOKU NO RESUELTO");
		else
			System.out.println("OK");
		
		System.out.println("Tiempo: "+tiempo+" milisegundos.");
	}

}
 
                
 
