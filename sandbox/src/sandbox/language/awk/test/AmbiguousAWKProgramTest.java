package sandbox.language.awk.test;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import sandbox.language.ModelCCTest;
import sandbox.language.awk.variations.AmbiguousAWKProgram;

// http://www.staff.science.uu.nl/~oostr102/docs/nawk/nawk_7.html#SEC10

public class AmbiguousAWKProgramTest extends ModelCCTest<AmbiguousAWKProgram>
{
	@Test
	public void testVerySimpleAmbiguousProgram()
		throws Exception
	{
		Collection<AmbiguousAWKProgram> programs = parseAll("/foo/ { print $0 }");
		boolean cases[] = new boolean[2];
		
		assertEquals(2, programs.size());
		
		for (AmbiguousAWKProgram program: programs) {
			
			if (program.getRules().length==1) {
				assertEquals( 1, program.getRules().length );
				assertNotNull( program.getRule(0).getPattern() );
				assertNotNull( program.getRule(0).getAction() );
				assertEquals("{ print $0 }", program.getRule(0).getAction().toString());
				assertEquals("/foo/", program.getRule(0).getPattern().toString());
				cases[0] = true;
			} else {
				assertEquals( 2, program.getRules().length );
				assertNotNull( program.getRule(0).getPattern() );
				assertNull   ( program.getRule(0).getAction() );
				assertNull   ( program.getRule(1).getPattern() );
				assertNotNull( program.getRule(1).getAction() );
				assertEquals("/foo/", program.getRule(0).getPattern().toString());								
				assertEquals("{ print $0 }", program.getRule(1).getAction().toString());
				cases[1] = true;
			}
		}
		
		for (int i=0; i<cases.length; i++)
			assertTrue(cases[i]);
	}
	
}
