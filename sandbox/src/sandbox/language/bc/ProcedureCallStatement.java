package sandbox.language.bc;

import org.modelcc.*;

public class ProcedureCallStatement extends Statement 
{
	private Identifier id;
	
	@Prefix("\\(")
	@Suffix("\\)")
	@Separator(",")
	private Expression[] arguments;
	
	
	public Identifier getProcedureId ()
	{
		return id;
	}
	
	public Expression[] getArguments ()
	{
		return arguments;
	}

}
