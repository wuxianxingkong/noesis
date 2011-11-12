//Title:       Sudoku test suite
//Version:     0.9
//Copyright:   2011
//Author:      Fernando Berzal Galiano
//E-mail:      berzal@acm.org


import java.io.*;
import java.util.ArrayList;
import sandbox.ai.Sudoku;

public class TestSuite 
{
	public static final String FICHERO_POR_DEFECTO = "80Sudokus-X.txt";
	public static final long   TIME_LIMIT = 120000;

	public static void main(String[] args) 
	{
		String       fichero;
		SudokuSolver solver;
		Sudoku       sudoku;
		Sudoku       solution;
		
		long   inicio;
		long   fin;
		long   tiempo;
		long   deadline;
		
		int    resueltos;
		int    errores;
		
		// Argumentos de entrada
		
		if (args.length==0) {
			
			System.err.println("Por favor, indique su fichero de prueba como argumento de este programa.");
			System.err.println("Asumiendo fichero por defecto...");
			fichero = FICHERO_POR_DEFECTO;
			
		} else {
			
			fichero = args[0];
		}
		
		String[] sudokus = getSudokus(fichero);
	
		// Resolución del sudoku

		resueltos = 0;
		errores   = 0;
		
		inicio    = System.currentTimeMillis();
		fin       = inicio;

		if (args.length>1) {
			deadline = inicio + Integer.parseInt(args[1]);
		} else {
			deadline = inicio + TIME_LIMIT;
		}
		

		for (int i=0; (i<sudokus.length) && (fin<deadline); i++) {
			
			System.err.println(i+"...");
			solver = new SudokuSolver (sudokus[i]);
			solver.solve();
			
			// check results: sudoku.toString()
			
			sudoku = new Sudoku(sudokus[i]);
			solution = new Sudoku(solver.toString());
			
			System.err.println(solution);
			
			if (solution.isSolvedX() && solution.isCompatibleWith(sudoku))
				resueltos++;
			else
				errores++;
			
			fin = System.currentTimeMillis();
		}
		
		if (fin>deadline)
			resueltos--;
		
		tiempo = fin - inicio;
		
		
		// Resultado
		
		System.out.println(resueltos+", "+errores+", "+tiempo);
	}
	
	
	private static String[] getSudokus (String fichero)
	{
	   ArrayList<String> list = new ArrayList<String>();
	   String[] array;
	   String   line;
	   FileReader     reader;
	   BufferedReader input;
	   
	   try {
		   reader = new FileReader(fichero);
		   input  = new BufferedReader(reader);
		   
		   line = input.readLine();
		   
		   while (line!=null) {
			   list.add(line);
			   line = input.readLine();
		   }
		   
		   input.close();
		   
	   } catch (IOException error) {
		   System.err.println(error);
	   }
	   
	   System.err.println(list.size()+ " sudokus de prueba.");
	   array = new String[list.size()];
	   list.toArray(array);
	   
	   return array;
	}

}
