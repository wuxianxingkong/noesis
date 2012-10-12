package sandbox.ai;

public abstract class Sudoku 
{
	protected int[][] sudoku;	

	public int get(int i, int j) 
	{
		return sudoku[i][j];
	}

	public void set(int i, int j, int v) 
	{
		sudoku[i][j] = v;
	}
	
	// Dimensions
	
	public abstract int size();
	
	// Empty cells
	
	public final int emptyCells ()
	{
		int i,j;
		int count = 0;
		
		for (i=0; i<sudoku.length; i++)
			for (j=0; j<sudoku[i].length; j++)
				if (isEmpty(i,j))
					count++;
		
		return count;
	}
		
	public abstract boolean isEmpty(int i, int j);

	public abstract int emptyValue ();

	
	// Check constraints
	
	public abstract boolean check ();

	// Solved?

	public boolean isSolved ()
	{
		return (emptyCells()==0) && check();
	}

	// Candidates
	
	public abstract int[] candidates (int i, int j);

	// Compatibility

	public boolean isCompatibleWith (SudokuX template)
	{
		for (int i=0; i<sudoku.length; i++)
			for (int j=0; j<sudoku[i].length; j++)
				if (!isEmpty(i,j) && (this.get(i,j)!=template.get(i,j)))
					return false;
		
		return true;
	}
	
	
}