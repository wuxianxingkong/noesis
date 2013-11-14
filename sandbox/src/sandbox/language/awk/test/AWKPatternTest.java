package sandbox.language.awk.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.modelcc.io.ModelReader;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserFactory;

import sandbox.language.awk.AWKPattern;
import sandbox.language.awk.AWKRegularExpressionPattern;

public class AWKPatternTest 
{
	@Test
	public void testFieldExpression()
		throws Exception
	{
		AWKPattern expression = parse("/foo/");
		
		assertEquals("/foo/", expression.toString());
		assertTrue( expression instanceof AWKRegularExpressionPattern );
	}

	@Test(expected=org.modelcc.parser.ParserException.class)
	public void testInvalidRegularExpression()
		throws Exception
	{
		parse("/abc");
	}
	
	public AWKPattern parse (String awk)
			throws Exception
	{
		Parser<AWKPattern> parser;
		AWKPattern pattern;

		try {
			parser = createParser();
		} catch (Exception error) {
			System.err.println("Parser creation error: "+error);
			throw error;
		}

		try {
			pattern = parser.parse(awk);
		} catch (Exception error) {
			System.err.println("Parser error: "+error);
			throw error;
		}

		return pattern;
	}

	public Parser<AWKPattern> createParser ()
		throws Exception
	{
		ModelReader modelReader = new JavaModelReader(AWKPattern.class);
		Model model = modelReader.read();
		Parser<AWKPattern> parser = ParserFactory.create(model,ParserFactory.WHITESPACE);

		return parser;
	}

}
