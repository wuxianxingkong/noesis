package sandbox.language.awk.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.modelcc.io.ModelReader;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserFactory;

import sandbox.language.awk.AWKExpression;
import sandbox.language.awk.AWKField;

public class AWKExpressionTest 
{
	@Test
	public void testFieldExpression()
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
	
	public AWKExpression parse (String awk)
			throws Exception
	{
		Parser<AWKExpression> parser;
		AWKExpression expression;

		try {
			parser = createParser();
		} catch (Exception error) {
			System.err.println("Parser creation error: "+error);
			throw error;
		}

		try {
			expression = parser.parse(awk);
		} catch (Exception error) {
			System.err.println("Parser error: "+error);
			throw error;
		}

		return expression;
	}

	public Parser<AWKExpression> createParser ()
		throws Exception
	{
		ModelReader modelReader = new JavaModelReader(AWKExpression.class);
		Model model = modelReader.read();
		Parser<AWKExpression> parser = ParserFactory.create(model,ParserFactory.WHITESPACE);

		return parser;
	}

}
