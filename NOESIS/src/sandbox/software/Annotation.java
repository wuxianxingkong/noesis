package sandbox.software;

import ikor.util.xml.XmlWriter;

// Title:       Annotation module
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

public class Annotation extends Module {

	private ModuleProxy type;
	
	public Annotation ()
	{
		type = null;
	}
	
	public Annotation (String id)
	{
		setID(id);
	}

	public Proxy getType() {
		return type;
	}

	public void setType(ModuleProxy type) {
		this.type = type;
	}
	
	public void setType(String type) {
		
		this.type = new ModuleProxy(Type.class, type);
	}
	
	// Output
	
	@Override
	protected void outputLocalInfo (XmlWriter writer)
	{
		super.outputLocalInfo(writer);
		
		if (type!=null)
			type.output(writer);
	}	
	
	protected String outputID ()
	{
		return "Meta";
	}		
}
