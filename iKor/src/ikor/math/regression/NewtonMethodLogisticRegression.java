package ikor.math.regression;

// Title:       Logistic regression using Newton's Method
// Version:     2.0
// Copyright:   2012-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.Matrix;
import ikor.math.MatrixFactory;
import ikor.math.Vector;

/**
 * Logistic regression using Newton's method.
 * 
 * See http://en.wikipedia.org/wiki/Newton's_method_in_optimization
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class NewtonMethodLogisticRegression extends GradientDescentLogisticRegression
{

	public NewtonMethodLogisticRegression (double[][] x, double[] y) 
	{
		super(x, y);
	}
	
	// Newton's method iteration: x(n+1) = x(n) - gamma * inv(H(f(x))) * grad(f(x))
	
	public void updateParameters()
	{
		RegressionModel model = getModel();
		double[] t = new double[model.parameters()];
		
		Vector grad = MatrixFactory.createVector(gradient()).transpose();  // px1 vector
		Matrix hessian = MatrixFactory.create(hessian());                  // pxp matrix
		Matrix update = hessian.inverse().multiply(grad);                  // px1 vector
		
		for (int j=0; j<t.length; j++) {
			t[j] = model.getParameter(j) - getLearningRate() * update.get(j,0);
		}
		
		model.setParameters(t);
	}		

}
