package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.model.regular.ToroidalNetwork;
import noesis.model.regular.RegularNetwork;

import org.junit.Test;
import org.junit.Before;

public class ToroidalNetworkTest extends RegularNetworkTest
{
	private ToroidalNetwork network;
	
	int rows;
	int columns;
	
	@Override
	public RegularNetwork network()
	{
		return network;
	}
	
    @Before
	public void setUp() throws Exception 
	{
    	rows = (int) Math.sqrt(SIZE);
    	columns = SIZE/rows;
    	
    	network = new ToroidalNetwork(rows,columns);
	}
	
	@Test
	public void testLinks() 
	{
		assertEquals( 2*2*rows*columns, network.links());
	}
	
	@Test
	public void testTopology()
	{
		int size = network.size();
		int nextColumn;
		int nextRow;
		
		for (int i=0; i<size; i++) {
			assertEquals ( i, (int) network.get(i));
			
			nextColumn = (i/columns)*columns + (i+1)%columns;
			nextRow = (i+columns)%size;
			
			assertEquals ( nextColumn, (int) network.get(i,nextColumn));
			assertEquals ( i, (int) network.get(nextColumn,i));
			
			assertEquals ( nextRow, (int) network.get(i,nextRow));
			assertEquals ( i, (int) network.get(nextRow,i));
		}
	}
	
	@Test
	public void testDegrees()
	{
		for (int i=0; i<network.size(); i++) {
			assertEquals ( 4, network.outDegree(i));
			assertEquals ( 4, network.inDegree(i));
		}	
	}
}
