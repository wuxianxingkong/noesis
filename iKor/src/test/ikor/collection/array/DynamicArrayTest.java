package test.ikor.collection.array;

import ikor.collection.array.Array;
import ikor.collection.array.DynamicArray;


public class DynamicArrayTest extends ArrayTest
{
	@Override
	public Array createArray() 
	{
		return new DynamicArray();
	}

}
