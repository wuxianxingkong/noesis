package test.noesis;

import static org.junit.Assert.*;
import ikor.model.data.IntegerModel;
import ikor.model.data.RealModel;
import noesis.NoesisTask;
import noesis.ParameterMetadata;
import noesis.analysis.structure.Betweenness;
import noesis.analysis.structure.Decay;
import noesis.analysis.structure.DiffusionCentrality;
import noesis.analysis.structure.KatzCentrality;
import noesis.analysis.structure.NormalizedDecay;

import org.junit.Test;

public class ParameterTest 
{
	public static final double EPSILON = 1e-9;
	
	@Test
	public void testNoParameters ()
	{
		NoesisTask betweenness = new Betweenness(null);

		assertEquals("betweenness", betweenness.getName() );
		assertEquals("Node betweenness", betweenness.getDescription() );
		assertEquals(0, betweenness.getParameters().length );
	}

	@Test
	public void testOneParameter ()
	{
		NoesisTask decay = new Decay(null);

		assertEquals("decay-50", decay.getName() );
		assertEquals("Node decay", decay.getDescription() );
		assertEquals(1, decay.getParameters().length );
		
		ParameterMetadata parameter = decay.getParameters()[0];

		assertEquals("delta", parameter.getLabel());
		assertEquals("delta parameter", parameter.getDescription());
		assertTrue(parameter.getModel() instanceof RealModel);
		assertEquals(0.0, parameter.getMinimumValue(), EPSILON);
		assertEquals(1.0, parameter.getMaximumValue(), EPSILON);
		assertEquals(0.5, parameter.getDefaultValue(), EPSILON);		
	}

	@Test
	public void testInheritedParameter ()
	{
		NoesisTask decay = new NormalizedDecay(null);

		assertEquals("decay-50-norm", decay.getName() );
		assertEquals("Normalized decay", decay.getDescription() );
		assertEquals(1, decay.getParameters().length );
		
		ParameterMetadata parameter = decay.getParameters()[0];

		assertEquals("delta", parameter.getLabel());
		assertEquals("delta parameter", parameter.getDescription());
		assertTrue(parameter.getModel() instanceof RealModel);
		assertEquals(0.0, parameter.getMinimumValue(), EPSILON);
		assertEquals(1.0, parameter.getMaximumValue(), EPSILON);
		assertEquals(0.5, parameter.getDefaultValue(), EPSILON);		
	}
	
	@Test
	public void testTwoParameters ()
	{
		NoesisTask katz = new KatzCentrality(null);

		assertEquals("katz(1.0,1.0)", katz.getName() );
		assertEquals("Katz centrality", katz.getDescription() );
		
		ParameterMetadata parameters[] = katz.getParameters();

		assertEquals(2, parameters.length );

		assertEquals("alpha", parameters[0].getLabel());
		assertEquals("alpha parameter", parameters[0].getDescription());
		assertTrue(parameters[0].getModel() instanceof RealModel);
		assertEquals(-Double.MAX_VALUE, parameters[0].getMinimumValue(), EPSILON);
		assertEquals(Double.MAX_VALUE, parameters[0].getMaximumValue(), EPSILON);
		assertEquals(1.0, parameters[0].getDefaultValue(), EPSILON);		
		
		assertEquals("beta", parameters[1].getLabel());
		assertEquals("beta parameter", parameters[1].getDescription());
		assertTrue(parameters[1].getModel() instanceof RealModel);
		assertEquals(-Double.MAX_VALUE, parameters[1].getMinimumValue(), EPSILON);
		assertEquals(Double.MAX_VALUE, parameters[1].getMaximumValue(), EPSILON);
		assertEquals(1.0, parameters[1].getDefaultValue(), EPSILON);				
	}

	@Test
	public void testTwoParameterTypes ()
	{
		NoesisTask diffusion = new DiffusionCentrality(null);

		assertEquals("diffusion(0.1,3)", diffusion.getName() );
		assertEquals("Diffusion centrality", diffusion.getDescription() );
		
		ParameterMetadata parameters[] = diffusion.getParameters();

		assertEquals(2, parameters.length );

		assertEquals("passing probability", parameters[0].getLabel());
		assertEquals("passing probability parameter", parameters[0].getDescription());
		assertTrue(parameters[0].getModel() instanceof RealModel);
		assertEquals(0.0, parameters[0].getMinimumValue(), EPSILON);
		assertEquals(1.0, parameters[0].getMaximumValue(), EPSILON);
		assertEquals(0.1, parameters[0].getDefaultValue(), EPSILON);		
		
		assertEquals("path length", parameters[1].getLabel());
		assertEquals("path length parameter", parameters[1].getDescription());
		assertTrue(parameters[1].getModel() instanceof IntegerModel);
		assertEquals(1, parameters[1].getMinimumValue(), EPSILON);
		assertEquals(Integer.MAX_VALUE, parameters[1].getMaximumValue(), EPSILON);
		assertEquals(3, parameters[1].getDefaultValue(), EPSILON);				
	}
	
}
