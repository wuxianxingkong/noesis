package sandbox.software;

//Title:       Type module
//Version:     1.0
//Copyright:   2006
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

import ikor.collection.*;
import ikor.util.xml.XmlWriter;


public class Type extends Module {

	/**
	 * Extended base class and implemented interfaces
	 */
	private List<Proxy> superclasses;      // Collection<Proxy<Type>>
	
	/**
	 * Type parameters (e.g. generics & arrays)
	 */
	private List<Proxy> parameters;
		
	/**
	 * Constructor
	 * @param key Fully-qualified type name
	 */
	public Type ()
	{
		superclasses = new DynamicList<Proxy>();
		parameters =  new DynamicList<Proxy>();
	}
	
	// Extended classes
	
	public ReadOnlyCollection<Proxy> getSuperclasses() {
		return new DynamicList<Proxy>(superclasses);
	}
	public Proxy getSuperclass (int i)
	{
		return superclasses.get(i);
	}
	public int getSuperclassCount ()
	{
		return superclasses.size();
	}
	public void addSuperclass (Proxy superclass)
	{
		superclasses.add(superclass);
	}
	
	public void addSuperclass (String type) {
		
		ModuleProxy proxy = new ModuleProxy(Type.class, type);
		
		superclasses.add ( proxy );
	}
	
	public void removeSuperclass(Proxy superclass)
	{
		superclasses.remove(superclass);
	}	
	
	
	// Parameters
	
	public ReadOnlyCollection<Proxy> getParameters() {
		return new DynamicList<Proxy>(parameters);
	}
	public Proxy getParameter (int i)
	{
		return parameters.get(i);
	}
	public int getParameterCount ()
	{
		return parameters.size();
	}
	public void addParameter (Proxy parameter)
	{
		parameters.add(parameter);
	}
	
	public void addParameter (String type) 
	{		
		ModuleProxy proxy = new ModuleProxy(Type.class, type);
		
		parameters.add ( proxy );
	}
	
	public void removeParameter(Proxy parameter)
	{
		parameters.remove(parameter);
	}		
	
	
	// Proxy
	
	@Override
	public Proxy getProxy() {

		return new TypeProxy(this);
	}	
	
	// Standard output
	
	@Override
	protected void outputLocalInfo (XmlWriter writer)
	{
		super.outputLocalInfo(writer);
		
		if (superclasses.size()>0) {
			writer.start("Superclasses");
			outputCollection(writer,superclasses);
			writer.end("Superclasses");
		}
		
		if (parameters.size()>0) {
			writer.start("Parameters");
			outputCollection(writer,parameters);
			writer.end("Parameters");
		}		
	}

}
