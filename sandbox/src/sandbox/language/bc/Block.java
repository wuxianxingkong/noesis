package sandbox.language.bc;

import org.modelcc.*;

@Prefix("(I|i)(N|n)(I|i)(C|c)(I|i)(O|o)")
@Suffix("(F|f)(I|i)(N|n)")
public class Block extends Statement implements IModel
{
	@Optional
	@Prefix("(V|v)(A|a)(R|r)")
	Variable[] variables;
	
	@Optional
	@Separator(";")
	@Suffix(";")
	Procedure[] procedures;
	
	@Separator(";")
	@Suffix(";")
	Statement[] statements;
	
}
