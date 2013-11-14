package sandbox.language.awk;

import org.modelcc.Prefix;

@Prefix("print")
public class AWKPrintSentence extends AWKSentence 
{
	AWKExpression argument;
	
	
	public String toString ()
	{
		return "print "+argument.toString();
	}

}
