package sandbox.language.awk;

import org.modelcc.IModel;
import org.modelcc.Optional;

public class AWKRule implements IModel 
{
	@Optional
	AWKPattern pattern;
	
	@Optional
	AWKAction action;

}
