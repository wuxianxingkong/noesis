package sandbox.language.bc;

import org.modelcc.*;

@Prefix("(A|a)(R|r)(R|r)(A|a)(Y|y)")
public class ArrayType extends Type
{
	@Prefix("\\[")
	@Suffix("\\]")
	@Separator(",")
	Range[] ranges;

}
