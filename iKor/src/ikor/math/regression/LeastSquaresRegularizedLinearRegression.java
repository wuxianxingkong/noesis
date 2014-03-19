package ikor.math.regression;

// Title:       Regularized linear regression
// Version:     2.0
// Copyright:   2012-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.Matrix;
import ikor.math.MatrixFactory;
import ikor.math.Vector;

/**
 * Regularized linear regression.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class LeastSquaresRegularizedLinearRegression extends Regression
{
	private double lambda;
	
	// Constructors
	
	public LeastSquaresRegularizedLinearRegression (Vector[] x, Vector y)
	{
		super(x,y);
	}

	public LeastSquaresRegularizedLinearRegression (double[][] x, double[] y)
	{
		super(x,y);
	}
	
	// Regularization factor
	
	public double getRegularizationFactor ()
	{
		return lambda;
	}
	
	public void setRegularizationFactor (double lambda)
	{
		this.lambda = lambda;
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
		Matrix XtXri;
		Matrix result;
		Matrix regularization = MatrixFactory.create(p,p);
		
		// Design matrix
		
		for (int i=0; i<m; i++) 
			for (int j=0; j<p; j++)
				X.set(i, j, getX(j,i));
		
		for (int i=1; i<p;i++)
			regularization.set(i,i, lambda);
		
		Xt = X.transpose(); // p x m
		Yt = Y.transpose(); // m x 1
		
		// Normal equation
		
		XtXri = Xt.multiply(X).add(regularization).inverse();
		result = XtXri.multiply(Xt).multiply(Yt);
		
		// Linear regression model
		
		LinearRegressionModel model = new LinearRegressionModel(variables());
		
		for (int i=0; i<p; i++)
			model.setParameter(i, result.get(i,0));
		
		fit(model);
		
		return model;
	}
}
