package sandbox.mdsd.data;


public class Record extends Dataset
{
	private Dataset dataset;
	private int index;
	
	public Record (Dataset dataset, int index)
	{
		this.dataset = dataset;
		this.index = index;
	}

	public Dataset getDataset() 
	{
		return dataset;
	}

	public int getIndex() 
	{
		return index;
	}
	
	// Record fields
	
	public Object getField (int field)
	{
		return dataset.get(index,field);
	}
	
	// Dataset interface

	@Override
	public Object get(int row, int column) 
	{
		if (row==0)
			return dataset.get(index, column);
		else
			return null;
	}

	@Override
	public void set(int row, int column, Object object) 
	{
		if (row==0)
			dataset.set(index,column,object);
	}
	
	@Override
	public int getColumnCount() 
	{
		return dataset.getColumnCount();
	}

	@Override
	public int getRowCount() 
	{
		return 1;
	}

	@Override
	public DataModel getModel(int column) 
	{
		return dataset.getModel(column);
	}
}
