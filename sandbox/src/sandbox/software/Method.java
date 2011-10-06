package sandbox.software;

import ikor.util.xml.XmlWriter;

public class Method extends Code {

	// Proxy
	
	private MethodProxy proxy;
	
	@Override
	public Proxy getProxy() {

		return proxy;
	}
	
	public void setProxy (MethodProxy proxy)
	{
		this.proxy = proxy;
	}
	
	
	// Output
	
	@Override
	protected void outputLocalInfo (XmlWriter writer)
	{
		super.outputLocalInfo(writer);
		
		if (proxy!=null)
			proxy.output(writer);
	}		
}
