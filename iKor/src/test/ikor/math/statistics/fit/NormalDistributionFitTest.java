package test.ikor.math.statistics.fit;

import static org.junit.Assert.assertEquals;
import ikor.math.MatrixFactory;
import ikor.math.Vector;
import ikor.math.statistics.NormalDistribution;
import ikor.math.statistics.fit.ConfidenceInterval;
import ikor.math.statistics.fit.NormalDistributionFit;

import org.junit.Test;

public class NormalDistributionFitTest 
{
	public static double EPSILON = 1e-4;
	
	@Test
	public void testDataArray ()
	{
		double[] data = {15, 20, 35, 45, 50};
		
		NormalDistributionFit fit = new NormalDistributionFit(data);
		
		NormalDistribution distribution = fit.fit();
		
		assertEquals( 33.0, fit.mleMean(), EPSILON );
		assertEquals( 186.0, fit.mleVariance(), EPSILON );
		assertEquals( 33.0, fit.sampleMean(), EPSILON );
		assertEquals( 232.5, fit.sampleVariance(), EPSILON );

		assertEquals( 33.0, distribution.mean(), EPSILON );
		assertEquals( 232.5, distribution.variance(), EPSILON );
	}
	
	@Test
	public void testDataVector ()
	{
		Vector data = MatrixFactory.createVector(5);
		data.set(0, 1);
		data.set(1, 2);
		data.set(2, 3);
		data.set(3, 4);
		data.set(4, 5);
		
		NormalDistributionFit fit = new NormalDistributionFit(data);
		NormalDistribution distribution = fit.fit();
		
		assertEquals( 3.0, fit.mleMean(), EPSILON );
		assertEquals( 2.0, fit.mleVariance(), EPSILON );
		assertEquals( 3.0, fit.sampleMean(), EPSILON );
		assertEquals( 2.5, fit.sampleVariance(), EPSILON );

		assertEquals( 3.0, distribution.mean(), EPSILON );
		assertEquals( 2.5, distribution.variance(), EPSILON );
	}
	
	// MATLAB TEST
	// >> data = normrnd(10,2,100,1);
	// >> data = round(data*10000)/10000
	// >> [mu,sigma,muci,sigmaci] = normfit(data)

	private double data[] = new double[] {
		13.3928, 11.5426, 10.3346, 10.8725, 10.6344, 10.5224,  9.9257,  8.2026, 11.5533, 10.4869,
		 7.7444,  8.5580,  9.4166,  8.1697, 10.4207, 10.1782,  7.9264, 13.6869,  5.9962,  9.0677, 
		11.6686, 13.3500,  7.8993,  8.7805, 13.5749, 10.6993,  8.0967, 10.5713,  6.8365,  6.8661, 
		11.1724,  6.3261,  9.2380, 12.4008,  8.1545, 14.6264, 12.3244,  6.7510, 11.9703,  7.0402, 
		 7.7346, 13.8410, 12.0259, 14.0617, 12.5067, 10.5446, 13.0791,  6.5974, 11.0335,  9.1296, 
		10.1187, 12.2390, 13.2069,  9.0180,  8.1074,  9.5730, 11.4585,  9.7091,  9.8147, 11.4692,
		 8.6336, 11.8045, 11.3720,  9.5142, 13.5891,  6.3297, 12.9238,  9.9431,  7.4621, 10.6111, 
		 7.6390, 14.7949, 11.2538,  6.2019,  8.4958,  9.9826,  9.0633, 11.0252,  6.1090, 12.0453,
		 6.7868,  7.7989,  8.4843, 15.0938,  9.1272, 12.6907,  7.9750,  9.7961, 13.1475,  8.9430,
		11.5757, 11.6376, 10.9997,  8.0613,  9.3519, 11.2059,  9.2515,  9.1821, 13.8975, 11.4444
	};
	
	@Test
	public void testMatlab ()
	{
		NormalDistributionFit fit = new NormalDistributionFit(data);

		NormalDistribution distribution = fit.fit();
		
		assertEquals( 10.1749, distribution.mean(), EPSILON );
		assertEquals(  2.2564, distribution.deviation(), EPSILON );
		
		ConfidenceInterval ciMean = fit.mean(0.05);
		
		assertEquals( 9.7272, ciMean.min(), EPSILON);
		assertEquals(10.6226, ciMean.max(), EPSILON);
		assertEquals( 0.05,   ciMean.alpha(), EPSILON);
		assertEquals( 0.95,   ciMean.confidenceLevel(), EPSILON);

		ConfidenceInterval ciDeviation = fit.deviation(0.05);
		
		assertEquals( 1.9811, ciDeviation.min(), EPSILON);
		assertEquals( 2.6212, ciDeviation.max(), EPSILON);
		assertEquals( 0.05,   ciMean.alpha(), EPSILON);
		assertEquals( 0.95,   ciMean.confidenceLevel(), EPSILON);		
	}
}
