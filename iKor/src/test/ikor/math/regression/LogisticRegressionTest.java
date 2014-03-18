package test.ikor.math.regression;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ikor.math.regression.LogisticRegressionModel;

public abstract class LogisticRegressionTest 
{

	// DATA
	
	public static final int N = 10;

	public double[][] createIndependentVariable ()
	{
		double x[][] = new double[1][N];
		double v = 0.0;
		double step = 1.0/N;
		
		for (int i=0; i<N; i++) {
			x[0][i] = v;
			v += step;
		}
			
		return x;
	}
	
	public double[][] createIndependentVariables ()
	{
		double x[][] = new double[2][N*N];
		double v,w;
		double step = 1.0/N;
		
		v = 0.0;
		
		for (int i=0; i<N; i++) {
			w = 0.0;
			for (int j=0; j<N; j++) {
				x[0][N*i+j] = v;
				x[1][N*i+j] = w;
				w += step;
			}
			v += step;
		}
			
		return x;
	}	
	
	public double[] createDependentVariable (double beta[], double x[][])
	{
		LogisticRegressionModel model = new LogisticRegressionModel(beta);
		int    n = x[0].length;
		int    v = x.length;
		double y[] = new double[n];
		double d[] = new double[v];
		
		for (int i=0; i<x[0].length; i++) {
			
			for (int j=0; j<v; j++)
				d[j] = x[j][i];
			
			y[i] = model.predict(d);
		}
	
		return y;
	}
	
	
	// Generic interface for logistic regression models
	
	public abstract LogisticRegressionModel createLogisticRegressionModel ( double x[][], double y[]);
		
	public abstract double EPSILON ();
	
	// TEST CASES
	
	@Before
	public void setUp() throws Exception 
	{	

	}
	
	
	@Test
	public void test00LogisticRegression ()
	{
		double beta[] = new double[]{0,0};
		double x[][] = createIndependentVariable();
		double y[] = createDependentVariable(beta, x);
		LogisticRegressionModel model = createLogisticRegressionModel(x,y);
		
		assertEquals ( 2, model.parameters());
		assertEquals ( 0, model.getParameter(0), EPSILON());
		assertEquals ( 0, model.getParameter(1), EPSILON());
		
		assertEquals ( 0.500, model.predict(new double[]{0.0}), EPSILON());
		assertEquals ( 0.500, model.predict(new double[]{0.5}), EPSILON());
		assertEquals ( 0.500, model.predict(new double[]{1.0}), EPSILON());
	}	

	@Test
	public void test01LogisticRegression ()
	{
		double beta[] = new double[]{0,1};
		double x[][] = createIndependentVariable();
		double y[] = createDependentVariable(beta,x);
		LogisticRegressionModel model = createLogisticRegressionModel(x,y);
		
		assertEquals ( 2, model.parameters());
		assertEquals ( 0, model.getParameter(0), EPSILON());
		assertEquals ( 1, model.getParameter(1), EPSILON());
		
		assertEquals ( 0.5000, model.predict(new double[]{0.0}), EPSILON());
		assertEquals ( 0.6225, model.predict(new double[]{0.5}), EPSILON());
		assertEquals ( 0.7311, model.predict(new double[]{1.0}), EPSILON());
	}	

	@Test
	public void test10LogisticRegression ()
	{
		double beta[] = new double[]{1,0};
		double x[][] = createIndependentVariable();
		double y[] = createDependentVariable(beta,x);
		LogisticRegressionModel model = createLogisticRegressionModel(x,y);
		
		assertEquals ( 2, model.parameters());
		assertEquals ( 1, model.getParameter(0), EPSILON());
		assertEquals ( 0, model.getParameter(1), EPSILON());
		
		assertEquals ( 0.7311, model.predict(new double[]{0}), EPSILON());
		assertEquals ( 0.7311, model.predict(new double[]{0.5}), EPSILON());
		assertEquals ( 0.7311, model.predict(new double[]{1}), EPSILON());
	}	

	@Test
	public void test11LogisticRegression ()
	{
		double beta[] = new double[]{1,1};
		double x[][] = createIndependentVariable();
		double y[] = createDependentVariable(beta,x);
		LogisticRegressionModel model = createLogisticRegressionModel(x,y);
		
		assertEquals ( 2, model.parameters());
		assertEquals ( 1, model.getParameter(0), EPSILON());
		assertEquals ( 1, model.getParameter(1), EPSILON());
		
		assertEquals ( 0.7311, model.predict(new double[]{0.0}), EPSILON());
		assertEquals ( 0.8176, model.predict(new double[]{0.5}), EPSILON());
		assertEquals ( 0.8808, model.predict(new double[]{1.0}), EPSILON());
	}	
	
