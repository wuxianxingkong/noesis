package test.noesis.analysis.structure;

import ikor.math.Vector;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import noesis.BasicNetwork;
import noesis.Network;

import noesis.analysis.structure.AdjustedBetweenness;
import noesis.analysis.structure.Betweenness;
import noesis.analysis.structure.Closeness;
import noesis.analysis.structure.Decay;
import noesis.analysis.structure.EigenvectorCentrality;
import noesis.analysis.structure.FreemanBetweenness;
import noesis.analysis.structure.NormalizedBetweenness;
import noesis.analysis.structure.NormalizedDecay;
import noesis.analysis.structure.NormalizedInDegree;
import noesis.analysis.structure.NormalizedOutDegree;
import noesis.analysis.structure.PageRank;


public class BowtieTest 
{
	public final double EPSILON = 1e-8;

	Network  netBowtie;

	private void connect (Network net, int node1, int node2)
	{
		net.add(node1, node2);
		net.add(node2, node1);
	}
	
	@Before
	public void setUp() throws Exception 
	{
		// From Jackson's "Social and Economic Networks" @ Coursera
		
		netBowtie = new BasicNetwork(); 
		netBowtie.setSize(7);
		
		connect(netBowtie, 0, 1);
		connect(netBowtie, 1, 2);
		connect(netBowtie, 0, 2);

		connect(netBowtie, 2, 3);
		connect(netBowtie, 3, 4);

		connect(netBowtie, 4, 5);
		connect(netBowtie, 4, 6);
		connect(netBowtie, 5, 6);
	}
	
	private void checkMeasure (Vector observed, double[] expected)
	{
		for (int i=0; i<observed.size(); i++)
			assertEquals ( expected[i], observed.get(i), EPSILON); 
	}
	

	// Degree centrality
	
	private static final double degree[] = new double[] { 2.0/6.0, 2.0/6.0, 3.0/6.0, 2.0/6.0, 3.0/6.0, 2.0/6.0, 2.0/6.0 };
	
	@Test
	public void testInDegree ()
	{
		NormalizedInDegree inDegree = new NormalizedInDegree(netBowtie);
		
		inDegree.compute();
		
		checkMeasure(inDegree, degree);
	}

	@Test
	public void testOutDegree ()
	{
		NormalizedOutDegree outDegree = new NormalizedOutDegree(netBowtie);
		
		outDegree.compute();

		checkMeasure(outDegree, degree);
	}

	// Closeness centrality
	
	private static final double closeness[] = new double[] { 6.0/15.0, 6.0/15.0, 6.0/11.0, 6.0/10.0, 6.0/11.0, 6.0/15.0, 6.0/15.0 };
	
	@Test
	public void testCloseness ()
	{
		Closeness measure = new Closeness(netBowtie);
		
		measure.compute();

		checkMeasure(measure, closeness);
	}
	
	// Decay
	
	private static final double decay0[] = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	private static final double decay1[] = new double[] { 6.0, 6.0, 6.0, 6.0, 6.0, 6.0, 6.0 };
	
	private static final double decay25[] = new double[] { 0.5859375, 0.5859375, 0.84375, 0.75, 0.84375, 0.5859375, 0.5859375 };
	private static final double decay50[] = new double[] { 1.5, 1.5, 2.0, 2.0, 2.0, 1.5, 1.5 };
	private static final double decay75[] = new double[] { 3.1171875, 3.1171875, 3.65625, 3.75, 3.65625, 3.1171875, 3.1171875 };
	
	private static final double ndecay25[] = new double[] { 0.5859375/1.5, 0.5859375/1.5, 0.84375/1.5, 0.75/1.5, 0.84375/1.5, 0.5859375/1.5, 0.5859375/1.5 };
	private static final double ndecay50[] = new double[] { 0.5, 0.5, 2.0/3.0, 2.0/3.0, 2.0/3.0, 0.5, 0.5 };
	private static final double ndecay75[] = new double[] { 3.1171875/4.5, 3.1171875/4.5, 3.65625/4.5, 3.75/4.5, 3.65625/4.5, 3.1171875/4.5, 3.1171875/4.5 };
	
	
	@Test
	public void testDecay0 ()
	{
		Decay measure = new Decay(netBowtie,0.00);
		
		measure.compute();

		checkMeasure(measure, decay0);
	}

