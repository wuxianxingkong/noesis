package sandbox.software;

import ikor.util.xml.XmlWriter;

// Title:       Data module
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

public class Data extends Module {

	private TypeProxy type;
	
	public Data ()
	{
	}

	public Proxy getType() {
		return type;
	}

	public void setType(TypeProxy type) {
		this.type = type;
	}
	
	public void setType(String type) {
		
		this.type = new TypeProxy(type);
	}
	
	@Override
	protected void outputLocalInfo (XmlWriter writer)
	{
		super.outputLocalInfo(writer);
		// writer.writeAttribute("Type", type.getID());
		
		if (type!=null)
			type.output(writer);
	}	
	
	protected String outputID ()
	{
		return "Data";
	}	
}
