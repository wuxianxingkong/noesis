package sandbox.software;

import ikor.util.xml.XmlWriter;

public class LocalProxy extends DataProxy {
	
	private int   index;
	
	public LocalProxy (int index)
	{
		super ();
		
		this.index = index;
	}
	
	public Data resolve (Block block)
	{
		Data local = block.getLocal(index);
		
		this.setElement(local);
		
		return local;
	}
	
	
	@Override
	public void output (XmlWriter writer)
	{
		if (getElement()!=null) {
			
			getElement().output(writer);
			
		} else {
			
			writer.write(index);		
		}
	}

}
