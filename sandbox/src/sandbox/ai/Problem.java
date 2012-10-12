package sandbox.ai;

public abstract class Problem 
{
	
	// Variables

	public abstract int variables();
	
	public final int unasignedVariables ()
	{
		int i;
		int count = 0;
		int total = variables();
		
		for (i=0; i<total; i++)
			if (isUnasigned(i))
			   count++;
		
		return count;
	}

	public abstract boolean isUnasigned(int var);
	
	
	// Set variable values
	
	public abstract void set (int var, int value);
	
	public abstract void clear (int var);
	
	// Get candidate values

	public abstract int[] values (int var);
	
	
	// Constraint checking
	
	public abstract boolean check();

	public boolean isSolved() {
		return (unasignedVariables()==0) && check();
	}

}