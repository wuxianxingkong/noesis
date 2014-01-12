package test.noesis;

import static org.junit.Assert.*;

import noesis.AttributeNetwork;
import noesis.DynamicNetwork;

import org.junit.Before;
import org.junit.Test;


public class DynamicNetworkTest 
{
	private static final int BASE_NETWORK_SIZE = 10;
	private static final int BASE_NETWORK_LINKS = BASE_NETWORK_SIZE*(BASE_NETWORK_SIZE-1)/2;

	AttributeNetwork base;
	DynamicNetwork   dynamic;
	
	@Before
	public void setUp() throws Exception 
	{
		base = new AttributeNetwork();
		
		for (int i=0; i<BASE_NETWORK_SIZE; i++)
			base.add(i);


		for (int i=0; i<BASE_NETWORK_SIZE; i++)
			for (int j=i+1; j<BASE_NETWORK_SIZE; j++)
				base.add(i,j);
		
		dynamic = new DynamicNetwork(base);
	}
	
	@Test
	public void testConstructor() 
	{
		assertEquals( BASE_NETWORK_SIZE, dynamic.size() );
		assertEquals( BASE_NETWORK_LINKS, dynamic.links() );
		
		for (int i=0; i<BASE_NETWORK_SIZE; i++) {
			assertEquals ( i, dynamic.inDegree(i) );
			assertEquals ( BASE_NETWORK_SIZE-i-1, dynamic.outDegree(i) );
			
			assertEquals ( i, dynamic.index(i) );
			
			for (int j=0; j<dynamic.inDegree(i); j++)
				assertEquals ( j, dynamic.inLink(i,j));

			for (int j=0; j<dynamic.outDegree(i); j++)
				assertEquals ( i+j+1, dynamic.outLink(i,j));
		}
	}

	@Test
	public void testAddNode ()
	{
		dynamic.add(1000);
		
		assertEquals( BASE_NETWORK_SIZE+1, dynamic.size() );
		assertEquals( BASE_NETWORK_LINKS, dynamic.links() );
		
		for (int i=0; i<BASE_NETWORK_SIZE; i++) {
			assertEquals ( i, dynamic.inDegree(i) );
			assertEquals ( BASE_NETWORK_SIZE-i-1, dynamic.outDegree(i) );
		}
		
		assertEquals ( 0, dynamic.inDegree(BASE_NETWORK_SIZE));
		assertEquals ( 0, dynamic.outDegree(BASE_NETWORK_SIZE));
		assertEquals ( BASE_NETWORK_SIZE, dynamic.index(1000) );
	}

	@Test
	public void testAddLinks ()
	{
		for (int i=1; i<BASE_NETWORK_SIZE; i++)
			dynamic.add(i,0);
		
		assertEquals( BASE_NETWORK_SIZE, dynamic.size() );
		assertEquals( BASE_NETWORK_LINKS + (BASE_NETWORK_SIZE-1), dynamic.links() );
		
		assertEquals ( BASE_NETWORK_SIZE-1, dynamic.inDegree(0) );
		assertEquals ( BASE_NETWORK_SIZE-1, dynamic.outDegree(0) );
		
		for (int i=1; i<BASE_NETWORK_SIZE; i++) {
			assertEquals ( i, dynamic.inDegree(i) );
			assertEquals ( BASE_NETWORK_SIZE-i, dynamic.outDegree(i) );
		}
	}

	@Test
	public void testAddNodeWithLinks ()
	{
		dynamic.add(1000);

		for (int i=0; i<BASE_NETWORK_SIZE; i++) {
			dynamic.add(BASE_NETWORK_SIZE, i);
			dynamic.add(i, BASE_NETWORK_SIZE);
		}
		
		assertEquals( BASE_NETWORK_SIZE + 1, dynamic.size() );
		assertEquals( BASE_NETWORK_LINKS + 2*BASE_NETWORK_SIZE, dynamic.links() );
		
		for (int i=0; i<BASE_NETWORK_SIZE; i++) {
			assertEquals ( i+1, dynamic.inDegree(i) );
			assertEquals ( BASE_NETWORK_SIZE-i, dynamic.outDegree(i) );
		}
		
		assertEquals ( BASE_NETWORK_SIZE, dynamic.inDegree(BASE_NETWORK_SIZE));
		assertEquals ( BASE_NETWORK_SIZE, dynamic.outDegree(BASE_NETWORK_SIZE));
	}

	@Test
	public void testRemoveNodeWithOutLinks ()
	{
		dynamic.remove(0);
		
		assertEquals( BASE_NETWORK_SIZE-1, dynamic.size() );
		assertEquals( BASE_NETWORK_LINKS-(BASE_NETWORK_SIZE-1), dynamic.links() );
		
		for (int i=1; i<BASE_NETWORK_SIZE; i++) {
			assertEquals ( i-1, dynamic.inDegree(i-1) );
			assertEquals ( BASE_NETWORK_SIZE-i-1, dynamic.outDegree(i-1) );
		}
	}

