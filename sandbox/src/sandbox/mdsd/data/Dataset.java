package sandbox.mdsd.data;

import java.util.Iterator;

import ikor.collection.ReadOnlyList;


public abstract class Dataset implements ReadOnlyList<Record> 
{
	// Data
	
	public abstract Object get (int row, int column);

	public abstract void set (int row, int column, Object object);
	
	// Dataset dimensions
	
	public abstract int getColumnCount ();

	public abstract int getRowCount ();
	
	// Data model
	
	public abstract DataModel getModel (int column);
	
	public final DatasetModel getModel ()
	{
		DatasetModel model = new DatasetModel();
		
		for (int i=0; i<getColumnCount(); i++)
			model.add( getModel(i) );
		
		return model;
	}
	
	// Single row/column datasets
	
	public Column getColumn (int index)
	{
		if ((index>=0) && (index<getColumnCount()))
			return new Column(this,index);
		else
			return null;
	}
	
	public Record getRow (int index)
	{
		if ((index>=0) && (index<getRowCount()))
			return new Record(this,index);
		else
			return null;
	}
	
	// toString
	
	public String toString ()
	{
		return getRowCount()+"x"+getColumnCount()+" dataset";
	}

	// List interface
	
	@Override
	public final Record get (int index) 
	{
		return getRow(index);
	}

	@Override
	public final int index (Record object) 
	{
		if (object.getDataset()==this)
			return object.getIndex();
		else
			return -1;
	}
	
	@Override
	public final int size()
	{
		return getRowCount();
	}


	@Override
	public boolean contains(Record object) 
	{
		return (object.getDataset()==this) && (object.getIndex()>=0) && (object.getIndex()<size());
	}

	@Override
	public Iterator<Record> iterator() 
	{
		return new DatasetIterator();
	}

	// Iterator
	
	public class DatasetIterator implements Iterator<Record> 
	{
		private int current = 0;
		
		@Override
		public boolean hasNext() 
		{
			return current < size();
		}

		@Override
		public Record next() 
		{
			return get(current++);
		}

		@Override
		public void remove() 
		{
			throw new UnsupportedOperationException("Unsupported dataset removal through iterator");
		}
	}
	
	
	// Single column dataset
	
	public class Column extends Dataset 
	{
		Dataset dataset;
		int     index;
		
		public Column (Dataset dataset, int index)
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
		
		@Override
		public Object get(int row, int column) 
		{
			if (column==0)
				return dataset.get(row,index);
			else
				return null;
		}

		@Override
		public void set(int row, int column, Object object) 
		{
			if (column==0)
				dataset.set(row,index, object);			
		}
		
		@Override
		public int getColumnCount() 
		{
			return 1;
		}

		@Override
		public int getRowCount() 
		{
			return dataset.getRowCount();
		}

		@Override
		public DataModel getModel(int column) 
		{
			if (column==0)
				return dataset.getModel(index);
			else
				return null;
		}
	
	}

}
