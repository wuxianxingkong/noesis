package test.noesis;

import org.junit.Test;

import noesis.ArrayNetwork;


public class SampleArrayNetworkTest extends SampleNetworkTest 
{
	@Override
	public Class networkClass() 
	{
		return ArrayNetwork.class;
	}
	
	@Override
	@Test(expected=UnsupportedOperationException.class)
	public void testWebgraphNodeRemoval ()
	{
		super.testWebgraphNodeRemoval();
	}
	
	@Override
	@Test(expected=UnsupportedOperationException.class)
	public void testWebgraphRootNodeRemoval ()
	{
		super.testWebgraphRootNodeRemoval();
	}

	@Override
	@Test(expected=UnsupportedOperationException.class)
	public void testWebgraphOutNodeRemoval ()
	{
		super.testWebgraphOutNodeRemoval();
	}
	
	@Override
	@Test(expected=UnsupportedOperationException.class)
	public void testWebgraphClear ()
	{
		super.testWebgraphClear();
	}

}
