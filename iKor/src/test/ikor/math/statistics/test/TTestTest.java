package test.ikor.math.statistics.test;

import static org.junit.Assert.*;
import ikor.math.MatrixFactory;
import ikor.math.Vector;
import ikor.math.statistics.test.OneTailedTTest;
import ikor.math.statistics.test.TTest;

import org.junit.Test;

public class TTestTest 
{
	// Test for a mean equal to zero
	// i.e. test the null hypothesis that the sample data comes from a population with mean equal to zero.

	double matlabData[] = new double[]{ 
			 0.1503,-0.6113,-2.1800, 0.2538,-2.0649, 0.9817,-1.1336,-2.5854, 2.7277, 0.1204,
			-1.9299, 1.3826, 0.8026,-1.0192, 0.7411,-0.5693, 0.7926,-0.9956,-2.8566,-0.7199,
			 1.4767, 1.4242,-2.0904,-2.9415,-0.0066,-0.5264, 0.6979,-2.0252,-0.4553, 1.1312,
			-0.5385,-0.7690, 0.9285,-1.2165, 0.0233,-2.3043,-1.8647,-0.6956,-0.7350,-2.6563,
			 1.5476,-4.0893,-0.5430,-2.1048, 2.9253, 0.1552, 2.7744,-0.1134, 2.0197,-2.0048,
			-1.1844,-0.1389,-1.3115, 0.1205,-0.5916,-0.2751,-1.1820,-2.1487, 1.9439,-0.4516,
			 1.0756,-4.4384, 0.4817,-0.9116,-2.4673,-0.4138, 0.4073,-1.4923, 1.6206, 1.3221,
			 1.0257, 1.3118,-0.0535, 1.6185,-2.7414, 1.0202,-3.1805,-0.2178,-0.6452, 0.0026,
			-3.3920,-0.3629,-2.0689, 2.5589, 0.1086, 1.1313, 0.5060,-2.0160,-1.6420,-2.1869,
			-1.7440, 0.1725,-2.0778, 1.6968, 0.4082, 0.8788,-3.4666, 0.9269, 1.6294, 0.3508
	};
	
	@Test
	public void testMATLAB0 ()
	{
		Vector data = MatrixFactory.createVector(matlabData);
		
		assertEquals(100, data.size());
		assertEquals(2.5685, data.sampleVariance(), 0.0001);
		assertEquals(1.6027, data.sampleDeviation(), 0.0001);
		
		TTest t = new TTest(data);
		
		assertEquals(99, t.df() );
		assertEquals(1.6027, t.sd(), 0.0001 );
		assertEquals(-2.6065, t.tstat(), 0.0001);
		assertEquals(0.0106, t.pvalue(), 0.0001);
		
		assertEquals(-0.7357, t.minConfidenceInterval(0.05), 0.0001);
		assertEquals(-0.0997, t.maxConfidenceInterval(0.05), 0.0001);
	}
	
	// Test for an hypothesized mean
	// i.e. test the null hypothesis that sample data comes from a distribution with mean m = 75.
	// (the t-test does not reject the null hypothesis at the 5% significance level)
	
	double matlabDataMean[] = new double[]{
			65,61,81,88,69,89,55,84,86,84,71,81,84,81,78,67,96,66,73,75,
			59,71,69,63,79,76,63,85,87,88,80,71,65,84,71,75,81,79,64,65,
			84,77,70,75,84,75,73,92,90,79,80,71,73,71,58,79,73,64,77,82,
			81,59,54,82,57,79,79,73,74,82,63,64,73,69,87,68,81,73,83,73,
			80,73,73,71,66,78,64,74,68,67,75,75,80,85,74,76,80,77,93,70,
			86,80,81,83,68,60,85,64,74,82,81,77,66,85,75,81,69,60,83,72
	};
	
	@Test
	public void testMATLABmean ()
	{
		Vector data = MatrixFactory.createVector(matlabDataMean);
		
		assertEquals(120, data.size());
		assertEquals(75.0083, data.average(), 0.0001);
		
		TTest t = new TTest(data,75);
		
		assertEquals(119, t.df() );
		assertEquals(8.7202, t.sd(), 0.0001 );
		assertEquals(0.0105, t.tstat(), 0.0001);
		assertEquals(0.9917, t.pvalue(), 0.0001);
		
		assertEquals(73.4321, t.minConfidenceInterval(0.05), 0.0001);
		assertEquals(76.5846, t.maxConfidenceInterval(0.05), 0.0001);
	}

	// One-sided t-test:
	// [h,p,ci,stats] = ttest(x,65,0.05,'right')
	// i.e. test the null hypothesis that the data comes from a population with mean equal to 65, 
	// against the alternative that the mean is greater than 65.
	// (the t-test rejects the null hypothesis at the 5% significance level, in favor of the alternate
	// hypothesis that the data comes from a population with a mean greater than 65).
	
	@Test
	public void testMATLABright ()
	{
		Vector data = MatrixFactory.createVector(matlabDataMean);
		
		assertEquals(120, data.size());
		assertEquals(75.0083, data.average(), 0.0001);
		
		TTest t = new OneTailedTTest(data,65,OneTailedTTest.Tail.RIGHT);
		
		assertEquals(119, t.df() );
		assertEquals(8.7202, t.sd(), 0.0001 );
		assertEquals(12.5726, t.tstat(), 0.0001);
		assertEquals(6.9555e-24, t.pvalue(), 0.0001e-24);
		
		assertEquals(73.6887, t.minConfidenceInterval(0.05), 0.0001);
		assertEquals(Double.POSITIVE_INFINITY, t.maxConfidenceInterval(0.05), 0.0);		
	}
	
	// One-sided t-test:
	// [h,p,ci,stats] = ttest(x,85,0.05,'left')
	// i.e. test the null hypothesis that the data comes from a population with mean equal to 85, 
	// against the alternative that the mean is lower than 85.
	// (the t-test rejects the null hypothesis at the 5% significance level, in favor of the alternate
	// hypothesis that the data comes from a population with a mean lower than 85).
	
	@Test
	public void testMATLABleft ()
	{
		Vector data = MatrixFactory.createVector(matlabDataMean);
		
		assertEquals(120, data.size());
		assertEquals(75.0083, data.average(), 0.0001);
		
		TTest t = new OneTailedTTest(data,85,OneTailedTTest.Tail.LEFT);
		
		assertEquals(119, t.df() );
		assertEquals(8.7202, t.sd(), 0.0001 );
		assertEquals(-12.5517, t.tstat(), 0.0001);
		assertEquals(7.7936e-24, t.pvalue(), 0.0001e-24);
		
		assertEquals(Double.NEGATIVE_INFINITY, t.minConfidenceInterval(0.05), 0.0001);
		assertEquals(76.3280, t.maxConfidenceInterval(0.05), 0.0001);
	}
	
}
