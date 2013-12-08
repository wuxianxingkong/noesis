package sandbox.language.awk.test;

import static org.junit.Assert.*;

import org.junit.Test;

import sandbox.language.ModelCCTest;
import sandbox.language.awk.AWKPattern;
import sandbox.language.awk.AWKRegularExpressionPattern;

public class AWKPatternTest extends ModelCCTest<AWKPattern> 
{
	@Test
	public void testValidPattern()
		throws Exception
	{
		AWKPattern expression = parse("/foo/");
		
		assertEquals("/foo/", expression.toString());
		assertTrue( expression instanceof AWKRegularExpressionPattern );
	}

	@Test(expected=org.modelcc.parser.ParserException.class)
	public void testInvalidPattern()
		throws Exception
	{
		parse("/abc");
	}

}
