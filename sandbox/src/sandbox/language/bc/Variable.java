package sandbox.language.bc;

import org.modelcc.*;

public class Variable implements IModel
{
	private Identifier id;
	
	@Prefix(":")
	private Type type;
	
	
	public Identifier getId ()
	{
		return id;
	}
	
	public Type getType ()
	{
		return type;
	}
}
