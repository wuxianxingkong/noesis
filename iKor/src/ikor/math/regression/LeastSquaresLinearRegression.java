package ikor.math.regression;

// Title:       Ordinary least squares linear regression
// Version:     2.0
// Copyright:   1998-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.Matrix;
import ikor.math.MatrixFactory;
import ikor.math.Vector;

/**
 * Ordinary least squares linear regression: Parameter estimation using the normal equation, i.e. (Xt*X)^(-1)*Xt*Yt.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class LeastSquaresLinearRegression extends Regression
{
	// Constructors
	
	public LeastSquaresLinearRegression (Vector[] x, Vector y)
	{
		super(x,y);
	}

	public LeastSquaresLinearRegression (double[][] x, double[] y)
	{
		super(x,y);
	}
	
	// Task interface

	@Override
	public LinearRegressionModel call() 
	{
		int    p = variables() + 1;
		int    m = instances();
		Matrix X = MatrixFactory.create(m,p);	// m x p
		Matrix Y = getY();				 		// 1 x m
		Matrix Xt;
		Matrix Yt;
		Matrix XtXi;
		Matrix result;
		
		// Design matrix
		
		for (int i=0; i<m; i++) 
			for (int j=0; j<p; j++)
				X.set(i, j, getX(j,i));
		
		Xt = X.transpose(); // p x m
		Yt = Y.transpose(); // m x 1
		
		// Normal equation
		
		XtXi = Xt.multiply(X).inverse();
		result = XtXi.multiply(Xt).multiply(Yt);
		
		// Linear regression model
		
		LinearRegressionModel model = new LinearRegressionModel(variables());
		
		for (int i=0; i<p; i++)
			model.setParameter(i, result.get(i,0));
		
		fit(model);
		
		// Standard errors
		
		double sigma2 = getSSE(model)/(m-p);
		Vector parameterErrors = XtXi.getDiagonal();
		
		for (int i=0; i<p; i++)
			parameterErrors.set(i, Math.sqrt(sigma2*parameterErrors.get(i)) );

		model.setStandardErrors(parameterErrors);
		
		return model;
	}
}
