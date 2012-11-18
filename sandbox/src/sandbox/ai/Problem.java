package sandbox.ai;

public abstract class Problem 
{
	
	// Variables

	public abstract int variables();
	
	public final int unassignedVariables ()
	{
		int i;
		int count = 0;
		int total = variables();
		
		for (i=0; i<total; i++)
			if (isUnassigned(i))
			   count++;
		
		return count;
	}

	public abstract boolean isUnassigned(int var);
	
	
	// Set variable values
	
	public abstract void set (int var, int value);
	
	public abstract void clear (int var);
	
	// Get candidate values

	public abstract int[] values (int var);
	
	public int valueCount (int var)
	{
		return values(var).length;
	}
	
	// Constraint checking
	
	public abstract boolean check();

	public abstract boolean check(int var);

	public boolean isComplete() {
		return (unassignedVariables()==0);
	}
	
	public boolean isSolved() {
		return (unassignedVariables()==0) && check();
	}

}