package test.ikor.math.statistics.test;

import ikor.math.MatrixFactory;
import ikor.math.Vector;
import ikor.math.statistics.test.FriedmanTest;

import org.junit.Test;
import static org.junit.Assert.*;

public class FriedmanTestTest 
{

	// [p, table, stats]= friedman(popcorn)
	//
	// Source    SS   df   MS   Chi-sq   Prob>Chi-sq
	// ---------------------------------------------
	// Columns   12    2   6      12       0.0025   
	// Error      0   10   0                        
	// Total     12   17                           
	
	@Test
	public void testMATLABexample ()
	{
		double popcorn[][] = new double[][] { 
				{5.5, 5.5, 6.0, 6.5, 7.0, 7.0},
				{4.5, 4.5, 4.0, 5.0, 5.5, 5.0},
				{3.5, 4.0, 3.0, 4.0, 5.0, 4.5}};

		Vector columns[] = new Vector[] {
				MatrixFactory.createVector(popcorn[0]),
				MatrixFactory.createVector(popcorn[1]),
				MatrixFactory.createVector(popcorn[2])};
		
		FriedmanTest test = new FriedmanTest(columns);
		
		assertEquals(6, test.n());
		assertEquals(3, test.nc());
		assertEquals(2, test.df());
		
		assertEquals(3, test.meanrank(0), 0.0001);
		assertEquals(2, test.meanrank(1), 0.0001);
		assertEquals(1, test.meanrank(2), 0.0001);
		assertEquals(2, test.meanrank(), 0.0001);
		
		assertEquals(12, test.sst(), 0.0001);
		assertEquals( 6, test.mst(), 0.0001);
		assertEquals( 1, test.sse(), 0.0001);
		assertEquals(10, test.dfe());
		assertEquals( 0.1, test.mse(), 0.0001);
		
		assertEquals(12, test.q(), 0.0001);
		
		assertEquals(0.0025, test.pvalue(), 0.0001);
	}
	
	// Example from Victor Martinez's study on link prediction methods
	
	@Test
	public void testLinkPrediction ()
	{
		double cn[] = new double[] {
				0.0437, 0.0756, 0.0898, 0.1141, 0.1765, 0.1100, 0.3065, 0.1443, 0.1352, 0.1371,
				0.1893, 0.3910, 0.2471, 0.2412, 0.2057, 0.2566, 0.3942, 0.4285, 0.3894, 0.4945};
		double aa[] = new double[] {
				0.0367, 0.0849, 0.0972, 0.1136, 0.1880, 0.1335, 0.3642, 0.1560, 0.1655, 0.1562,
				0.2118, 0.4098, 0.2743, 0.3549, 0.3986, 0.3215, 0.4180, 0.4516, 0.5394, 0.6207};
		double ra[] = new double[] {
				0.0346, 0.0674, 0.0822, 0.0898, 0.1735, 0.1140, 0.3579, 0.1546, 0.1604, 0.1383,
				0.2083, 0.4116, 0.2698, 0.3605, 0.4299, 0.3961, 0.5278, 0.5169, 0.5605, 0.6444};
		double adp[] = new double[] {
				0.0481, 0.0841, 0.0992, 0.1160, 0.1932, 0.1363, 0.3738, 0.1569, 0.1708, 0.1581, 
				0.2188, 0.4098, 0.2675, 0.3628, 0.4372, 0.3970, 0.5340, 0.5080, 0.5523, 0.6466}; 
		double best[] = new double[] {
				0.0464, 0.0869, 0.1044, 0.1175, 0.2005, 0.1378, 0.3843, 0.1546, 0.1727, 0.1538, 
				0.2184, 0.4174, 0.2835, 0.3612, 0.4399, 0.3935, 0.5336, 0.5061, 0.5643, 0.6681};
		
		Vector[] columns = new Vector[] {
				MatrixFactory.createVector(cn),
				MatrixFactory.createVector(aa),
				MatrixFactory.createVector(ra),
				MatrixFactory.createVector(adp),
				MatrixFactory.createVector(best)};
		
		FriedmanTest test = new FriedmanTest(columns);
		
		assertEquals(20, test.n());
		assertEquals( 5, test.nc());
		assertEquals( 4, test.df());
		
		assertEquals( 49.206, test.q(), 0.001);
		assertEquals( 5.289e-10, test.pvalue(), 0.001e-10);
		
		assertEquals( 1.350, test.meanrank(0), 0.001); // CN
		assertEquals( 2.725, test.meanrank(1), 0.001); // AA
		assertEquals( 2.475, test.meanrank(2), 0.001); // RA
		assertEquals( 4.075, test.meanrank(3), 0.001); // ADP
		assertEquals( 4.375, test.meanrank(4), 0.001); // best
	}
	
}
