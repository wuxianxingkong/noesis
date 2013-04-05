package sandbox.mdsd.ui;

import sandbox.mdsd.Subject;
import sandbox.mdsd.data.DataModel;
import sandbox.mdsd.data.DataModelFactory;


public class Editor<T> extends DataComponent<T> 
{
	public Editor (String id, DataModel<T> model)
	{
		super(id,model);
	}
	
	public Editor (String id, Class type)
	{
		this(id, DataModelFactory.create(type));
	}
			

	public Editor (String id, Subject<T> subject, DataModel<T> model)
	{
		super(id,model);
		subject.addObserver(this);
		this.addObserver(subject);
	}

	public Editor (String id, Subject<T> subject, Class type)
	{
		this(id, subject, DataModelFactory.create(type));
	}	
}
