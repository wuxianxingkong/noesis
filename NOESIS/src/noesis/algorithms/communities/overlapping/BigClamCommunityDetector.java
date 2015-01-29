package noesis.algorithms.communities.overlapping;

import ikor.collection.DynamicPriorityQueue;
import ikor.collection.DynamicSet;
import ikor.collection.PriorityQueue;
import ikor.collection.Set;
import ikor.collection.util.Pair;
import ikor.math.DenseMatrix;
import ikor.math.DenseVector;
import ikor.math.Matrix;
import ikor.math.Vector;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;

import java.util.Comparator;

import noesis.AttributeNetwork;
import noesis.algorithms.communities.CommunityDetector;

/**
 * BigClam Community Detection Algorithm: Cluster Affiliation Model for Big Networks.
 *
 * Reference: 
 * - Jaewon Yang and Jure Leskovec:
 *   "Overlapping community detection at scale: a nonnegative matrix factorization approach." 
 *   In Proceedings of the sixth ACM International Conference on Web Search and Data Mining (WSDM '13), 2013. 
 *   DOI 10.1145/2433396.2433471
 * 
 * @author Fernando Berzal (berzal@acm.org) & Victor Martinez (victormg@acm.org)
 */
@Label("BigClam")
@Description("BigClam community detection algorithm")
public class BigClamCommunityDetector extends CommunityDetector 
{

	// Default max iterations for gradient descent
	public static final int DEFAULT_MAX_ITERATIONS = 10000;
	
	// Initial step size for gradient descent
	public static final double INITIAL_STEP_SIZE = 1.0;
	
	// Default minimal improvement ratio for stop condition
	public static final double DEFAULT_STOP_RATIO = 0.01;
	
	// Default max iterations for backtracking line search
	public static final int DEFAULT_BLS_ITERATIONS = 100;
	
	// Alpha parameter for backtracking line search
	public static final double ALPHA = 0.15;
	
	// Beta parameter for backtracking line search
	public static final double BETA = 0.5;
	
	private boolean hasConverged;
	private int maxIterations;
	private double initialStepSize;
	private double defaultStopRatio;
	private double defaultBLSIterations;
	private double alpha;
	private double beta;
	private int[][] undirectedNeighbors;
	
	public BigClamCommunityDetector (AttributeNetwork network) 
	{
		this( network, 
			  DEFAULT_MAX_ITERATIONS, INITIAL_STEP_SIZE, DEFAULT_STOP_RATIO,
			  DEFAULT_BLS_ITERATIONS, ALPHA, BETA);
	}
	
	public BigClamCommunityDetector (AttributeNetwork network, int maxIterations, double initialStepSize,
			double defaultStopRatio, double defaultBLSIterations, double alpha, double beta) 
	{
		super(network);
		this.maxIterations = maxIterations;
		this.initialStepSize = initialStepSize;
		this.defaultStopRatio = defaultStopRatio;
		this.defaultBLSIterations = defaultBLSIterations;
		this.alpha = alpha;
		this.beta = beta;
	}

	@Override
	public void compute () 
	{
		hasConverged = false;
		
		// Check for at least two nodes
		if( network.nodes() < 2 ) {
			
			if( network.nodes() == 1 )
				results.setRow(0, new double[]{1.0});
			
		} else {
			
			// To treat the network as undirected
			initUndirectedNeighbors();
			
			// Initial cluster assignments
			int[] communitySeeds = computeCommunitySeeds();
			Matrix strengths = initStrengths(communitySeeds);
			
			// Update until convergence
			for ( int iteration=0; (iteration<maxIterations) && !hasConverged; iteration++ ) {
				updateStrengths(strengths);
			}
			
			// Get main cluster
			Vector nodeCluster = getClusterAssignment(strengths);
			
			results.setRow(0, nodeCluster);
		}
	}
	
