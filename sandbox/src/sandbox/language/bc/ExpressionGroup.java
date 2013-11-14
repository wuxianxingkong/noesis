package sandbox.language.bc;

import org.modelcc.*;

@Prefix("\\(")
@Suffix("\\)")
public class ExpressionGroup extends Expression
{
	Expression expression;

}
