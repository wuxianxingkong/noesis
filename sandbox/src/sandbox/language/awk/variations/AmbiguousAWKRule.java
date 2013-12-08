package sandbox.language.awk.variations;

import org.modelcc.IModel;
import org.modelcc.Optional;

import sandbox.language.awk.*;

public class AmbiguousAWKRule implements IModel 
{
	@Optional
	private AWKPattern pattern;
	
	@Optional
	private AWKAction action;

	
	public AWKPattern getPattern ()
	{
		return pattern;
	}
	
	public AWKAction getAction ()
	{
		return action;
	}
}