	public void initUndirectedNeighbors() 
	{
		undirectedNeighbors = new int[network.nodes()][];
		Set<Integer> neighbors = new DynamicSet<Integer>();
	
		for ( int node = 0; node < network.nodes() ; node++ ) {
			int[] inNeighbors = network.inLinks(node);
			if ( inNeighbors != null )
				for ( int in : inNeighbors )
					neighbors.add(in);
			
			int[] outNeighbors = network.outLinks(node);
			if ( outNeighbors != null )
				for ( int out : outNeighbors )
					neighbors.add(out);
			
			undirectedNeighbors[node] = new int[neighbors.size()];
			int i = 0;
			for ( int neighbor : neighbors ) {
				undirectedNeighbors[node][i] = neighbor;
				i++;
			}
			
			neighbors.clear();
		}
	}
	
	
	private void updateStrengths (Matrix F) 
	{
		// Compute column sum
		Vector sum = new DenseVector(F.columns());
		for ( int cluster=0 ; cluster < F.columns() ; cluster++ )
			sum.set(cluster, F.getColumn(cluster).sum() );
		
		// Compute current global likelihood
		double currentLikelihood = 0;
		for ( int row = 0 ; row < F.rows() ; row++ )
			currentLikelihood += computeRowLikelihood(F,row,sum,null);
		
		
		Vector newFRow = new DenseVector(F.columns());
		double nextLikelihood = 0;
		
		for ( int row = 0 ; row < F.rows() ; row++ ) {
			
			// Compute row gradient
			Vector gradient = computeGradient(F,row,sum);
			
			// Compute step size by backtracking line search
			double stepSize = initialStepSize;

			double initialL = computeRowLikelihood(F,row,sum,null);
			double nextL = 0;
			
			for( int localIterations = 0; localIterations < defaultBLSIterations ; localIterations++ ) {
				nextL = computeRowLikelihood(F,row,sum,gradient.multiply(stepSize));
				double estimated = initialL + alpha * stepSize * gradient.dotProduct(gradient);
				
				// Infinite values are obtained when all components become zero due to too large step
				if( nextL < estimated || !Double.isFinite(nextL) || !Double.isFinite(estimated) ) {
					stepSize *= beta;
				} else {
					break;
				}
				
				if(localIterations==defaultBLSIterations-1)
					stepSize = 0.0;
			}

			
			// Update strengths
			for ( int column = 0; column < F.columns() ; column++ ) {
				double oldF = F.get(row,column);
				double gradientValue = gradient.get(column);
				double newF = oldF + stepSize * gradientValue;
				
				// Project to [0,1] vector
				newF = Math.max( Math.min( newF , 1.0) , 0.0) ;

				newFRow.set(column,newF);
			}
			
			sum = sum.add(newFRow).subtract(F.getRow(row));
			F.setRow(row, newFRow);
			
			double rowLikelihood = computeRowLikelihood(F,row,sum,null);
			nextLikelihood += rowLikelihood;
		}
		
		// Check stopping condition
		if( currentLikelihood == 0.0 || (Math.abs(nextLikelihood-currentLikelihood)/Math.abs(currentLikelihood) < defaultStopRatio))
			hasConverged = true;
		
		currentLikelihood = nextLikelihood;
	}
	
	
	private double computeRowLikelihood (Matrix F, int node, Vector sum, Vector offset) 
	{
		double likelihood = 0;
		
		Vector Fu = F.getRow(node);
		if(offset!=null) {
			Fu = Fu.add(offset);
		
			// Project to [0,1] vector
			for(int i=0;i<Fu.size();i++)
				Fu.set(i, Math.max( Math.min( Fu.get(i) , 1.0) , 0.0) );
		}
		
		for ( int neighbor : undirectedNeighbors[node] ) {
			likelihood += Math.log(1.0 - Math.exp( - Fu.dotProduct( F.getRow(neighbor) ) ));
		}
		
		Vector term = new DenseVector(F.columns());
		term = term.add( Fu );
		
		term = term.subtract( sum );
		for ( int neighbor : undirectedNeighbors[node] ) {
			term = term.add ( F.getRow(neighbor) );
		}
		
		likelihood += Fu.dotProduct(term);
		return likelihood;
	}
	
	
	private Vector computeGradient (Matrix F, int node, Vector sum) 
	{
		Vector gradient = new DenseVector(F.columns());
		
		for ( int neighbor : undirectedNeighbors[node] ) {
			double np = Math.exp( - F.getRow(node).dotProduct( F.getRow(neighbor) ) );
			gradient = gradient.add( F.getRow(neighbor).multiply( np / (1-np) ) );
				
			// Remove neighbors from sum substraction
			gradient = gradient.add ( F.getRow(neighbor) );
		}
		
		gradient = gradient.add( F.getRow(node) );
		gradient = gradient.subtract( sum );
		
		return gradient;
	}
	

