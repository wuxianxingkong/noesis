package test.ikor.math.regression;

import ikor.math.regression.LinearRegressionModel;
import ikor.math.regression.OLSLinearRegression;

public class OLSLinearRegressionTest extends LinearRegressionTest 
{
	private static final double NORMAL_EQUATION_EPSILON  = 1e-14;

	@Override
	public LinearRegressionModel createLinearRegressionModel (double[][] x, double[] y) 
	{
		OLSLinearRegression reg = new OLSLinearRegression (x,y);
		
		return reg.call();
		
	}

	@Override
	public double EPSILON() 
	{
		return NORMAL_EQUATION_EPSILON;
	}

}
