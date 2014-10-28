package sandbox.graphs;

import ikor.math.DenseMatrix;
import ikor.math.DenseVector;
import ikor.math.Matrix;
import ikor.math.Vector;
import ikor.math.random.Random;
import noesis.BasicNetwork;
import noesis.Network;

public class BigClam 
{
	public static final double DEFAULT_LEARNING_RATE = 0.05;
	public static final int DEFAULT_MAX_ITERATIONS = 1000;
	public static final double DEFAULT_EPSILON = 0.01;
	
	Network network;
	int k;
	
	Matrix strength;
	double learningRate;
	int maxIterations;
	double epsilon;
	double J[];
	
	public BigClam (Network network, int k)
	{
		this.network = network;
		this.k = k;
	
		maxIterations = DEFAULT_MAX_ITERATIONS;
		learningRate = DEFAULT_LEARNING_RATE;
		epsilon = DEFAULT_EPSILON;
		
		// Initialization

		strength = new DenseMatrix(network.size(),k);
		
		for (int i=0; i<strength.rows(); i++)
			for (int j=0; j<strength.columns(); j++)
				strength.set(i,j, Random.random() );
		
		// Coordinate gradient ascent
		
		for (int i=0; (i<maxIterations) && !hasConverged(i); i++) {
			strength = updateParameters(strength);
			System.err.println(i+ " "+maxDelta);
		}
			
	}
	
	double maxDelta;
	
	public Matrix updateParameters (Matrix F)
	{
		Vector sum = columnSum(F);
		
		maxDelta = 0;
		
		for (int row=0; row<F.rows(); row++) {
			
			// Compute gradient
			
			Vector gradient = gradient(F,row,sum);
		
			// Update row: F_u = F_u + nu * grad(F_u)

			for (int column=0; column<F.columns(); column++) {
				double oldF = F.get(row,column);
				double newF = oldF + learningRate*gradient.get(column);
				
				if (newF<0)		 // Project to non-negative vector
					newF = 0.0;
				else if (newF>1) // Modification: Trim to 1
					newF = 1.0;

				F.set(row, column, newF );
				sum.set(column, sum.get(column)+newF-oldF);
				
				if (Math.abs(newF-oldF)>maxDelta)
					maxDelta = Math.abs(newF-oldF);
			}
			
		}
		
		return F;
	}	
	
	
	public Vector gradient (Matrix F, int node, Vector sum)
	{
		Vector grad = new DenseVector(F.columns());
		
		for (int link=0; link<network.outDegree(node); link++) {
			int neighbor = network.outLink(node, link);
			double np = Math.exp( - F.getRow(node).dotProduct( F.getRow(neighbor) ) );
			
			grad = grad.add( F.getRow(neighbor).multiply( np / (1-np) ) );
		}
		
		// - sum(F_v) for non-neighbors
		
		grad = grad.subtract( sum );
		grad = grad.add( F.getRow(node) );

		for (int link=0; link<network.outDegree(node); link++) {
			int neighbor = network.outLink(node, link);
			grad = grad.add ( F.getRow(neighbor) );
		}
		
		return grad;
	}
	
	public Vector columnSum (Matrix matrix)
	{
		Vector sum = new DenseVector(matrix.columns());
		
		for (int i=0; i<matrix.columns(); i++)
			sum.set(i, matrix.getColumn(i).sum() );
		
		return sum;
	}
	
	// Check for convergence
	
	public boolean hasConverged (int iteration)
	{
		// TODO delta(l(F))
		return (iteration>1) && (maxDelta<epsilon);
	}
	
	public String toString ()
	{
		String str = strength.rows()+ " nodes, "+ strength.columns()+" communities:\n";
		
		str += strength.toString();
		
		return str;
	}
	
	public static Network createNetwork ()
	{
		Network network = new BasicNetwork();
		
		network.setSize(10);
		
		for (int i=0; i<5; i++) {
			for (int j=i+1; j<5; j++) {
				network.add2(i, j);
				network.add2(5+i, 5+j);
			}
		}
		
		// network.add2(4, 5);
		// network.add2(4, 6);
		
		return network;
	}
	
	
	public static void main (String args[])
	{
		Network network = createNetwork();
		
		BigClam communities = new BigClam(network,2);
		
		System.out.println(communities);
	}

}
