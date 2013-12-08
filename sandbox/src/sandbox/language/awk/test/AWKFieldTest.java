package sandbox.language.awk.test;

import static org.junit.Assert.*;

import org.junit.Test;

import sandbox.language.ModelCCTest;
import sandbox.language.awk.AWKField;

public class AWKFieldTest extends ModelCCTest<AWKField>
{

	@Test
	public void testValidField()
		throws Exception
	{
		AWKField field = parse("$0");
		
		assertEquals("$0", field.toString());
		assertEquals(0, field.getNumber());
	}

	@Test
	public void testValidField123()
		throws Exception
	{
		AWKField field = parse("$123");
		
		assertEquals("$123", field.toString());
		assertEquals(123, field.getNumber());
	}

	@Test(expected=org.modelcc.parser.ParserException.class)
	public void testInvalidField()
		throws Exception
	{
		parse("$abc");
	}

}
