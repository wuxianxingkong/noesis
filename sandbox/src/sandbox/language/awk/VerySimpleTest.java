package sandbox.language.awk;

import static org.junit.Assert.*;

import org.junit.Test;

import org.modelcc.io.ModelReader;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserFactory;

// http://www.staff.science.uu.nl/~oostr102/docs/nawk/nawk_7.html#SEC10

public class VerySimpleTest 
{
	@Test
	public void testverySimpleProgram()
		throws Exception
	{
		AWKProgram program = parse("/foo/ { print $0 }");
		
		assertEquals( 1, program.rules.length );
		assertNotNull( program.rules[0].pattern );
		assertNotNull( program.rules[0].action );
		assertEquals("print $0", program.rules[0].action.sentence.toString());
		assertEquals("/foo/", ((AWKRegularExpressionPattern)program.rules[0].pattern).regexp);
	}
	

	@Test
	public void testOptionalPattern()
		throws Exception
	{
		AWKProgram program = parse("{}");
		
		assertEquals( 1, program.rules.length );
		assertNull( program.rules[0].pattern );
		assertNotNull( program.rules[0].action );
	}

	@Test
	public void testOptionalSentence()
		throws Exception
	{
		AWKProgram program = parse("/foo/");
		
		assertEquals( 1, program.rules.length );
		assertNotNull( program.rules[0].pattern );
		assertNull( program.rules[0].action );
		assertEquals("/foo/", ((AWKRegularExpressionPattern)program.rules[0].pattern).regexp);
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
