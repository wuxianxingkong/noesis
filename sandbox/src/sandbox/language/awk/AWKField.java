package sandbox.language.awk;

import org.modelcc.Prefix;
import org.modelcc.Value;

@Prefix("$")
public class AWKField extends AWKExpression
{
	@Value
	int number;  // better than... IntegerLiteral number;

	public String toString ()
	{
		return "$"+number;
	}
}