	private double nodeConductance (int nodeIndex) 
	{
		if( undirectedNeighbors[nodeIndex].length == 0 )
			return 0;
		
		Set<Integer> neighbors = new DynamicSet<Integer>();
		neighbors.add(nodeIndex);
		for( int neighbor : undirectedNeighbors[nodeIndex] ) {
			neighbors.add(neighbor);
		}
		
		double neighborsDegree = 0;
		double nonNeighborsDegree = 0;
		for( int node = 0 ; node < network.nodes() ; node++ ) {
			if(neighbors.contains(node))
				neighborsDegree += undirectedNeighbors[node].length;
			else
				nonNeighborsDegree += undirectedNeighbors[node].length;
		}
		
		double numerator = 0;
		for( int neighbor : undirectedNeighbors[nodeIndex] ) {
				for( int neighbor2 : undirectedNeighbors[neighbor] ) {
					if( !neighbors.contains(neighbor2) )
						numerator++;
				}
		}
		
		return numerator/Math.min(neighborsDegree, nonNeighborsDegree);
	}
	
	private int[] computeCommunitySeeds() 
	{
		double[] conductance = new double[network.nodes()];
		for( int nodeIndex = 0 ; nodeIndex < network.nodes() ; nodeIndex++ )
			conductance[nodeIndex] = nodeConductance(nodeIndex);
		
		PriorityQueue<Pair<Integer,Double>> conductanceList = 
			new DynamicPriorityQueue<Pair<Integer,Double>>(
				new Comparator<Pair<Integer,Double>>() {
					@Override
					public int compare(Pair<Integer,Double> o1, Pair<Integer,Double> o2) {
						return o1.second().compareTo(o2.second());
					}
				}
			);
		
		
		for( int node = 0 ; node < network.nodes() ; node++ ) {
			if( undirectedNeighbors[node].length > 0 ) {
				boolean isSeed = true;
				double avgConductance = 0;
				for( int neigh : undirectedNeighbors[node] ) {
					avgConductance += conductance[neigh];
					if( conductance[node] >= conductance[neigh] )
						isSeed = false;
				}
				avgConductance /= undirectedNeighbors[node].length;
				if( isSeed )
					conductanceList.add(new Pair<Integer,Double>(node,avgConductance-conductance[node]));
			}
		}
		
		int[] seeds = new int[conductanceList.size()];
		
		for( int seed = 0 ; seed < seeds.length ; seed++ ) {
			seeds[seed] = conductanceList.get().first();
		}
		
		return seeds;
	}
	
	
	private Matrix initStrengths (int[] communitySeeds) 
	{
		// Force at least two communities
		int[] actualCommunitySeeds = null;
		if ( communitySeeds.length < 2 ) {
			// Fixed community seeds
			actualCommunitySeeds = new int[] {0, network.nodes()-1};
		} else {
			actualCommunitySeeds = communitySeeds;
		}
		
		// Init strength
		Matrix strengths = new DenseMatrix(network.nodes(), actualCommunitySeeds.length);

		for ( int cluster = 0 ; cluster < actualCommunitySeeds.length ; cluster++ ) {
			strengths.set(actualCommunitySeeds[cluster], cluster, 1.0);
			for(int neighbor : undirectedNeighbors[actualCommunitySeeds[cluster]])
				strengths.set(neighbor, cluster, 1.0 );
			
			for ( int i = 0 ; i < strengths.rows() ; i++ )
				for ( int j = 0 ; j < strengths.columns(); j++ )
					if( strengths.get(i, j) != 1.0 )
						strengths.set(i,j, 0.01 );
			
		}
		
		return strengths;
	}
	
	private Vector getClusterAssignment (Matrix F) 
	{
		Vector nodeCluster = new DenseVector(network.nodes());
		
		for( int nodeIndex = 0 ; nodeIndex < network.nodes() ; nodeIndex++ ) {
			double strongerScore = -Double.MAX_VALUE;
			int strongerIndex = -1;
			
			for( int clusterIndex = 0 ; clusterIndex < F.columns() ; clusterIndex++ ) {
				if( strongerScore < F.get(nodeIndex, clusterIndex) ) {
					strongerIndex = clusterIndex;
					strongerScore = F.get(nodeIndex, clusterIndex);
				}
			}
			
			nodeCluster.set(nodeIndex, strongerIndex);
		}
		
		return nodeCluster;
	}
}
