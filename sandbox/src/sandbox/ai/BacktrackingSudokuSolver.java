package sandbox.ai;

/**
 * Resolución de sudokus simple usando backtracking.
 * 
 * @author Fernando Berzal
 */

public class BacktrackingSudokuSolver extends AbstractSudokuSolver {

	private Sudoku sudoku;
	
	public BacktrackingSudokuSolver(String template) {
		super(template);
		sudoku = new Sudoku(template);
	}
	
	public boolean check ()
	{
		return sudoku.check();
	}
	
	public boolean checkX ()
	{
		return sudoku.checkX();
	}
	
	public void solve()
	{
		backtrack(sudoku);
	}
	
	private boolean backtrack (Sudoku sudoku)
	{
		int     i, j, v;
		int     x, y;
		boolean first;
		boolean fin = sudoku.isSolvedX();
		
		if (!fin) {
			
			// First empty cell

			x = -1;
			y = -1;
			
			first = false;
			
			for (i=0; (i<9) && !first; i++) 		
				for (j=0; (j<9) && !first; j++) 
					if (sudoku.isEmpty(i,j)) {
						x=i;
						y=j;
						first = true;
					}

			// Assign different values to (x,y) cell
			
			for (v=1; (v<=9) && !fin; v++) {
				sudoku.set(x,y,v);

				if (sudoku.checkX())
					fin = backtrack(sudoku);
			}
						
			if (!fin)
				sudoku.set(x,y,0);
		}
		
		return fin;
	}
	
	
	public String toString ()
	{
		return sudoku.toString();
	}
	
	
	// E/S
	

}
