package ikor.math.regression;

// Title:       Gradien descent linear regression
// Version:     2.0
// Copyright:   2012-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.Vector;

/**
 * Linear regression using the gradient descent method for parameter estimation
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class GradientDescentLinearRegression extends LinearRegression 
{
	public static final double DEFAULT_LEARNING_RATE = 0.05;
	public static final int DEFAULT_MAX_ITERATIONS = 1000;
	
	private double   alpha = DEFAULT_LEARNING_RATE;
	private int      iterations = DEFAULT_MAX_ITERATIONS;
	
	private LinearRegressionModel model;
	
	private double[] J;     // Cost vector
	
	
	// Constructors
	
	public GradientDescentLinearRegression (Vector[] x, Vector y)
	{
		super(x,y);
	}

	public GradientDescentLinearRegression (double[][] x, double[] y)
	{
		super(x,y);
	}
	
	// Parameters
	
	public void setLearningRate (double alpha)
	{
		this.alpha = alpha;
	}

	public double getLearningRate ()
	{
		return alpha;
	}
	
	public void setIterations (int iterations)
	{
		this.iterations = iterations;
	}

	public double getIterations ()
	{
		return iterations;
	}

	public double[] getJ ()
	{
		return J;
	}
	
	private double getH (int i)
	{
		double h = 0;
		
		for (int j=0; j<model.parameters(); j++) {
			h += getX(j,i)*model.getParameter(j);
		}
		
		return h;
	}
	
	public double getCost()
	{
		return getSSE()/ (2*instances());
	}

	public double getSSE()
	{
		int    m = y.size();
		double sse = 0;
		double d;
		
		for (int i=0; i<m; i++) {
			d = getH(i) - getY(i);
			sse += d*d; 
		}
		
		return sse;
	}
	
	private void updateParameters()
	{
		double[] t = new double[model.parameters()];
		int      m = y.size();
		double   s;
		int      i,j;
		
		
		for (j=0; j<t.length; j++) {
			
			s = 0;
			
			for (i=0; i<m; i++) {
				s += ( getH(i) - getY(i) ) * getX(j,i);
			}
			
			t[j] = model.getParameter(j) - (alpha / m) * s;
		}
		
		model.setParameters(t);
	}

	
	// Task interface 
	
	@Override
	public LinearRegressionModel call() 
	{
		model = new LinearRegressionModel(variables());
		
		J = new double[iterations];
		
		for (int i=0; i<iterations; i++) {
			updateParameters();
			J[i] = getCost();
		}
		
		model.setSSE( getSSE() ); 
				
		return model;
	}	

}
