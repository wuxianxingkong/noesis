package sandbox.language.awk;

import org.modelcc.Pattern;
import org.modelcc.Value;

@Pattern(regExp="/[^/]*/")
public class AWKRegularExpressionPattern extends AWKPattern 
{
	@Value
	String regexp;
}
