package sandbox.mdsd.ui;

import sandbox.mdsd.data.DataModel;
import sandbox.mdsd.data.DataModelFactory;

public class Viewer<T> extends DataComponent<T> 
{
	public Viewer (String id, DataModel<T> model)
	{
		super(id, model);
	}
	
	public Viewer (String id, Class type)
	{
		this(id, DataModelFactory.create(type));
	}
}
