package sandbox.software.jvm;

import ikor.collection.*;


import sandbox.software.*;

public class AsmProvider implements ElementProvider {

	private DynamicDictionary<ModuleProxy,Type> cache;
	
	public AsmProvider ()
	{
		cache = new DynamicDictionary<ModuleProxy,Type>();
	}

	
	public Element getElement(Proxy proxy) {
		
		ModuleProxy module = (ModuleProxy) proxy; 
		Type        type;
		
		
		type = cache.get(module);
		
		if (type==null) {
			
			try {
			
				AsmReader reader = new AsmReader(module.getID());
			
				type = reader.getType();
			
				cache.set(module, type);
			
			} catch (Exception error) {
				
				
			}
		}
		
		return type;
	}
	
}

