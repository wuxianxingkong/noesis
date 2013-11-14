package sandbox.language.bc;

import org.modelcc.*;

@Prefix("(I|i)(N|n)(I|i)(C|c)(I|i)(O|o)")
@Suffix("(F|f)(I|i)(N|n)")
public class Block extends Statement implements IModel
{
	@Optional
	@Prefix("(V|v)(A|a)(R|r)")
	@Suffix("(F|f)(I|i)(N|n);")
	@Minimum(0) // TODO Workaround
	private Variable[] variables;
	
	//@Separator(";")
	//@Suffix(";")
	//@Minimum(0) // TODO Workaround
	//Procedure[] procedures;
	
	@Optional
	@Separator(";")
	@Suffix(";")
	private Statement[] statements;
	
	
	public Variable[] getVariables ()
	{
		return variables;
	}
	
	public Procedure[] getProcedures ()
	{
		return null; // procedures;
	}
	
	public Statement[] getStatements ()
	{
		return statements;
	}
	
}
