package sandbox.language.awk.test;

import static org.junit.Assert.*;

import org.junit.Test;

import org.modelcc.io.ModelReader;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserFactory;

import sandbox.language.awk.AWKProgram;

// http://www.staff.science.uu.nl/~oostr102/docs/nawk/nawk_7.html#SEC10

public class VerySimpleTest 
{
	@Test
	public void testverySimpleProgram()
		throws Exception
	{
		AWKProgram program = parse("/foo/ { print $0 }");
		
		assertEquals( 1, program.getRules().length );
		assertNotNull( program.getRule(0).getPattern() );
		assertNotNull( program.getRule(0).getAction() );
		assertEquals("{ print $0 }", program.getRule(0).getAction().toString());
		assertEquals("/foo/", program.getRule(0).getPattern().toString());
	}
	

	@Test
	public void testOptionalPattern()
		throws Exception
	{
		AWKProgram program = parse("{}");
		
		assertEquals( 1, program.getRules().length );
		assertNull( program.getRule(0).getPattern() );
		assertNotNull( program.getRule(0).getAction() );
	}

	@Test
	public void testOptionalSentence()
		throws Exception
	{
		AWKProgram program = parse("/foo/");
		
		assertEquals( 1, program.getRules().length );
		assertNotNull( program.getRule(0).getPattern() );
		assertNull( program.getRule(0).getAction() );
		assertEquals("/foo/", program.getRule(0).getPattern().toString() );
	}
	
	public AWKProgram parse (String awk)
		throws Exception
	{
		Parser<AWKProgram> parser;
		AWKProgram program;
		
		try {
			parser = createParser();
		} catch (Exception error) {
			System.err.println("Parser creation error: "+error);
			throw error;
		}

		try {
			program = parser.parse(awk);
		} catch (Exception error) {
			System.err.println("Parser error: "+error);
			throw error;
		}
		
		return program;
	}

	public Parser<AWKProgram> createParser ()
		throws Exception
	{
		ModelReader modelReader = new JavaModelReader(AWKProgram.class);
		Model model = modelReader.read();
		Parser<AWKProgram> parser = ParserFactory.create(model,ParserFactory.WHITESPACE);
		
		return parser;
	}
}
