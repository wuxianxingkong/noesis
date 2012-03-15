package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.model.regular.MeshNetwork;
import noesis.model.regular.RegularNetwork;

import org.junit.Test;
import org.junit.Before;

public class MeshNetworkTest extends RegularNetworkBasicTest
{
	private MeshNetwork network;
	
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
    	
    	network = new MeshNetwork(rows,columns);
	}
	
	@Test
	public void testLinks() 
	{
		assertEquals( 4*rows*columns - 2*(rows+columns), network.links());
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
			
			if (nextColumn%columns!=0) {
				assertEquals ( nextColumn, (int) network.get(i,nextColumn));
				assertEquals ( i, (int) network.get(nextColumn,i));
			}
			
			if (nextRow/columns!=0) {
				assertEquals ( nextRow, (int) network.get(i,nextRow));
				assertEquals ( i, (int) network.get(nextRow,i));
			}
				
		}
	}
	
	
	private boolean atCorner (int i)
	{
		return ((i%columns==0) || (i%columns==columns-1))
		    && ((i/columns==0) || (i/columns==rows-1));
	}
	
	private boolean atBorder (int i)
	{
		return ((i%columns==0) || (i%columns==columns-1))
	        || ((i/columns==0) || (i/columns==rows-1));
	}
	
	@Test
	public void testDegrees()
	{
		for (int i=0; i<network.size(); i++) {
			
			if (atCorner(i)) {
				assertEquals ( 2, network.outDegree(i));
				assertEquals ( 2, network.inDegree(i));			
			} else if (atBorder(i)) {
				assertEquals ( 3, network.outDegree(i));
				assertEquals ( 3, network.inDegree(i));
			} else {
				assertEquals ( 4, network.outDegree(i));
				assertEquals ( 4, network.inDegree(i));
			}
		}	
	}
}
