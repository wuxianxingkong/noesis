package sandbox.ai;

//Title:       Sudoku
//Version:     0.9
//Copyright:   2011
//Author:      Fernando Berzal Galiano
//E-mail:      berzal@acm.org

import java.util.Date;

/**
* Prueba básica de un Sudoku.
* 
* @author Fernando Berzal
*/

public class TestX 
{
	public static final String SUDOKU_X = "008704900020005000065080400000000007003000600500000000059060130000800040001309700";

		
	public static void main (String args[])
	{
		String       plantilla;
		Sudoku       sudoku;
		Solver solver;
		
		long   inicio;
		long   fin;
		long   tiempo;
		
		// Argumentos de entrada
		
		if (args.length==0) {
			
			System.err.println("Por favor, indique su sudoku como argumento de este programa.");
			System.err.println("Asumiendo sudoku por defecto...");
			plantilla = SUDOKU_X;
			
		} else {
			
			plantilla = "";
			
			for (int i=0;i<args.length; i++)
				plantilla += args[i];
			
		}
		
	
		// Resolución del sudoku

		inicio = (new Date()).getTime();

		sudoku = new SudokuX(plantilla);
		
		solver = new BacktrackingSolver (sudoku);	
		solver.solve();
		
		fin = (new Date()).getTime();
		
		tiempo = fin - inicio;
		
		
		// Resultado
		
		System.out.println("Solución:");
		System.out.println(solver.toString());
		
		if (!solver.isSolved())
			System.out.println("ERROR: SUDOKU NO RESUELTO");
		else
			System.out.println("OK");
				
		System.out.println("Tiempo: "+tiempo+" milisegundos.");
	}

}
