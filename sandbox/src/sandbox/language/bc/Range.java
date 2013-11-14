package sandbox.language.bc;

import org.modelcc.*;

public class Range implements IModel
{
	IntegerLiteral min;
	
	@Prefix("\\.\\.")
	IntegerLiteral max;
	
}
