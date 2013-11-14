package sandbox.language.bc;

import org.modelcc.*;

@Prefix("'")
@Suffix("'")
@Pattern(regExp="([^']|'')*")
public class StringLiteral extends Expression implements IModel
{
	@Value
	String value;
	
	public String toString ()
	{
		return "'"+value+"'";
	}
}
