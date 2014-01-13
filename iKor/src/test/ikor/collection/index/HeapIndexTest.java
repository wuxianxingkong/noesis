package test.ikor.collection.index;

import ikor.collection.index.Index;
import ikor.collection.index.HeapIndex;


public class HeapIndexTest extends IndexTest
{
	@Override
	public Index createIndex() 
	{
		return new HeapIndex();
	}

}
