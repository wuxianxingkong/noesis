package test.noesis.analysis.structure;

import static org.junit.Assert.assertEquals;
import noesis.BasicNetwork;
import noesis.Network;
import noesis.analysis.NodeScore;
import noesis.analysis.structure.DegreeAssortativity;
import noesis.analysis.structure.UnbiasedDegreeAssortativity;

import org.junit.Test;

public class DegreeAssortativityTest 
{
	public final double EPSILON = 1e-9;
	
	
	public Network createTriangleNetwork () 
	{
		Network triangleNetwork = new BasicNetwork(); 
	
		triangleNetwork.setSize(3);
		triangleNetwork.add2(0,1);
		triangleNetwork.add2(0,2);
		triangleNetwork.add2(1,2);
		
		return triangleNetwork;
	}
	
	public Network createStarNetwork ()
	{
		Network starNetwork = new BasicNetwork(); 
		
		starNetwork.setSize(6);
		starNetwork.add2(0,1);
		starNetwork.add2(0,2);
		starNetwork.add2(0,3);
		starNetwork.add2(0,4);
		starNetwork.add2(0,5);
		
		return starNetwork;
	}
	
	@Test
	public void testTriangleAssortativity() 
	{
		DegreeAssortativity assortativityTask = new DegreeAssortativity(createTriangleNetwork());
		NodeScore assortativity = assortativityTask.call();

		assertEquals ( 1.0/3.0, assortativity.get(0), EPSILON);
		assertEquals ( 1.0/3.0, assortativity.get(1), EPSILON);
		assertEquals ( 1.0/3.0, assortativity.get(2), EPSILON);
		assertEquals ( 1.0 , assortativity.sum(), EPSILON);
	}
	
	@Test
	public void testTriangleUnbiasedAssortativity() 
	{
		UnbiasedDegreeAssortativity assortativityTask = new UnbiasedDegreeAssortativity(createTriangleNetwork());
		NodeScore assortativity = assortativityTask.call();

		assertEquals ( 1.0/3.0, assortativity.get(0), EPSILON);
		assertEquals ( 1.0/3.0, assortativity.get(1), EPSILON);
		assertEquals ( 1.0/3.0, assortativity.get(2), EPSILON);
		assertEquals ( 1.0 , assortativity.sum(), EPSILON);
	}
	
	
	@Test
	public void testStarAssortativity() 
	{
		DegreeAssortativity assortativityTask = new DegreeAssortativity(createStarNetwork());
		NodeScore assortativity = assortativityTask.call();
		
		assertEquals ( -0.5, assortativity.get(0), EPSILON);
		assertEquals ( -0.1, assortativity.get(1), EPSILON);
		assertEquals ( -0.1, assortativity.get(2), EPSILON);
		assertEquals ( -0.1, assortativity.get(3), EPSILON);
		assertEquals ( -0.1, assortativity.get(4), EPSILON);
		assertEquals ( -0.1, assortativity.get(5), EPSILON);
		assertEquals ( -1.0, assortativity.sum(), EPSILON);
	}
	
	
	@Test
	public void testStarUnbiasedAssortativity() 
	{
		UnbiasedDegreeAssortativity assortativityTask = new UnbiasedDegreeAssortativity(createStarNetwork());
		NodeScore assortativity = assortativityTask.call();
		
		assertEquals ( -1.0, assortativity.get(0), EPSILON);
		assertEquals ( 0.0, assortativity.get(1), EPSILON);
		assertEquals ( 0.0, assortativity.get(2), EPSILON);
		assertEquals ( 0.0, assortativity.get(3), EPSILON);
		assertEquals ( 0.0, assortativity.get(4), EPSILON);
		assertEquals ( 0.0, assortativity.get(5), EPSILON);
		assertEquals ( -1.0 , assortativity.sum(), EPSILON);
	}
}
