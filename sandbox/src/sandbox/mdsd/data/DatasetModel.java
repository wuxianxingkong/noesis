package sandbox.mdsd.data;


public class DatasetModel extends ikor.collection.DynamicList<DataModel> implements DataModel<Dataset> 
{
	@Override
	public String toString(Dataset object) 
	{
		return object.toString();
	}

	@Override
	public Dataset fromString(String string) 
	{
		throw new UnsupportedOperationException();
	}
}
