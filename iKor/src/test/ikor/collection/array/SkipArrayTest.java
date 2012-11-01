package test.ikor.collection.array;

import ikor.collection.array.Array;
import ikor.collection.array.SkipArray;


public class SkipArrayTest extends ArrayTest
{
	@Override
	public Array createArray() 
	{
		return new SkipArray();
	}

}
