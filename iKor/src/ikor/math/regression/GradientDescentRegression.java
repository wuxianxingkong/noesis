package ikor.math.regression;

// Title:       Gradient descent regression
// Version:     2.0
// Copyright:   2012-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.Vector;

/**
 * Regression using the gradient descent method for parameter estimation
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public abstract class GradientDescentRegression extends Regression 
{
	public static final double DEFAULT_LEARNING_RATE = 0.05;
	public static final int DEFAULT_MAX_ITERATIONS = 1000;
	public static final double DEFAULT_MIN_COST_IMPROVEMENT = 1e-9;
	
	private double   alpha = DEFAULT_LEARNING_RATE;
	private int      maxIterations = DEFAULT_MAX_ITERATIONS;
	private double   minCostImprovement = DEFAULT_MIN_COST_IMPROVEMENT;
	
	private RegressionModel model;
	
	private double[] J;     // Cost vector
	
	
	// Constructors
	
	public GradientDescentRegression (Vector[] x, Vector y)
	{
		super(x,y);
	}

	public GradientDescentRegression (double[][] x, double[] y)
	{
		super(x,y);
	}
	
	// Regression model
	
	public abstract RegressionModel createModel ();

	public RegressionModel getModel ()
	{
		return model;
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
	
	public void setMaxIterations (int iterations)
	{
		this.maxIterations = iterations;
	}

	public double getMaxIterations ()
	{
		return maxIterations;
	}

	public void setMinCostImprovement (double error)
	{
		this.minCostImprovement = error;
	}

	public double getMinCostImprovement ()
	{
		return minCostImprovement;
	}
	
	public double[] getJ ()
	{
		return J;
	}
	
	// Check for convergence
	
	public boolean hasConverged (int iteration)
	{
		return (iteration>1) && (Math.abs(J[iteration-1]-J[iteration-2])<minCostImprovement);
	}
	
	// Cost function
	
	public abstract double cost();
	
	public abstract double[] gradient();
	
	// Gradient descent iteration
	
	public void updateParameters()
	{
		RegressionModel model = getModel();
		double[] t = new double[model.parameters()];
		double[] gradient = gradient();
		
		for (int j=0; j<t.length; j++) {
			t[j] = model.getParameter(j) - getLearningRate() * gradient[j];
		}
		
		model.setParameters(t);
	}	

	
	// Task interface 
	
	@Override
	public RegressionModel call() 
	{
		model = createModel();
		
		J = new double[maxIterations];
		
		for (int i=0; (i<maxIterations) && !hasConverged(i); i++) {
			updateParameters();
			J[i] = cost();
		}
		
		fit(model);
				
		return model;
	}	

}
