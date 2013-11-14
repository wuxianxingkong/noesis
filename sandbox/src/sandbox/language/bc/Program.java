package sandbox.language.bc;

import org.modelcc.*;

@Suffix(".")
public class Program implements IModel
{
	private Procedure main;
	
	public Procedure getEntryPoint()
	{
		return main;
	}

}
