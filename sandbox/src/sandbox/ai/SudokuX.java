package sandbox.ai;

//Title:       Sudoku
//Version:     0.9
//Copyright:   2011
//Author:      Fernando Berzal Galiano
//E-mail:      berzal@acm.org

public class SudokuX extends Sudoku9 
{
	public SudokuX (String plantilla)
	{
		super(plantilla);
	}

	public SudokuX (int[][] tablero)
	{
		super(tablero);
	}


	// Constraints
	
	protected int[] diagonal1 ()
	{
		int i;
		
		for (i=0; i<9; i++)
			data[i] = sudoku[i][i];
		
		return data;
	}
	
	public boolean checkDiagonal1 ()
	{
		return checkSet(diagonal1());		
	}
	
	protected int[] diagonal2 ()
	{
		int i;
		
		for (i=0; i<9; i++)
			data[i] = sudoku[i][8-i];
		
		return data;
	}
	
	public boolean checkDiagonal2 ()
	{
		return checkSet(diagonal2());		
	}
	
	

	public boolean check ()
	{
		boolean ok = super.check();
		
		if (ok)
		    ok = checkDiagonal1();
		
		if (ok)
			ok = checkDiagonal2();
		
		return ok;
	}
	

}