	@Test
	public void testDecay1 ()
	{
		Decay measure = new Decay(netBowtie,1.00);
		
		measure.compute();

		checkMeasure(measure, decay1);
	}

	@Test
	public void testDecay25 ()
	{
		Decay measure = new Decay(netBowtie,0.25);
		
		measure.compute();

		checkMeasure(measure, decay25);
	}
	
	@Test
	public void testDecay50 ()
	{
		Decay measure = new Decay(netBowtie,0.50);
		
		measure.compute();

		checkMeasure(measure, decay50);
	}
	
	@Test
	public void testDecay75 ()
	{
		Decay measure = new Decay(netBowtie,0.75);
		
		measure.compute();

		checkMeasure(measure, decay75);
	}

	@Test
	public void testNormalizedDecay25 ()
	{
		NormalizedDecay measure = new NormalizedDecay(netBowtie,0.25);
		
		measure.compute();

		checkMeasure(measure, ndecay25);
	}
	
	@Test
	public void testNormalizedDecay50 ()
	{
		NormalizedDecay measure = new NormalizedDecay(netBowtie,0.50);
		
		measure.compute();

		checkMeasure(measure, ndecay50);
	}
	
	@Test
	public void testNormalizedDecay75 ()
	{
		NormalizedDecay measure = new NormalizedDecay(netBowtie,0.75);
		
		measure.compute();

		checkMeasure(measure, ndecay75);
	}

	// Betweenness centrality
	
	private static final double betweenness[] = new double[] { 13.0, 13.0, 29.0, 31.0, 29.0, 13.00, 13.00 };
	private static final double adjusted[] = new double[] { 13.0-13, 13.0-13, 29.0-13, 31.0-13, 29.0-13, 13.00-13, 13.00-13 };
	private static final double freeman[] = new double[] { 13.0/43, 13.0/43, 29.0/43, 31.0/43, 29.0/43, 13.0/43, 13.0/43 };
	private static final double normalized[] = new double[] { (13.0-13)/43, (13.0-13)/43, (29.0-13)/43, (31.0-13)/43, (29.0-13)/43, (13.0-13)/43, (13.0-13)/43 };
		
	@Test
	public void testBetweenness ()
	{
		Betweenness measure = new Betweenness(netBowtie);
		
		measure.compute();

		checkMeasure(measure, betweenness);
	}	

	@Test
	public void testBetweennessAdjust ()
	{
		AdjustedBetweenness measure = new AdjustedBetweenness(netBowtie);
		
		measure.compute();

		checkMeasure(measure, adjusted);
	}	
	
	@Test
	public void testBetweennessFreeman ()
	{
		FreemanBetweenness measure = new FreemanBetweenness(netBowtie);
		
		measure.compute();

		checkMeasure(measure, freeman);
	}
	
	
	@Test
	public void testBetweennessNormalized ()
	{
		NormalizedBetweenness measure = new NormalizedBetweenness(netBowtie);
		
		measure.compute();

		checkMeasure(measure, normalized);
	}	

	// Eigenvector centrality
	
	private static final double EV_CORNER = 0.33480516002936894; // 0.745 or 0.47
	private static final double EV_GATEWAY = 0.4496180482622337; // 1.000 or 0.63
	private static final double EV_BRIDGE = 0.38380885949200544; // 0.853 or 0.54
	
	private static final double eigenvector[] = new double[] { EV_CORNER, EV_CORNER, EV_GATEWAY, EV_BRIDGE, EV_GATEWAY, EV_CORNER, EV_CORNER };
	
	@Test
	public void testEigenvector ()
	{
		EigenvectorCentrality measure = new EigenvectorCentrality(netBowtie);
		
		measure.compute();

		checkMeasure(measure, eigenvector);
	}

	// PageRank centrality

	private static final double PR_CORNER = 0.12733686137367323;
	private static final double PR_GATEWAY = 0.18282987837819772;
	private static final double PR_BRIDGE = 0.12499279774891152;
	private static final double pagerank[] = new double[] { PR_CORNER, PR_CORNER, PR_GATEWAY, PR_BRIDGE, PR_GATEWAY, PR_CORNER, PR_CORNER };

	@Test
	public void testPageRank ()
	{
		PageRank measure = new PageRank(netBowtie);
		
		measure.compute();

		checkMeasure(measure, pagerank);
	}
	
}
