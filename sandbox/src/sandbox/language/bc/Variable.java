package sandbox.language.bc;

import org.modelcc.*;

public class Variable implements IModel
{
	Identifier id;
	
	@Prefix(":")
	Type type;
}
