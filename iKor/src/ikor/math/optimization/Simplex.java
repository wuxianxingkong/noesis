package ikor.math.optimization;

import static ikor.math.Configuration.EPSILON;

/**
 *  Simplex algorithm. Extended from Sedgewick's "Algorithms" [4th edition]
 *  (http://algs4.cs.princeton.edu/65reductions/Simplex.java.html).
 *  
 *  Given an M-by-N matrix A, an M-length vector b, and an
 *  N-length vector c, solve the  LP { max cx : Ax <= b, x >= 0 }.
 *  Assumes that b >= 0 so that x = 0 is a basic feasible solution.
 *
 *  Creates an (M+1)-by-(N+M+1) simplex tableau with the 
 *  RHS in column M+N, the objective function in row M, and
 *  slack variables in columns M through M+N-1.
 * 
 *  When solving minimization problems or using >= constraints,
 *  the algorithm employs an (M+1)x(N+2M+1) tableau. 
 *
 */
public class Simplex 
{
	private double[][] A;
	private double[]   b;
	private double[]   c;
	private OP[]       op;
	
    private double[][] a;   // tableau (variables + slack(max)/surplus(min) + artificial)
    private int M;          // number of constraints
    private int N;          // number of original variables
    private int Q;          // last column
   
    private int[] basis;    // basis[i] = basic variable corresponding to row i

    
    // Constructor
    
    public Simplex(double[][] A, double[] b, double[] c) 
    {
    	this.A = A;
    	this.b = b;
    	this.c = c;
    	
        M = b.length;
        N = c.length;
        Q = M+N;
        a = new double[M+1][N+M+1];
        
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = A[i][j];
        
        for (int i = 0; i < M; i++) 
        	a[i][N+i] = 1.0;
        
        for (int j = 0; j < N; j++) 
        	a[M][j]   = c[j];
        
        for (int i = 0; i < M; i++) 
        	a[i][M+N] = b[i];

        basis = new int[M];
        
        for (int i = 0; i < M; i++) 
        	basis[i] = N + i;

    }

    
    public enum OP { LT, LE, GT, GE, EQ }; 
    
    public Simplex(double[][] A, OP[] op, double[] b, double[] c) 
    {
    	this.A = A;
    	this.b = b;
    	this.c = c;
    	this.op = op;
    	
        M = b.length;
        N = c.length;
        Q = N+2*M;
   
        a = new double[M+1][N+M+M+1];
        
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = A[i][j];
        
        // Slack / surplus variables
        
        for (int i = 0; i < M; i++)					
        	if ( (op[i]==OP.GT) || (op[i]==OP.GE))
        		a[i][N+i] = -1.0;
        	else if ( (op[i]==OP.LT) || (op[i]==OP.LE))
        		a[i][N+i] = 1.0;

        // Artificial variables
        
        for (int i = 0; i < M; i++)					
        	if ( (op[i]==OP.EQ) || (op[i]==OP.GE) )
        		a[i][N+M+i] = 1.0;
        
        // Final row
        
        for (int j=0; j<N; j++) 
        	a[M][j] = c[j];
        
        // Final column
        
        for (int i = 0; i < M; i++) 
        	a[i][2*M+N] = b[i];

        basis = new int[M];
        
        for (int i = 0; i < M; i++) 
        	basis[i] = N + M + i;

    }
    
   
    
    // run simplex algorithm starting from initial BFS
    
    public void maximize() 
    {
    	maxCost();
    	    	
    	int q = bland();
    	
        while (q!=-1) {

            // find leaving row p
            int p = minRatioRule(q);
            
            if (p == -1)
            	throw new RuntimeException("Linear program is unbounded");

            // pivot
            pivot(p, q);

            // update basis
            basis[p] = q;

            // find entering column q (bland/dantzig)
            q = bland();
        }

        // check optimality conditions
        
        if (!check())
        	throw new RuntimeException("Linear program unable to find maximum.");
    }

    
    