	@Test
	public void test55LogisticRegression ()
	{
		double beta[] = new double[]{0.5,0.5};
		double x[][] = createIndependentVariable();
		double y[] = createDependentVariable(beta,x);
		LogisticRegressionModel model = createLogisticRegressionModel(x,y);
		
		assertEquals ( 2, model.parameters());
		assertEquals ( 0.5, model.getParameter(0), EPSILON());
		assertEquals ( 0.5, model.getParameter(1), EPSILON());
		
		assertEquals ( 0.6225, model.predict(new double[]{0.0}), EPSILON());
		assertEquals ( 0.6792, model.predict(new double[]{0.5}), EPSILON());
		assertEquals ( 0.7311, model.predict(new double[]{1.0}), EPSILON());
	}
	
	@Test
	public void test1m1LogisticRegression ()
	{
		double beta[] = new double[]{1,-1};
		double x[][] = createIndependentVariable();
		double y[] = createDependentVariable(beta,x);
		LogisticRegressionModel model = createLogisticRegressionModel(x,y);
		
		assertEquals ( 2, model.parameters());
		assertEquals ( 1.0, model.getParameter(0), EPSILON());
		assertEquals (-1.0, model.getParameter(1), EPSILON());
		
		assertEquals ( 0.7311, model.predict(new double[]{0.0}), EPSILON());
		assertEquals ( 0.6225, model.predict(new double[]{0.5}), EPSILON());
		assertEquals ( 0.5000, model.predict(new double[]{1.0}), EPSILON());
	}	
	
	@Test
	public void test02LogisticRegression ()
	{
		double beta[] = new double[]{0,2};
		double x[][] = createIndependentVariable();
		double y[] = createDependentVariable(beta,x);
		LogisticRegressionModel model = createLogisticRegressionModel(x,y);
		
		assertEquals ( 2, model.parameters());
		assertEquals ( 0.0, model.getParameter(0), EPSILON());
		assertEquals ( 2.0, model.getParameter(1), EPSILON());
		
		assertEquals ( 0.5000, model.predict(new double[]{0.0}), EPSILON());
		assertEquals ( 0.7311, model.predict(new double[]{0.5}), EPSILON());
		assertEquals ( 0.8808, model.predict(new double[]{1.0}), EPSILON());
	}
	
	@Test
	public void test0m2LogisticRegression ()
	{
		double beta[] = new double[]{0,-2};
		double x[][] = createIndependentVariable();
		double y[] = createDependentVariable(beta,x);
		LogisticRegressionModel model = createLogisticRegressionModel(x,y);
		
		assertEquals ( 2, model.parameters());
		assertEquals ( 0.0, model.getParameter(0), EPSILON());
		assertEquals (-2.0, model.getParameter(1), EPSILON());
		
		assertEquals ( 0.5000, model.predict(new double[]{0.0}), EPSILON());
		assertEquals ( 0.2689, model.predict(new double[]{0.5}), EPSILON());
		assertEquals ( 0.1192, model.predict(new double[]{1.0}), EPSILON());
	}	
	
	// Two variables
	
	@Test
	public void test000LogisticRegression ()
	{
		double beta[] = new double[]{0,0,0};
		double x[][] = createIndependentVariables();
		double y[] = createDependentVariable(beta,x);
		LogisticRegressionModel model = createLogisticRegressionModel(x,y);
		
		assertEquals ( 3, model.parameters());
		assertEquals ( 0, model.getParameter(0), EPSILON());
		assertEquals ( 0, model.getParameter(1), EPSILON());
		assertEquals ( 0, model.getParameter(2), EPSILON());
		
		assertEquals ( 0.5000, model.predict(new double[]{0.0,0.0}), EPSILON());
		assertEquals ( 0.5000, model.predict(new double[]{0.0,0.5}), EPSILON());
		assertEquals ( 0.5000, model.predict(new double[]{0.0,1.0}), EPSILON());
		assertEquals ( 0.5000, model.predict(new double[]{0.5,0.0}), EPSILON());
		assertEquals ( 0.5000, model.predict(new double[]{0.5,0.5}), EPSILON());
		assertEquals ( 0.5000, model.predict(new double[]{0.5,1.0}), EPSILON());
		assertEquals ( 0.5000, model.predict(new double[]{1.0,0.0}), EPSILON());
		assertEquals ( 0.5000, model.predict(new double[]{1.0,0.5}), EPSILON());
		assertEquals ( 0.5000, model.predict(new double[]{1.0,1.0}), EPSILON());
	}	
	
