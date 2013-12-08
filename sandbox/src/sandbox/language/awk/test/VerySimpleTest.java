package sandbox.language.awk.test;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import sandbox.language.ModelCCTest;
import sandbox.language.awk.AWKProgram;

// http://www.staff.science.uu.nl/~oostr102/docs/nawk/nawk_7.html#SEC10

public class VerySimpleTest extends ModelCCTest<AWKProgram>
{

	@Test
	public void testUnambiguousProgram()
		throws Exception
	{
		Collection<AWKProgram> programs = parseAll("/foo/ { print $0 }");
	
		assertEquals(1, programs.size());
	}
	
	@Test
	public void testVerySimpleProgram()
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
	public void testOptionalAction()
		throws Exception
	{
		AWKProgram program = parse("/foo/");
		
		assertEquals( 1, program.getRules().length );
		assertNotNull( program.getRule(0).getPattern() );
		assertNull( program.getRule(0).getAction() );
		assertEquals("/foo/", program.getRule(0).getPattern().toString() );
	}
}
