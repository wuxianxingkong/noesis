//Title:       Sudoku test suite
//Version:     0.9
//Copyright:   2011
//Author:      Fernando Berzal Galiano
//E-mail:      berzal@acm.org


import java.io.*;
import java.util.ArrayList;

public class TestSuite implements Runnable
{
	public static final String FICHERO_POR_DEFECTO = "SudokuHex.txt";
	public static final long   TIME_LIMIT = 120000;	
	
	private String[] sudokus;
	private int      resueltos;
	private int      errores;
	
	private long     plazo;
	private long     inicio;
	private long     fin;
	
	
	
	public TestSuite(String[] sudokus, long plazo)
	{
		this.sudokus = sudokus;
		this.plazo = plazo;
	}
	

	@Override
	public void run() 
	{
		SudokuHex solver;
		sandbox.ai.SudokuHex sudoku;
		sandbox.ai.SudokuHex solution;
		
		long   deadline;
		
		resueltos = 0;
		errores   = 0;
		
		inicio    = System.currentTimeMillis();
		fin       = inicio;

		if (plazo!=0) {
			deadline = inicio + plazo;
		} else {
			deadline = inicio + TIME_LIMIT;
		}
		

		for (int i=0; (i<sudokus.length) && (fin<deadline); i++) {
			
			System.err.println(i+"...");
			solver = new SudokuHex (sudokus[i]);
			solver.solve();
			
			// check results: sudoku.toString()
			
			sudoku = new sandbox.ai.SudokuHex(sudokus[i]);
			solution = new sandbox.ai.SudokuHex(solver.toString());
			
			System.err.println(solution);
			
			if (solution.isSolved() && solution.isCompatibleWith(sudoku))
				resueltos++;
			else
				errores++;
			
			fin = System.currentTimeMillis();
		}
		
		if (fin>deadline)
			resueltos--;
	}	

	
	
	public static void main(String[] args) 
		throws InterruptedException
	{
		String fichero;
		long   plazo;
		
		// Argumentos de entrada
		
		if (args.length==0) {
			
			System.err.println("Por favor, indique su fichero de prueba como argumento de este programa.");
			System.err.println("Asumiendo fichero por defecto...");
			fichero = FICHERO_POR_DEFECTO;
			
		} else {
			
			fichero = args[0];
		}
		
		if (args.length>1) {
			plazo = Long.parseLong(args[1]);
		} else {
			plazo = TIME_LIMIT;
		}
			
		// Test suite
		
		String[]   sudokus = getSudokus(fichero);
		TestSuite  testSuite = new TestSuite(sudokus,plazo);
	
		// Independent thread
		
		Thread t = new Thread(testSuite); // myRunnable does your calculations

		long startTime = System.currentTimeMillis();
		long endTime = startTime + plazo;

		// testSuite.run();

		// Workaround: Marks this thread as a daemon thread.
		// The Java Virtual Machine exits when the only threads running are all daemon threads.
		t.setDaemon(true); 
		
		t.start(); // Kick off calculations

		while (  (System.currentTimeMillis() < endTime)
			  && t.isAlive() && !t.isInterrupted()
			  && (testSuite.resueltos + testSuite.errores < testSuite.sudokus.length) ) {
		    // Still within time theshold, wait a little longer
		    try {
		         Thread.sleep(plazo/60);  // Sleep T/60 seconds
		    } catch (InterruptedException e) {
		         // Someone woke us up during sleep, that's OK
		    }
		}

		// t.interrupt();  // Tell the thread to [voluntarily] stop 
		// t.join();       // Wait for the thread to cleanup and finish

		// Resultado
		
		long tiempo = testSuite.fin - testSuite.inicio;
		
		System.out.println(testSuite.resueltos+", "+testSuite.errores+", "+tiempo);

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
