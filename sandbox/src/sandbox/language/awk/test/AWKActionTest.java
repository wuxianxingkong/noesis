package sandbox.language.awk.test;

import static org.junit.Assert.*;

import org.junit.Test;

import sandbox.language.ModelCCTest;
import sandbox.language.awk.*;

public class AWKActionTest extends ModelCCTest<AWKAction>
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
	public void testInvalidAction()
		throws Exception
	{
		parse("{ abc");
	}
	
}

