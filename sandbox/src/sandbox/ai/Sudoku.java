package sandbox.ai;

public abstract class Sudoku extends Problem 
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
	
	public void set (int var, int value)
	{
		set( var/size(), var%size(), value);
	}
	
	public void clear (int var)
	{
		set( var/size(), var%size(), emptyValue());
	}
	
	// Dimensions
	
	public abstract int size();
	
	public final int variables ()
	{
		return size()*size();
	}
	
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
	
	public final boolean isUnassigned (int var)
	{
		return isEmpty(var/size(), var%size());
	}
	
	// Check

	public abstract boolean check (int i, int j);
	
	public final boolean check (int var)
	{
		return check(var/size(), var%size());
	}

	// Candidates
	
	public abstract int[] values (int i, int j);

	public abstract int valueCount (int i, int j);

	public final int[] values (int var)
	{
		return values(var/size(), var%size());
	}

	public final int valueCount (int var)
	{
		return valueCount(var/size(), var%size());
	}

	// Compatibility

	public boolean isCompatibleWith (Sudoku template)
	{
		for (int i=0; i<sudoku.length; i++)
			for (int j=0; j<sudoku[i].length; j++)
				if (!template.isEmpty(i,j) && (this.get(i,j)!=template.get(i,j)))
					return false;
		
		return true;
	}
	
	
}