    public void minimize()
    {
    	minCost();
    	initFinalRow();

    	int q = blandMin();
    	
        while (q!=-1) {
        	
            // find leaving row p
            int p = minRatioRule(q);
            
            if (p == -1)
            	throw new RuntimeException("Linear program is unbounded");

            // pivot
            pivot(p, q);

            // update basis
            basis[p] = q;

            // find entering column q
            q = blandMin();
        }

        // check optimality conditions
        
        if (!isPrimalFeasible())
    		throw new RuntimeException("Linear program unable to find minimum.");
        
    }    

    private static final double LARGE = 1e6;
       
    
    double[] cost;

    private void minCost ()
    {
    	cost = new double[N+2*M];
    	
    	for (int i=0; i<N; i++)
    		cost[i] = c[i];

        for (int j=N; j<N+M; j++) 
        	cost[j] = 0;
    	
        for (int j=N+M; j<N+2*M; j++) 
        	cost[j] = LARGE;
    }
    
    private void maxCost ()
    {
    	cost = new double[N+2*M];
    	
    	for (int i=0; i<N; i++)
    		cost[i] = c[i];

        for (int j=N; j<N+M; j++) 
        	cost[j] = 0;
    	
        for (int j=N+M; j<N+2*M; j++) 
        	cost[j] = -LARGE;
    }
    
    
    private void initFinalRow ()
    {
    	for (int i=0; i<=Q; i++) {
    		
    		if (i<cost.length)
    			a[M][i] = cost[i];
    	
    		for (int j=0; j<M; j++)
    			a[M][i] -= cost[N+M+j]*a[j][i];
    	}
    }
    
    // Lowest index of a non-basic column with a positive cost
    
    protected int bland()
    {
        for (int j = 0; j < Q; j++)
            if (a[M][j] > 0) 
            	return j;
        
        return -1;  // optimal
    }
    
    // Lowest index of a non-basic column with a decrease in cost
    
    protected int blandMin()
    {
        for (int j = 0; j < Q; j++)
            if (a[M][j] < 0) 
            	return j;
        
        return -1;  // optimal
    }    

    // Index of a non-basic column with most positive cost
    // NOTE: Degenerate problems cycle if you choose most positive objective function coefficient
    
    protected int dantzig() 
    {
        int q = 0;
        
        for (int j = 1; j < Q; j++)
            if (a[M][j] > a[M][q]) 
            	q = j;

        if (a[M][q] <= 0) 
        	return -1;  // optimal
        else 
        	return q;
    }

    protected int dantzigMin() 
    {
        int q = 0;
        
        for (int j = 1; j < Q; j++)
            if (a[M][j] < a[M][q]) 
            	q = j;

        if (a[M][q] >= 0) 
        	return -1;  // optimal
        else 
        	return q;
    }
    
    // find row p using min ratio rule (-1 if no such row)
    
    private int minRatioRule(int q)
    {
        int p = -1;
        
        for (int i = 0; i < M; i++) {
            if (a[i][q]>0) { 
            	if (p == -1) 
            		p = i;
            	else if ((a[i][Q] / a[i][q]) < (a[p][Q] / a[p][q])) 
            		p = i;
            }
        }
        
        return p;
    }

    // pivot on entry (p, q) using Gauss-Jordan elimination
    
    private void pivot(int p, int q) 
    {
        // everything but row p and column q
        for (int i = 0; i <= M; i++)
            for (int j = 0; j <= Q; j++)
                if (i != p && j != q) 
                	a[i][j] -= a[p][j] * a[i][q] / a[p][q];

        // zero out column q
        for (int i = 0; i <= M; i++)
            if (i != p) 
            	a[i][q] = 0.0;

        // scale row p
        for (int j = 0; j <= Q; j++)
            if (j != q) 
            	a[p][j] /= a[p][q];
        
        a[p][q] = 1.0;
    }

    // return optimal objective value
    
    public double value() 
    {
        return -a[M][Q];
    }

    // return primal solution vector
    
