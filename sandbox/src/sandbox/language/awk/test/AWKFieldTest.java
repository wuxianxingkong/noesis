package sandbox.language.awk.test;

import static org.junit.Assert.*;

import org.junit.Test;

import org.modelcc.io.ModelReader;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserFactory;

import sandbox.language.awk.AWKField;

public class AWKFieldTest {

	@Test
	public void testValid()
		throws Exception
	{
		AWKField field = parse("$0");
		
		assertEquals("$0", field.toString());
		assertEquals(0, field.getNumber());
	}

	@Test
	public void testValid123()
		throws Exception
	{
		AWKField field = parse("$123");
		
		assertEquals("$123", field.toString());
		assertEquals(123, field.getNumber());
	}

	@Test(expected=org.modelcc.parser.ParserException.class)
	public void testInvalid()
		throws Exception
	{
		parse("$abc");
	}
	
	public AWKField parse (String awk)
			throws Exception
	{
		Parser<AWKField> parser;
		AWKField field;

		try {
			parser = createParser();
		} catch (Exception error) {
			System.err.println("Parser creation error: "+error);
			throw error;
		}

		try {
			field = parser.parse(awk);
		} catch (Exception error) {
			System.err.println("Parser error: "+error);
			throw error;
		}

		return field;
	}

	public Parser<AWKField> createParser ()
		throws Exception
	{
		ModelReader modelReader = new JavaModelReader(AWKField.class);
		Model model = modelReader.read();
		Parser<AWKField> parser = ParserFactory.create(model,ParserFactory.WHITESPACE);

		return parser;
	}


}