	@Test
	public void testRemoveNodeWithInLinks ()
	{
		dynamic.remove(BASE_NETWORK_SIZE-1);
		
		assertEquals( BASE_NETWORK_SIZE-1, dynamic.size() );
		assertEquals( BASE_NETWORK_LINKS-(BASE_NETWORK_SIZE-1), dynamic.links() );
		
		for (int i=0; i<BASE_NETWORK_SIZE-1; i++) {
			assertEquals ( i, dynamic.inDegree(i) );
			assertEquals ( BASE_NETWORK_SIZE-i-2, dynamic.outDegree(i) );
		}
	}
	
	@Test
	public void testClear ()
	{
		dynamic.clear();

		assertEquals( 0, dynamic.size() );
		assertEquals( 0, dynamic.links() );
	}

	@Test
	public void testAddNodeAndRemove ()
	{
		dynamic.add(1000);

		for (int i=0; i<BASE_NETWORK_SIZE; i++) {
			dynamic.add(BASE_NETWORK_SIZE, i);
			dynamic.add(i, BASE_NETWORK_SIZE);
		}
		
		dynamic.remove(BASE_NETWORK_SIZE);

		assertEquals( BASE_NETWORK_SIZE, dynamic.size() );
		assertEquals( BASE_NETWORK_LINKS, dynamic.links() );
		
		for (int i=0; i<BASE_NETWORK_SIZE; i++) {
			assertEquals ( i, dynamic.inDegree(i) );
			assertEquals ( BASE_NETWORK_SIZE-i-1, dynamic.outDegree(i) );
		}
	}
	
	@Test
	public void testAddNodeAndClear ()
	{
		dynamic.add(1000);

		for (int i=0; i<BASE_NETWORK_SIZE; i++) {
			dynamic.add(BASE_NETWORK_SIZE, i);
			dynamic.add(i, BASE_NETWORK_SIZE);
		}
		
		dynamic.clear();

		assertEquals( 0, dynamic.size() );
		assertEquals( 0, dynamic.links() );
	}


	@Test
	public void testAddAndRemoveLinks ()
	{
		for (int i=1; i<BASE_NETWORK_SIZE; i++)
			dynamic.add(i,0);

		for (int i=1; i<BASE_NETWORK_SIZE; i++)
			dynamic.remove(i,0);
		
		dynamic.remove(BASE_NETWORK_SIZE);

		assertEquals( BASE_NETWORK_SIZE, dynamic.size() );
		assertEquals( BASE_NETWORK_LINKS, dynamic.links() );
		
		for (int i=0; i<BASE_NETWORK_SIZE; i++) {
			assertEquals ( i, dynamic.inDegree(i) );
			assertEquals ( BASE_NETWORK_SIZE-i-1, dynamic.outDegree(i) );
		}
	}

	@Test
	public void testRemoveFirstLinks ()
	{
		for (int i=0; i<BASE_NETWORK_SIZE-1; i++)
			dynamic.remove(i, i+1);
		
		assertEquals( BASE_NETWORK_SIZE, dynamic.size() );
		assertEquals( BASE_NETWORK_LINKS-(BASE_NETWORK_SIZE-1), dynamic.links() );
		
		assertEquals ( 0, dynamic.inDegree(0) );
		assertEquals ( BASE_NETWORK_SIZE-2, dynamic.outDegree(0) );

		for (int i=1; i<BASE_NETWORK_SIZE-1; i++) {
			assertEquals ( i-1, dynamic.inDegree(i) );
			assertEquals ( BASE_NETWORK_SIZE-i-2, dynamic.outDegree(i) );
		}

		assertEquals ( BASE_NETWORK_SIZE-2, dynamic.inDegree(BASE_NETWORK_SIZE-1) );
		assertEquals ( 0, dynamic.outDegree(BASE_NETWORK_SIZE-1) );
	}

	@Test
	public void testRemoveLastLinks ()
	{
		for (int i=0; i<BASE_NETWORK_SIZE-1; i++)
			dynamic.remove(i, BASE_NETWORK_SIZE-1);
		
		assertEquals( BASE_NETWORK_SIZE, dynamic.size() );
		assertEquals( BASE_NETWORK_LINKS-(BASE_NETWORK_SIZE-1), dynamic.links() );
		
		assertEquals ( 0, dynamic.inDegree(0) );
		assertEquals ( BASE_NETWORK_SIZE-2, dynamic.outDegree(0) );

		for (int i=1; i<BASE_NETWORK_SIZE-1; i++) {
			assertEquals ( i, dynamic.inDegree(i) );
			assertEquals ( BASE_NETWORK_SIZE-i-2, dynamic.outDegree(i) );
		}

		assertEquals ( 0, dynamic.inDegree(BASE_NETWORK_SIZE-1) );
		assertEquals ( 0, dynamic.outDegree(BASE_NETWORK_SIZE-1) );
	}
	
}
