package ikor.util.indexer;

import ikor.collection.Dictionary;
import ikor.collection.DynamicDictionary;


public class DictionaryIndexer<T> extends Indexer<T> 
{
	private Dictionary<T, Integer> dictionary = new DynamicDictionary<T,Integer>();
	private int max = -1;
	

	public void add (T value)
	{
		if (value!=null) {
			
			Integer entry = dictionary.get(value);
			
			if (entry==null) {
				dictionary.set(value,max+1);
				max++;
			}
		}
	}
	
	public void add (T value, int index)
	{
		if (value!=null) {
			dictionary.set(value,index);
			
			if (index>max)
				max = index;
		}
	}

	@Override
	public int index (T value) 
	{
		Integer index = null;
		
		if (value!=null)
			index = dictionary.get(value);
		
		if (index!=null)
			return index;
		else
			return 0;
	}

	@Override
	public int min() 
	{
		return 0;
	}

	@Override
	public int max() 
	{
		return max;
	}

}
