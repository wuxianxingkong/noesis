package sandbox.mdsd.ui;

import sandbox.mdsd.Subject;
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

	public Viewer (String id, Subject<T> subject, DataModel<T> model)
	{
		super(id,model);
		subject.addObserver(this);
	}

	public Viewer (String id, Subject<T> subject, Class type)
	{
		this(id, subject, DataModelFactory.create(type));
	}
	
}
