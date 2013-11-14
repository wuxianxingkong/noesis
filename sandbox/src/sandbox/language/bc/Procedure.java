package sandbox.language.bc;

import org.modelcc.*;

@Prefix("(P|p)(R|r)(O|o)(C|c)(E|e)(D|d)(I|i)(M|m)(I|i)(E|e)(N|n)(T|t)(O|o)")
public class Procedure implements IModel
{
	private Identifier id;
	
	//@Optional
	//@Prefix("(")
	//@Suffix(")")
	//Parameter[] parameters;
	
	@Prefix(";")
	private Block block;
	
	
	public Identifier getId ()
	{
		return id;
	}
	
	public Block getBlock ()
	{
		return block;
	}

}
