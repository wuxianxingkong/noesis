package sandbox.language.bc;

import org.modelcc.*;

@Pattern(regExp="[a-zA-Z_Ò—]([a-zA-Z_Ò—]|[0-9])*")
public class Identifier extends Expression implements IModel
{

	@Value
	private String id;
	
	public String toString ()
	{
		return id;
	}
}
