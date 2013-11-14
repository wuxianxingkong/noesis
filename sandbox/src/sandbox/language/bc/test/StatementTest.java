package sandbox.language.bc.test;

import static org.junit.Assert.*;

import org.junit.Test;

import org.modelcc.io.ModelReader;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserFactory;

import sandbox.language.bc.*;


public class StatementTest 
{
	@Test
	public void testOutputStatement()
		throws Exception
	{
		Statement statement = parse("salida 'Hola'");
		
		assertTrue (statement instanceof OutputStatement);
		assertEquals ( "'Hola'", ((OutputStatement)statement).getExpression().toString() );
	}
	
	@Test
	public void testInputStatement()
		throws Exception
	{
		Statement statement = parse("entrada x");
		
		assertTrue (statement instanceof InputStatement);
		assertEquals ( "x", ((InputStatement)statement).getVariable().toString() );
	}	

	@Test
	public void testAssignmentStatement()
		throws Exception
	{
		Statement statement = parse("x := 'Hola'");
		
		assertTrue (statement instanceof AssignmentStatement);
		assertEquals ( "x", ((AssignmentStatement)statement).getLValue().toString() );
		assertEquals ( "'Hola'", ((AssignmentStatement)statement).getRValue().toString() );
	}	

	@Test
	public void testIfStatement()
		throws Exception
	{
		Statement statement = parse("si x entonces salida 'OK'");
		
		assertTrue (statement instanceof IfStatement);
		assertEquals ( "x", ((IfStatement)statement).getCondition().toString() );
		assertEquals ( "salida 'OK'", ((IfStatement)statement).getThenStatement().toString() );
		assertNull ( ((IfStatement)statement).getElseStatement() );
	}	
	
	@Test
	public void testElseStatement()
		throws Exception
	{
		Statement statement = parse("si x entonces salida 'SI' si no salida 'NO'");
		
		assertTrue (statement instanceof IfStatement);
		assertEquals ( "x", ((IfStatement)statement).getCondition().toString() );
		assertEquals ( "salida 'SI'", ((IfStatement)statement).getThenStatement().toString() );
		assertEquals ( "salida 'NO'", ((IfStatement)statement).getElseStatement().toString() );
	}	
	
	@Test
	public void testWhileStatement()
		throws Exception
	{
		Statement statement = parse("mientras x hacer salida y");
		
		assertTrue (statement instanceof WhileStatement);
		assertEquals ( "x", ((WhileStatement)statement).getCondition().toString() );
		assertEquals ( "salida y", ((WhileStatement)statement).getStatement().toString() );
	}	

	@Test
	public void testRepeatStatement()
		throws Exception
	{
		Statement statement = parse("repetir salida y hasta x");
		
		assertTrue (statement instanceof RepeatStatement);
		assertEquals ( "x", ((RepeatStatement)statement).getCondition().toString() );
		assertEquals ( "salida y", ((RepeatStatement)statement).getStatement().toString() );
	}	
	
	@Test
	public void testProcedureCallStatement()
		throws Exception
	{
		Statement statement = parse("p()");
		
		assertTrue (statement instanceof ProcedureCallStatement);
		assertEquals ( "p", ((ProcedureCallStatement)statement).getProcedureId().toString() );
		assertEquals ( 0, ((ProcedureCallStatement)statement).getArguments().length );
	}	
		
	@Test
	public void testProcedureCallStatementWithParameter()
		throws Exception
	{
		Statement statement = parse("p(x)");
		
		assertTrue (statement instanceof ProcedureCallStatement);
		assertEquals ( "p", ((ProcedureCallStatement)statement).getProcedureId().toString() );
		assertEquals ( 1, ((ProcedureCallStatement)statement).getArguments().length );
		assertEquals ( "x", ((ProcedureCallStatement)statement).getArguments()[0].toString() );
	}	

	@Test
	public void testProcedureCallStatementWithParameters()
		throws Exception
	{
		Statement statement = parse("p(x,y)");
		
		assertTrue (statement instanceof ProcedureCallStatement);
		assertEquals ( "p", ((ProcedureCallStatement)statement).getProcedureId().toString() );
		assertEquals ( 2, ((ProcedureCallStatement)statement).getArguments().length );
		assertEquals ( "x", ((ProcedureCallStatement)statement).getArguments()[0].toString() );
		assertEquals ( "y", ((ProcedureCallStatement)statement).getArguments()[1].toString() );
	}	
		

	
	public Statement parse (String bc)
		throws Exception
	{
		Parser<Statement> parser;
		Statement statement;
		
		try {
			parser = createParser();
		} catch (Exception error) {
			System.err.println("Parser creation error: "+error);
			throw error;
		}

		try {
			statement = parser.parse(bc);
		} catch (Exception error) {
			System.err.println("Parser error: "+error);
			throw error;
		}
		
		return statement;
	}

	public Parser<Statement> createParser ()
		throws Exception
	{
		ModelReader modelReader = new JavaModelReader(Statement.class);
		Model model = modelReader.read();
		Parser<Statement> statement = ParserFactory.create(model,ParserFactory.WHITESPACE);
		
		return statement;
	}
}
