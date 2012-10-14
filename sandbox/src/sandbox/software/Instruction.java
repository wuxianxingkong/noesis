package sandbox.software;


import ikor.collection.ReadOnlyCollection;
import ikor.collection.DynamicDictionary;
import ikor.util.xml.XmlWriter;

// Title:       Instruction
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

public class Instruction extends Code {
	
	private String      label;
	
	private Opcode      opcode;
	
	// Operands
	
	private Object      operand;
	private ModuleProxy reference;
	
	// switch-like instructions
	
	private DynamicDictionary<Integer,String> targets;
	
	/**
	 * Constructor
	 * @param platform Software platform
	 * @param code Instruction code
	 */
	
	public Instruction (String platform, int code)
	{
		setOpcode(platform,code);
	}

	// Setters & getters
	
	public Opcode getOpcode() {
		return opcode;
	}

	public void setOpcode(Opcode opcode) {
		this.opcode = opcode;
	}

	public void setOpcode(String platform, int code)
	{
		setOpcode ( OpcodeFactory.getOpcode(platform, code) );
	}	
	

	public ModuleProxy getReference() {
		return reference;
	}

	public void setReference(ModuleProxy reference) {
		this.reference = reference;
	}

	public Object getOperand() {
		return operand;
	}

	public void setOperand(Object operand) {
		this.operand = operand;
	}
	

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}		
	
	// Jump targets
	
	public String getTarget (int key)
	{
		return targets.get(key);
	}
	public int getTargetCount ()
	{
		if (targets!=null)
			return targets.size();
		else
			return 0;
	}
	public void addTarget (int key, String target)
	{
		if (targets==null)
			targets = new DynamicDictionary<Integer,String>();
		
		targets.set(key, target);
	}
	
	public void removeTarget (int key)
	{
		targets.remove(key);
	}		
	
	// Output
	
	@Override
	protected void outputLocalInfo (XmlWriter writer)
	{
		super.outputLocalInfo(writer);
		
		// Opcode
		
		if (opcode!=null)
			writer.write("code",opcode.getID());

		// Label
		
		if (label!=null)
			writer.write("label", label);
		
		// Immediate operands
		
		if (reference!=null)
			reference.output(writer);
			
		if ((targets==null) && (operand!=null)) 
			writer.write(operand);

		
		// Switch-like instructions
		
		if ((targets!=null) && (targets.size()>0))  {
			
			ReadOnlyCollection<Integer> labels = targets.keys();
						
			for (int key: labels) {
				writer.start("target");
				writer.write("key", key);
				writer.write( targets.get(key) );
				writer.end("target");
			}
			
			if (operand!=null) {
				writer.start("default");
				writer.write(operand);
				writer.end("default");
			}
			
		}
	}	
	
	@Override
	protected String outputID ()
	{
		return "op";
	}
	

}
