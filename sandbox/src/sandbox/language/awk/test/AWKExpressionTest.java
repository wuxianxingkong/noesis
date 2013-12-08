package sandbox.language.awk.test;

import static org.junit.Assert.*;

import org.junit.Test;

import sandbox.language.ModelCCTest;
import sandbox.language.awk.AWKExpression;
import sandbox.language.awk.AWKField;

public class AWKExpressionTest extends ModelCCTest<AWKExpression>
{
	@Test
	public void testValidFieldExpression()
		throws Exception
	{
		AWKExpression expression = parse("$1");
		
		assertEquals("$1", expression.toString());
		assertTrue( expression instanceof AWKField );
	}

	@Test(expected=org.modelcc.parser.ParserException.class)
	public void testInvalidRegularExpression()
		throws Exception
	{
		parse("/abc");
	}
}
