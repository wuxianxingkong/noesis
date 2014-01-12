package test.ikor.collection.index;

import ikor.collection.index.Index;
import ikor.collection.index.ArrayIndex;


public class ArrayIndexTest extends IndexTest
{
	@Override
	public Index createArray() 
	{
		return new ArrayIndex();
	}

}
