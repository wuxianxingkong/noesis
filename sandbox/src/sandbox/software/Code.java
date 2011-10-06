package sandbox.software;

//Title:       Data module
//Version:     1.0
//Copyright:   2006
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

import ikor.collection.DynamicList;
import ikor.collection.List;
import ikor.util.xml.XmlWriter;


public class Code extends Module {

	private DynamicList<Proxy> exceptions;
	
	public Code ()
	{
		exceptions = null;
	}


	public List<Proxy> getExceptions() {
		return new DynamicList<Proxy>(exceptions);
	}
	
	public Proxy getException (int i)
	{
		return exceptions.get(i);
	}
	public int getExceptionCount ()
	{
		if (exceptions!=null)
			return exceptions.size();
		else
			return 0;
	}
	public void addException (Proxy exception)
	{
		if (exceptions==null)
			exceptions = new DynamicList<Proxy>();
		
		exceptions.add(exception);
	}
	
	public void addException (String type) {
		
		addException ( new ModuleProxy(Type.class, type) );
	}
	
	public void removeException (Proxy exception)
	{
		exceptions.remove(exception);
	}	
	
	@Override
	protected void outputLocalInfo (XmlWriter writer)
	{
		super.outputLocalInfo(writer);
		
		if ((exceptions!=null) && (exceptions.size()>0)) {
			writer.start("throws");
			outputCollection(writer,exceptions);
			writer.end("throws");
		}		
	}	
	
}