    public double[] primal() 
    {
        double[] x = new double[N];
        
        for (int i = 0; i < M; i++)
            if (basis[i] < N) 
            	x[basis[i]] = a[i][Q];
        
        return x;
    }
    
    private int basicRow (int x)
    {
        for (int i = 0; i < M; i++)
            if (basis[i] == x)
            	return i;
        
        return -1;   	
    }

    // return dual solution vector
    
    public double[] dual() 
    {
        double[] y = new double[M];
        
        for (int i = 0; i < M; i++)
            y[i] = Math.abs(a[M][N+i]);
        
        return y;
    }


    // is the solution primal feasible?
    
    private boolean isPrimalFeasible ()
    {
        double[] x = primal();

        // check that x >= 0
        for (int j = 0; j < x.length; j++) {
            if (x[j] < 0.0) 
                return false;
        }

        // check that Ax <= b
        for (int i = 0; i < M; i++) {           
            if (!isPrimalSatisfied(x,i))
                return false;
        }
        
        return true;
    }
    
    private boolean isPrimalSatisfied (double[] x, int i)
    {
    	double sum = 0.0;
        OP     operator = (op!=null)? op[i]: OP.LE;
        
        for (int j = 0; j < N; j++) {
            sum += A[i][j] * x[j];
        }     
        
        return checkOP(sum, operator, b[i]);
    }
    
    
    private boolean checkOP (double a, OP op, double b)
    {
    	switch (op) {
			case EQ: return Math.abs(a-b)<EPSILON;
			case LE: return a<=b+EPSILON;
			case LT: return a<b+EPSILON;
			case GE: return a>=b-EPSILON;
			case GT: return a>b-EPSILON;
    	}
	
    	return false;    	
    }

    
    // is the solution dual feasible?
    
    private boolean isDualFeasible () 
    {
        double[] y = dual();
        
        // check that y >= 0
        
        for (int i = 0; i < y.length; i++) {
            if (y[i] < 0.0) 
                return false;
        }

        // check that yA >= c
        
        for (int j = 0; j < N; j++) {
        	
        	if (!isDualSatisfied(y,j))
                return false;
        }
        
        return true;
    }
    

    private boolean isDualSatisfied (double[] y, int j)
    {
        double sum = 0.0;
        OP     operator = (op!=null)? op[j]: OP.LE;
        
        for (int i = 0; i < M; i++) {
            sum += A[i][j] * y[i];
        }
        
        //System.out.println(sum + " ? " + c[j]);
        return checkDualOP(sum, operator, c[j]);        
    }
    
    
    private boolean checkDualOP (double a, OP op, double b)
    {
    	switch (op) {
			case EQ: return Math.abs(a-b)<EPSILON;
			case LE: return a>b-EPSILON;
			case LT: return a>=b-EPSILON;
			case GE: return a<b+EPSILON;
			case GT: return a<=b+EPSILON;
    	}
	
    	return false;    	
    }    
    
    // check that optimal value = cx = yb
    
    private boolean isOptimal() 
    {
        double[] x = primal();
        double[] y = dual();
        double value = value();

        // check that value = cx = yb
        double value1 = 0.0;
        
        for (int j = 0; j < x.length; j++)
            value1 += c[j] * x[j];
        
        double value2 = 0.0;
        
        for (int i = 0; i < y.length; i++)
            value2 += y[i] * b[i];
        
        return (Math.abs(value - value1) <= EPSILON)
            && (Math.abs(value - value2) <= EPSILON); 
    }

    public boolean check () 
    {
        return isPrimalFeasible() && isDualFeasible() && isOptimal();
    }
    
    // Sensitivity analysis
    
    /**
     * Sensitivity analysis: Changes in the objective function coefficients
     */
  
    public double[] reduced ()
    {
        double[] r = new double[N];
        
        for (int i=0; i<N; i++)
            r[i] = a[M][i];
        
        return r;
    }

    // Slack/surplus
    
    public double[] used ()
    {
        double[] u = new double[M];
        double[] x = primal(); 
        
        for (int i=0; i<M; i++) 
        	for (int j=0; j<N; j++)
        		u[i] += A[i][j]*x[j];
        
        return u;
    }    
    
    
    // Range of insignificance (for maximization problems)
    
