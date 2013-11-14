package sandbox.language.bc.test;

import static org.junit.Assert.*;

import org.junit.Test;

import org.modelcc.io.ModelReader;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserFactory;

import sandbox.language.bc.*;


public class ProgramTest 
{
	@Test
	public void testEmptyProgram()
		throws Exception
	{
		Program program = parse("procedimiento simple; inicio fin.");
		
		assertEquals( "simple", program.getEntryPoint().getId().toString() );
		assertNull ( program.getEntryPoint().getBlock() );
	}

	@Test
	public void testHelloProgram()
		throws Exception
	{
		Program program = parse("procedimiento simple; inicio salida 'Hola' fin.");
		
		assertEquals( "simple", program.getEntryPoint().getId().toString() );
		assertNull ( program.getEntryPoint().getBlock() );
	}
	

	@Test(expected=org.modelcc.parser.ParserException.class)
	public void testInvalidProgram()
		throws Exception
	{
		Program program = parse("procedimiento simple; inicio fin;");
		
		assertEquals( "simple", program.getEntryPoint().getId().toString() );
		assertNull ( program.getEntryPoint().getBlock() );
	}

	
	public Program parse (String awk)
		throws Exception
	{
		Parser<Program> parser;
		Program program;
		
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

	public Parser<Program> createParser ()
		throws Exception
	{
		ModelReader modelReader = new JavaModelReader(Program.class);
		Model model = modelReader.read();
		Parser<Program> parser = ParserFactory.create(model,ParserFactory.WHITESPACE);
		
		return parser;
	}
}
