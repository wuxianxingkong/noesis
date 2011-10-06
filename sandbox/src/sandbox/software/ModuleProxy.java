package sandbox.software;

import ikor.util.xml.XmlWriter;

public class ModuleProxy extends Proxy {

	private Class  type;
	private String id;
	
	
	public ModuleProxy (Module module)
	{
		super(module);
		
		this.type = module.getClass();
		this.id = module.getID();
	}	
	
	protected ModuleProxy (Class type, String id)
	{
		this.type = type;
		this.id   = id;
	}
	
	
	public String getID() {
		return id;
	}
	
	public Class getType() {
		return type;
	}
	
	public String toString ()
	{	
		return type.getName() + " " + id;
	}
	
	@Override
	public void output (XmlWriter writer)
	{
		if (getElement()!=null) {
			
			getElement().output(writer);
			
		} else {
			
			String elementID = type.getSimpleName();
	
			writer.start(elementID);
		
			if (id!=null)
				writer.write("ID", id);		
		
			writer.end(elementID);
		}
	}
	

	
}
