package sandbox.ai;

/**
 * Resolución de sudokus simple usando backtracking.
 * 
 * @author Fernando Berzal
 */

public class BacktrackingSudokuSolver extends SudokuSolver {

	
	public BacktrackingSudokuSolver(Sudoku sudoku) {
		super(sudoku);
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
		boolean fin = sudoku.isSolved();
		
		if (!fin) {
			
			// First empty cell

			x = -1;
			y = -1;
			
			first = false;
			
			for (i=0; (i<sudoku.size()) && !first; i++) 		
				for (j=0; (j<sudoku.size()) && !first; j++) 
					if (sudoku.isEmpty(i,j)) {
						x=i;
						y=j;
						first = true;
					}

			// Assign different values to (x,y) cell
			
			int candidates[] = sudoku.candidates(x,y);
			
			for (v=0; (v<candidates.length) && !fin; v++) {
				sudoku.set(x,y,candidates[v]);

				if (sudoku.check())
					fin = backtrack(sudoku);
			}
						
			if (!fin)
				sudoku.set(x,y,sudoku.emptyValue());
		}
		
		return fin;
	}
		

}