	@Test
	public void test010LogisticRegression ()
	{
		double beta[] = new double[]{0,1,0};
		double x[][] = createIndependentVariables();
		double y[] = createDependentVariable(beta,x);
		LogisticRegressionModel model = createLogisticRegressionModel(x,y);
		
		assertEquals ( 3, model.parameters());
		assertEquals ( 0, model.getParameter(0), EPSILON());
		assertEquals ( 1, model.getParameter(1), EPSILON());
		assertEquals ( 0, model.getParameter(2), EPSILON());
		
		assertEquals ( 0.5000, model.predict(new double[]{0.0,0.0}), EPSILON());
		assertEquals ( 0.5000, model.predict(new double[]{0.0,0.5}), EPSILON());
		assertEquals ( 0.5000, model.predict(new double[]{0.0,1.0}), EPSILON());
		assertEquals ( 0.6225, model.predict(new double[]{0.5,0.0}), EPSILON());
		assertEquals ( 0.6225, model.predict(new double[]{0.5,0.5}), EPSILON());
		assertEquals ( 0.6225, model.predict(new double[]{0.5,1.0}), EPSILON());
		assertEquals ( 0.7311, model.predict(new double[]{1.0,0.0}), EPSILON());
		assertEquals ( 0.7311, model.predict(new double[]{1.0,0.5}), EPSILON());
		assertEquals ( 0.7311, model.predict(new double[]{1.0,1.0}), EPSILON());
	}		

	@Test
	public void test011LogisticRegression ()
	{
		double beta[] = new double[]{0,1,1};
		double x[][] = createIndependentVariables();
		double y[] = createDependentVariable(beta,x);
		LogisticRegressionModel model = createLogisticRegressionModel(x,y);
		
		assertEquals ( 3, model.parameters());
		assertEquals ( 0, model.getParameter(0), EPSILON());
		assertEquals ( 1, model.getParameter(1), EPSILON());
		assertEquals ( 1, model.getParameter(2), EPSILON());
		
		assertEquals ( 0.5000, model.predict(new double[]{0.0,0.0}), EPSILON());
		assertEquals ( 0.6225, model.predict(new double[]{0.0,0.5}), EPSILON());
		assertEquals ( 0.7311, model.predict(new double[]{0.0,1.0}), EPSILON());
		assertEquals ( 0.6225, model.predict(new double[]{0.5,0.0}), EPSILON());
		assertEquals ( 0.7311, model.predict(new double[]{0.5,0.5}), EPSILON());
		assertEquals ( 0.8176, model.predict(new double[]{0.5,1.0}), EPSILON());
		assertEquals ( 0.7311, model.predict(new double[]{1.0,0.0}), EPSILON());
		assertEquals ( 0.8176, model.predict(new double[]{1.0,0.5}), EPSILON());
		assertEquals ( 0.8808, model.predict(new double[]{1.0,1.0}), EPSILON());
	}		

	@Test
	public void test123LogisticRegression ()
	{
		double beta[] = new double[]{1,2,3};
		double x[][] = createIndependentVariables();
		double y[] = createDependentVariable(beta,x);
		LogisticRegressionModel model = createLogisticRegressionModel(x,y);
		
		assertEquals ( 3, model.parameters());
		assertEquals ( 1, model.getParameter(0), EPSILON());
		assertEquals ( 2, model.getParameter(1), EPSILON());
		assertEquals ( 3, model.getParameter(2), EPSILON());
	}
	
	@Test
	public void testm1m2m3LogisticRegression ()
	{
		double beta[] = new double[]{-1,-2,-3};
		double x[][] = createIndependentVariables();
		double y[] = createDependentVariable(beta,x);
		LogisticRegressionModel model = createLogisticRegressionModel(x,y);
		
		assertEquals ( 3, model.parameters());
		assertEquals ( -1, model.getParameter(0), EPSILON());
		assertEquals ( -2, model.getParameter(1), EPSILON());
		assertEquals ( -3, model.getParameter(2), EPSILON());
	}	
	
}
