package sandbox.software;

//Title:       Value module 
//Version:     1.0
//Copyright:   2006
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

import ikor.util.xml.XmlWriter;

/**
 * Values (constants, annotation parameters, access flags...)
 * 
 * @author Fernando Berzal
 *
 */
public class Value extends Data {

	private Object value;
	
	public Value ()
	{
	}
	
	public Object getValue() {
		return value;
	}
	
	public Module getReference() {
		
		if ((value!=null) && (value instanceof Module))
			return (Module)value;
		else
			return null;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	@Override
	protected void outputLocalInfo (XmlWriter writer)
	{
		String key = "Value";
		
		super.outputLocalInfo(writer);			
		
		if (getType()!=null) {
			writer.start(key);
			writer.write(value);		
			writer.end(key);
		} else {
			writer.write(key, value);
		}
	}	
	

	
}
