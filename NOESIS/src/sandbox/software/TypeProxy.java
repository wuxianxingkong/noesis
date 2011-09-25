package sandbox.software;

public class TypeProxy extends ModuleProxy 
{
	public TypeProxy (Type type) {
		super(type);
	}
	public TypeProxy (String typeName)
	{
		super (Type.class, typeName);
	}

}
