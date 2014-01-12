package test.noesis;

import noesis.DynamicNetwork;


public class SampleDynamicNetworkTest extends SampleNetworkTest 
{
	@Override
	public Class networkClass() 
	{
		return DynamicNetwork.class;
	}

}
