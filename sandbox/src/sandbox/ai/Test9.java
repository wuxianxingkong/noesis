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

public class Test9 
{
	public static final String SUDOKU_POR_DEFECTO = "97.3.4.65"
		                                          + ".2.5.6.8."
		                                          + "........."
		                                          + "..58.29.."
		                                          + "..2.4.3.."
		                                          + "..87.51.."
		                                          + "........."
		                                          + ".6.2.8.3."
		                                          + "84.1.9.27";

		
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
			plantilla = SUDOKU_POR_DEFECTO;
			
		} else {
			
			plantilla = "";
			
			for (int i=0;i<args.length; i++)
				plantilla += args[i];
			
		}
		
	
		// Resolución del sudoku

		inicio = (new Date()).getTime();

		sudoku = new Sudoku9(plantilla);
		
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
