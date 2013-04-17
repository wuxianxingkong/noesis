package noesis.io.graphics;

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

}
