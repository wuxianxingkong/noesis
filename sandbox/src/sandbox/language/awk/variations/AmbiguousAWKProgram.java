package sandbox.language.awk.variations;

import org.modelcc.IModel;

public class AmbiguousAWKProgram implements IModel
{
    AmbiguousAWKRule[] rules;


    public AmbiguousAWKRule[] getRules()
    {
    	return rules;
    }
    
    public AmbiguousAWKRule getRule (int n)
    {
    	return rules[n];
    }
}
