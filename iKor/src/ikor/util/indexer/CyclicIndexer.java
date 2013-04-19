package ikor.util.indexer;

public class CyclicIndexer extends Indexer<Integer> 
{
	private int size;
	
	public CyclicIndexer (int size)
	{
		this.size = size;
	}

	@Override
	public int index(Integer value) 
	{
		return value%size;
	}

	
	@Override
	public int min() 
	{
		return 0;
	}

	@Override
	public int max() 
	{
		return size-1;
	}

}
