package sandbox.language.bc;

import org.modelcc.*;

public class IntegerLiteral extends Expression implements IModel
{
	@Value
	int value;
	

	public String toString ()
	{
		return ""+value;
	}
}
