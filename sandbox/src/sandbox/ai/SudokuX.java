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

	
	// Candidate generation
	// - DOMAIN: 11x in 2min (2s)
	// - Valid values: 11x in 2min (2s)
	// - MRV heuristic: 80x in 37s
	
	private int[]     DOMAIN = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private boolean[] valid  = new boolean[10];
    
	public int[] values (int i, int j)
	{
		boolean[] valid = checkCandidates(i,j);
		int       count = 0;
		
		for (int v=0; v<DOMAIN.length; v++)
			if (valid[DOMAIN[v]])
				count++;
		
		int[] candidates = new int[count];
		int   position = 0;
		
		for (int v=0; v<DOMAIN.length; v++) 
			if (valid[DOMAIN[v]]) {
				candidates[position] = DOMAIN[v];
				position++;
			}
		
		return candidates; // candidates vs. DOMAIN
	}

	public int valueCount (int i, int j)
	{
		boolean[] valid = checkCandidates(i,j);
		int       count = 0;
		
		for (int v=0; v<DOMAIN.length; v++)
			if (valid[DOMAIN[v]])
				count++;
		
		return count;
	}

	private boolean[] checkCandidates(int i, int j)
	{
		for (int v=0; v<valid.length; v++)
			valid[v] = true;
		
		// Row
		
		for (int c=0; c<size(); c++)
			if (!isEmpty(i,c))
				valid[sudoku[i][c]] = false;

		// Column
		
		for (int r=0; r<size(); r++)
			if (!isEmpty(r,j))
				valid[sudoku[r][j]] = false;

		// Block

		int row = 3*(i/3);
		int column = 3*(j/3);
		
		for (int r=row; r<row+3; r++)
			for (int c=column; c<column+3; c++)
				if (!isEmpty(r,c))
					valid[sudoku[r][c]] = false;		
		
		// Diagonals
		
		if (i==j)
 		   for (int p=0; p<9; p++)
			   if (!isEmpty(p,p))
				   valid[sudoku[p][p]] = false;

		if ((i==8-j) || (j==8-i))
		   for (int p=0; p<9; p++)
			   if (!isEmpty(p,8-p))
				   valid[sudoku[p][8-p]] = false;
		
		return valid;
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
