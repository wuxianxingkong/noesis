package sandbox.language.awk.test;

import static org.junit.Assert.*;

import org.junit.Test;

import org.modelcc.io.ModelReader;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserFactory;

import sandbox.language.awk.*;

public class AWKActionTest 
{
	@Test
	public void testPrintAction()
		throws Exception
	{
		AWKAction action = parse("{ print $0 }");
		
		assertEquals("{ print $0 }", action.toString());
		assertTrue(action.getStatement() instanceof AWKPrintStatement);
		
		AWKPrintStatement print = (AWKPrintStatement) action.getStatement();
		
		assertEquals("$0", print.getArgument().toString() );
		assertTrue ( print.getArgument() instanceof AWKField );
	}

	@Test(expected=org.modelcc.parser.ParserException.class)
	public void testInvalidRegularExpression()
		throws Exception
	{
		parse("{ abc");
	}
	
	public AWKAction parse (String awk)
			throws Exception
	{
		Parser<AWKAction> parser;
		AWKAction action;

		try {
			parser = createParser();
		} catch (Exception error) {
			System.err.println("Parser creation error: "+error);
			throw error;
		}

		try {
			action = parser.parse(awk);
		} catch (Exception error) {
			System.err.println("Parser error: "+error);
			throw error;
		}

		return action;
	}

	public Parser<AWKAction> createParser ()
		throws Exception
	{
		ModelReader modelReader = new JavaModelReader(AWKAction.class);
		Model model = modelReader.read();
		Parser<AWKAction> parser = ParserFactory.create(model,ParserFactory.WHITESPACE);

		return parser;
	}

}

