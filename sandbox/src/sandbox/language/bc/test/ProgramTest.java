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
	@Test(expected=org.modelcc.parser.ParserException.class)
	public void testNullProgram()
		throws Exception
	{
		Program program = parse("procedimiento simple.");
		
		assertEquals( "simple", program.getEntryPoint().getId().toString() );
		assertNull ( program.getEntryPoint().getBlock() );
	}
	
	@Test
	public void testEmptyProgram()
		throws Exception
	{
		Program program = parse("procedimiento simple; inicio fin.");
		
		assertEquals( "simple", program.getEntryPoint().getId().toString() );
		assertNotNull ( program.getEntryPoint().getBlock() );
	}

	@Test
	public void testHelloProgram()
		throws Exception
	{
		Program program = parse("procedimiento simple; inicio salida 'Hola'; fin.");
		
		assertEquals( "simple", program.getEntryPoint().getId().toString() );
		assertNotNull ( program.getEntryPoint().getBlock() );
		assertNull( program.getEntryPoint().getBlock().getVariables() );
		assertEquals( 1, program.getEntryPoint().getBlock().getStatements().length );
	}

	@Test
	public void testVarProgram()
		throws Exception
	{
		Program program = parse("procedimiento simple; inicio var x: tipo; entrada x; salida x; fin.");
		
		assertEquals( "simple", program.getEntryPoint().getId().toString() );
		assertNotNull ( program.getEntryPoint().getBlock() );
		assertEquals( 1, program.getEntryPoint().getBlock().getVariables().length );
		assertEquals( 2, program.getEntryPoint().getBlock().getStatements().length );
	}

	@Test
	public void testVarsProgram()
		throws Exception
	{
		Program program = parse("procedimiento simple; inicio var x: tipo; y: tipo; entrada x; salida x; fin.");
		
		assertEquals( "simple", program.getEntryPoint().getId().toString() );
		assertNotNull ( program.getEntryPoint().getBlock() );
		assertEquals( 2, program.getEntryPoint().getBlock().getVariables().length );
		assertEquals( 2, program.getEntryPoint().getBlock().getStatements().length );
	}

	@Test
	public void testNestedProgram()
		throws Exception
	{
		Program program = parse ( "procedimiento compuesto; "
	                            + "inicio "
				                + "  var x: tipo; y: tipo;"
				                + "  procedimiento simple; inicio fin; "
	                            + "  entrada x;"
				                + "  salida x;"
				                + "fin.");
		
		assertEquals( "compuesto", program.getEntryPoint().getId().toString() );
		assertNotNull ( program.getEntryPoint().getBlock() );
		assertEquals( 2, program.getEntryPoint().getBlock().getVariables().length );
		assertEquals( 2, program.getEntryPoint().getBlock().getStatements().length );
		assertEquals( "entrada x", program.getEntryPoint().getBlock().getStatements()[0].toString() );
		assertEquals( "salida x", program.getEntryPoint().getBlock().getStatements()[1].toString() );
		assertEquals( 1, program.getEntryPoint().getBlock().getProcedures().length );
		assertEquals( "simple", program.getEntryPoint().getBlock().getProcedures()[0].getId().toString() );
	}

	
	@Test
	public void testEmptyVarProgram()
		throws Exception
	{
		Program program = parse("procedimiento simple; inicio var entrada x; salida x; fin.");
		
		assertEquals( "simple", program.getEntryPoint().getId().toString() );
		assertNotNull ( program.getEntryPoint().getBlock() );
		assertNull (program.getEntryPoint().getBlock().getVariables() );
		assertEquals( 2, program.getEntryPoint().getBlock().getStatements().length );
	}

	@Test(expected=org.modelcc.parser.ParserException.class)
	public void testInvalidProgram1()
		throws Exception
	{
		Program program = parse("procedimiento simple; inicio fin;");
		
		assertEquals( "simple", program.getEntryPoint().getId().toString() );
		assertNotNull ( program.getEntryPoint().getBlock() );
		assertNull ( program.getEntryPoint().getBlock().getVariables() );
		assertNull ( program.getEntryPoint().getBlock().getStatements() );
	}

	@Test(expected=org.modelcc.parser.ParserException.class)
	public void testInvalidProgram2()
		throws Exception
	{
		Program program = parse("procedimiento simple;");
		
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
