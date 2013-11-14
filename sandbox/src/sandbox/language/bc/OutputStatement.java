package sandbox.language.bc;

import org.modelcc.*;

@Prefix("(S|s)(A|a)(L|l)(I|i)(D|d)(A|a)")
public class OutputStatement extends Statement 
{
	private Expression expression;
	
	public Expression getExpression ()
	{
		return expression;
	}
	
	public String toString ()
	{
		return "salida "+expression.toString();
	}

}
