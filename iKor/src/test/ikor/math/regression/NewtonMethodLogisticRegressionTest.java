package test.ikor.math.regression;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ikor.math.Matrix;
import ikor.math.regression.LogisticRegressionModel;
import ikor.math.regression.NewtonMethodLogisticRegression;


public class NewtonMethodLogisticRegressionTest extends LogisticRegressionTest
{
	// Epsilon
	
	public double EPSILON ()
	{
		return 0.0001;
	}
	
	// Logistic regression model
	
	NewtonMethodLogisticRegression reg;
	
	@Override
	public LogisticRegressionModel createLogisticRegressionModel ( double x[][], double y[])
	{
		reg = new NewtonMethodLogisticRegression (x,y);

		reg.setLearningRate(1.0);
		reg.setMaxIterations(100);
		reg.setMinCostImprovement(1e-10);  
			
		return (LogisticRegressionModel) reg.getResult();		
	}	
	
	
	
	// MATLAB example
	// --------------
	// x = [2100 2300 2500 2700 2900 3100 3300 3500 3700 3900 4100 4300]';
	// n = [48 42 31 34 31 21 23 23 21 16 17 21]';
	// y = [1 2 0 3 8 8 14 17 19 15 17 21]';
	// [b,dev,stat] = glmfit(x, y./n, 'binomial', 'link', 'logit');
	// yfit = glmval(b, x, 'logit', 'size', n);
	// plot(x, y./n, 'o', x, yfit./n, '-')
	//
	// coefficient estimates, their standard errors, t-statistics, and p-values for the model:
	// [b stat.se stat.t stat.p]
	
	private double matlabX[][] = { { 2100, 2300, 2500, 2700, 2900, 3100, 
			                         3300, 3500, 3700, 3900, 4100, 4300 } };
	private double matlabY[]   = {  1, 2, 0, 3, 8, 8,14,17,19,15,17,21 };
	private double matlabN[]   = { 48,42,31,34,31,21,23,23,21,16,17,21 };
	
	@Test
	public void testMatlabLogisticRegression ()
	{
		double y[] = new double[matlabY.length];
		
		for (int i=0; i<y.length; i++)
			y[i] = matlabY[i]/matlabN[i];
		
		LogisticRegressionModel model = createLogisticRegressionModel(matlabX,y);

		assertEquals ( 2, model.parameters());
		assertEquals ( -13.7780, model.getParameter(0), EPSILON());
		assertEquals (   0.0043, model.getParameter(1), EPSILON());
		
		// Degrees of freedom
		assertEquals ( 10, model.dof());
		
		// Estimated covariance matrix for B
		Matrix covariance = reg.covariance();
		assertEquals( 58.1505, covariance.get(0,0), EPSILON() );
		assertEquals( -0.0179, covariance.get(0,1), EPSILON() );
		assertEquals( -0.0179, covariance.get(1,0), EPSILON() );
		assertEquals(  0.0000, covariance.get(1,1), EPSILON() );
				
		// Standard errors (square root of the diagonal of the covariance matrix)
		assertEquals ( 7.6256, model.standardError(0), EPSILON());
		assertEquals ( 0.0024, model.standardError(1), EPSILON());
		
		// t-statistics ( beta / stderr )
		assertEquals ( -1.8068, model.tStatistic(0), EPSILON());
		assertEquals (  1.8204, model.tStatistic(1), EPSILON());
		
		// p-values
		assertEquals ( 0.0708, model.pValue(0), EPSILON());
		assertEquals ( 0.0687, model.pValue(1), EPSILON());
		
		// Deviance of fit = -2*LogLikelihood
		// LogLikelihood = sum(log(binopdf(y,n,yfit./n))) - sum(log(binopdf(y,n,y./n)))
		// TODO 0.2250

		// Dispersion parameter (sfit)
		// TODO 0.1288
		
		// Theoretical or estimated dispersion parameter
		// TODO 0
		
		// Correlation matrix for B
		// TODO [ 1.0000 -0.9925; -0.9925 1.0000 ]

		// Residuals...
		// TODO
	}
}
