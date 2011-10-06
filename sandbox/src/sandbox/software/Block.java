package sandbox.software;

import ikor.collection.List;
import ikor.collection.DynamicList;
import ikor.util.xml.XmlWriter;


public class Block extends Code 
{
	private DynamicList<Instruction> instructions;
	
	public Block ()
	{
		instructions = null;
	}

	/* Locals */
	
	public int getLocalCount ()
	{
    	int    i;
    	int    locals = 0;
    	
    	for (i=0; i<size(); i++)
    		if (get(i) instanceof Data)
    			locals++;
    	
    	return locals;
	}
	
	public Data getLocal (int i)
	{
    	int    index = 0;
    	int    local = -1;
    	Module module;
    	Data   result = null;  	
    	
    	while ((index<size()) || (result==null)) {
    		
    		module = get(index);
    		
    		if (module instanceof Data) {
    			local++;
    			
    			if (local==i)
    				result = (Data) module;
    		}
    		
    		index++;
    	}
    	
    	return result;
	}

	/* Instructions */

	public List<Instruction> getInstructions() {
		return new DynamicList<Instruction>(instructions);
	}
	public Instruction getInstruction (int i)
	{
		return instructions.get(i);
	}
	public int getInstructionCount ()
	{
		if (instructions!=null)
			return instructions.size();
		else
			return 0;
	}
	public void addInstruction (Instruction instruction)
	{
		if (instructions==null)
			instructions = new DynamicList<Instruction>();
		
		instructions.add(instruction);
	}
	
	public void addInstruction (String type) {
		
		addException ( new ModuleProxy(Type.class, type) );
	}
	
	public void removeInstruction (int i)
	{
		instructions.remove(i);
	}
	
	public void removeException (Instruction instruction)
	{
		instructions.remove(instruction);
	}	
	
	
	@Override
	protected void outputLocalInfo (XmlWriter writer)
	{
		super.outputLocalInfo(writer);
		
		if ((instructions!=null) && (instructions.size()>0)) {
			outputCollection(writer,instructions);
		}		
	}		
	

}
