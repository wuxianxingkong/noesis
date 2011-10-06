package sandbox.software;

import ikor.util.xml.XmlWriter;

public class FieldProxy extends DataProxy 
{
	private TypeProxy owner;
	
	
	public FieldProxy (TypeProxy owner, String name)
	{
		super(name);
		
		this.owner = owner;	
	}
	
	public TypeProxy getOwner ()
	{
		return owner;
	}
	
	
	@Override
	public final void output (XmlWriter writer)
	{
		String elementID = "field"; 
	
		writer.start(elementID);
		
		writer.write("ID", getID());
		writer.write("owner",owner.getID());
		
		writer.end(elementID);
	}	

}
