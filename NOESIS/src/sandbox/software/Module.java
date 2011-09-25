package sandbox.software;

//Title:       Software module
//Version:     1.0
//Copyright:   2006
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

import ikor.collection.DynamicList;
import ikor.collection.List;
import ikor.util.xml.MemoryXmlWriter;
import ikor.util.xml.XmlWriter;




/**
 * Software module
 * 
 * @author Fernando Berzal
 *
 */
public abstract class Module extends Element {
	
	private String id;
	private DynamicList<Module> submodules;
	
	// Constructor 
	
	protected Module ()
	{
		submodules = new DynamicList<Module>();
	}
	
	// Module ID
	
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	
	// Submodules

	protected List<Module> get (Class type) {
		return submodules.get(type);
	}

	public Module get (int i)
	{
		return submodules.get(i);
	}
	public int size ()
	{
		return submodules.size();
	}
	public void add (Module sub)
	{
		submodules.add(sub);
	}
	public void remove (Module sub)
	{
		submodules.remove(sub);
	}
	
	// Standard output
	
	public final String toString ()
	{
		MemoryXmlWriter buffer = new MemoryXmlWriter();
		
		output(buffer);
		
		return buffer.toString();		
	}
	
	public final void output (XmlWriter writer)
	{
		String elementID = outputID(); 
		
		writer.start(elementID);
		
		outputLocalInfo(writer);
		outputCollection(writer,submodules);
		
		writer.end(elementID);
	}
	
	protected void outputLocalInfo (XmlWriter writer)
	{
		if (id!=null)
			writer.write("ID", id);
	}
	
	protected String outputID ()
	{
		return this.getClass().getSimpleName();
	}
	

	// Proxy
	
	@Override
	public Proxy getProxy() {

		return new ModuleProxy(this);
	}

}
