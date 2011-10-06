package sandbox.software;

import ikor.util.xml.XmlWriter;

public class MethodProxy extends ModuleProxy 
{
	private TypeProxy owner;
	private TypeProxy args[];
	
	
	public MethodProxy (TypeProxy owner, String name, TypeProxy args[])
	{
		super(Method.class,name);
		
		this.owner = owner;
		this.args  = args;
	
	}
	
	public TypeProxy getOwner ()
	{
		return owner;
	}
	
	public int getArgumentCount ()
	{
		return args.length;
	}
	
	public TypeProxy getArgument (int i)
	{
		return args[i];
	}
	
	@Override
	public final void output (XmlWriter writer)
	{
		String elementID = "signature"; 
	
		writer.start(elementID);
		
		writer.write("ID", getID());
		writer.write("owner",owner.getID());
		
		if ((args!=null) && (args.length>0)) {
			for (int i=0; i<args.length; i++)
				args[i].output(writer);
		}
		
		writer.end(elementID);
	}	

}
