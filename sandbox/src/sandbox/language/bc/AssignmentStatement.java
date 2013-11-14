package sandbox.language.bc;

import org.modelcc.*;

public class AssignmentStatement extends Statement 
{
	private Identifier lvalue;
	
	@Prefix(":=")
	private Expression rvalue;
	
	public Identifier getLValue ()
	{
		return lvalue;
	}
	
	public Expression getRValue ()
	{
		return rvalue;
	}

}