    public double insignificance (int i)
    {
    	return c[i] - a[M][i];
    }
    
    // Range of optimality (for maximization problems)
    
    public double optimality (int v)
    {
    	int    row = basicRow(v);
    	double max = 0;
    	double delta;
    	
    	if (row!=-1) {
    		
    		for (int i=0; i<Q; i++) {
    			
    			if ( (i!=v) && (a[row][i]!=0) && (a[M][i]<0) ) {
    				
    				delta = - a[M][i] / a[row][i];
    				             
    				if (c[v]-delta>max)
    					max = c[v] - delta;    				 
    			}
    		}
    	}
    	
    	return max;
    }
    
    
    // RHS ranging
    
    public double reducedRHS (int constraint)
    {
    	double ratio;
    	double result = b[constraint];
    	
    	for (int i=0; i<M; i++) {
    		ratio = a[i][Q]/a[i][N+constraint];
    		
    		if ((ratio>0) && (ratio<result))
    			result = ratio;
    	}
    	
    	return b[constraint]-result;
    }
    
    public double increasedRHS (int constraint)
    {
    	double  ratio;
    	double  result = 0;
    	boolean constrained = false;
    	
    	for (int i=0; i<M; i++) {
    		ratio = a[i][Q]/a[i][N+constraint];
    		
    		if ((ratio<0) && (ratio<result)) {
    			result = ratio;
    			constrained = true;
    		}
    	}
    	
    	if (constrained)
    		return b[constraint] - result;
    	else
    		return Double.POSITIVE_INFINITY;
    }
        
    // Standard output
    
    @Override
    public String toString ()
    {
    	StringBuffer buffer = new StringBuffer();

    	// Goal
    	
    	buffer.append("Optimize\n\t");
    	
    	for (int i=0; i<c.length; i++)
    		if (c[i]>0)
    			buffer.append( " + " + Math.abs(c[i]) + "*x["+i+"]");
    		else
    			buffer.append( " - " + Math.abs(c[i]) + "*x["+i+"]");
    
    	// Constraints
    	
    	buffer.append("\nSubject to\n");

    	for (int i=0; i<b.length; i++) {
    		
    		buffer.append("\t");
    		
        	for (int j=0; j<A[i].length; j++) {
        		if (A[i][j]>0)
        			buffer.append( " + " + Math.abs(A[i][j]) + "*x["+j+"]");
        		else
        			buffer.append( " - " + Math.abs(A[i][j]) + "*x["+j+"]");
        		
        	}
        	
        	if (op!=null) {
        		
        		buffer.append(" " + opString(op[i]) + " ");
        		
        	} else {
        		buffer.append(" <= ");
        	}	
        	
        	buffer.append(b[i]+"\n");
    	}
    	
    	// Solution
    	
    	buffer.append("N = " + N+"\n");
    	buffer.append("M = " + M+"\n");
        buffer.append("value  = " + value() + "\n");
        buffer.append("primal = "+ toString(primal()) + "\n");
        buffer.append("dual   = "+ toString(dual()) + "\n");
        
        // Tableaux
        
    	buffer.append("TABLEAU\n");
    	
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                buffer.append("\t"+ String.format("%7.2f ", a[i][j]));
            }
            buffer.append("\n");
        }

        return buffer.toString();
    }
    
    
    private String opString (OP op) 
    {
    	switch (op) {
    		case EQ: return "=";
    		case LE: return "<=";
    		case LT: return "<";
    		case GE: return ">=";
    		case GT: return ">";
    	}
    	
    	return "<=";
    }
    private String toString (double[] v)
    {
    	StringBuffer buffer = new StringBuffer();
    	
    	buffer.append("["+v[0]);
    	
    	for (int i=1; i<v.length; i++)
    		buffer.append(", "+ v[i]);
    	
    	buffer.append("]");
    	
    	return buffer.toString();
    }

}
