package test.noesis.analysis.structure;

import static org.junit.Assert.assertEquals;
import ikor.math.DenseMatrix;
import ikor.math.DenseVector;
import ikor.math.Matrix;
import ikor.math.Vector;
import noesis.BasicNetwork;
import noesis.Network;
import noesis.analysis.NodeScore;
import noesis.analysis.structure.DiffusionCentrality;
import noesis.network.AdjacencyMatrix;

import org.junit.Test;

/**
 * Diffusion Centrality unit tests
 * 
 * @author Victor Martinez (victormg@acm.org)
 */

public class DiffusionCentralityTest
{
	public final double EPSILON = 1e-9;

	@Test
	public void testBasicNetwork() 
	{
		BasicNetwork network = new BasicNetwork();
		network.setSize(4);
		network.add2(0, 1);
		network.add2(1, 2);
		network.add2(2, 3);
		
		performTest(network, 0.3, 2);
		performTest(network, 0.1, 4);
		performTest(network, 0.5, 7);
	}
	
	@Test
	public void testBowtieNetwork() 
	{
		BasicNetwork netBowtie = new BasicNetwork(); 
		netBowtie.setSize(7);
		
		netBowtie.add2(0, 1);
		netBowtie.add2(1, 2);
		netBowtie.add2(0, 2);

		netBowtie.add2(2, 3);
		netBowtie.add2(3, 4);

		netBowtie.add2(4, 5);
		netBowtie.add2(4, 6);
		netBowtie.add2(5, 6);
		
		performTest(netBowtie, 0.2, 3);
		performTest(netBowtie, 0.7, 6);
		performTest(netBowtie, 0.3, 10);
	}
	
	@Test
	public void testUndirectedNetwork() 
	{
		BasicNetwork undirectedNetwork = new BasicNetwork(); 
		undirectedNetwork.setSize(6);
		
		undirectedNetwork.add(2, 0);
		undirectedNetwork.add(0, 3);
		
		undirectedNetwork.add2(1, 2);
		undirectedNetwork.add2(1, 3);
		
		undirectedNetwork.add(5, 5);
		
		performTest(undirectedNetwork, 0.3, 3);
		performTest(undirectedNetwork, 0.5, 6);
		performTest(undirectedNetwork, 0.7, 10);
	}
	
	private void performTest(Network network, double passingProbability, int interations) 
	{		
		DiffusionCentrality centrality = new DiffusionCentrality(network, passingProbability, interations);
		NodeScore centralityScores = centrality.call();
	
		Vector expectedScores = computeExpectedValues(network, passingProbability, interations);
		
		for (int i=0; i<network.size(); i++)
			assertEquals ( expectedScores.get(i), centralityScores.get(i), EPSILON ); 
	}
	
	
	private Vector computeExpectedValues (Network network, double passingProbability, int iterations) 
	{
		int numNodes = network.nodes();
		
		AdjacencyMatrix adjacency = new AdjacencyMatrix(network);
		Matrix probAdjancecy = adjacency.multiply(passingProbability);
		
		Matrix resultingMatrix = new DenseMatrix(numNodes, numNodes);
		for (int t=1 ; t<=iterations ; t++)
			resultingMatrix = resultingMatrix.add(probAdjancecy.power(t));
		
		Matrix oneVector = new DenseMatrix(numNodes,1);
		for(int i=0;i<numNodes;i++)
			oneVector.set(i, 0, 1.0);
		
		resultingMatrix = resultingMatrix.multiply(oneVector);
		
		Vector result = new DenseVector(numNodes);
		for(int i=0;i<numNodes;i++)
			result.set(i, resultingMatrix.get(i, 0));
		
		return result;
	}
}
