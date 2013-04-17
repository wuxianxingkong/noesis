package noesis.io.graphics;

import ikor.collection.Dictionary;
import ikor.collection.DynamicDictionary;


public class DictionaryIndexer<T> extends Indexer<T> 
{
	private Dictionary<T, Integer> dictionary = new DynamicDictionary<T,Integer>();
	
	public void add (T value, int index)
	{
		dictionary.set(value,index);
	}

	@Override
	public int index (T value) 
	{
		Integer index = dictionary.get(value);
		
		if (index!=null)
			return index;
		else
			return 0;
	}

}
