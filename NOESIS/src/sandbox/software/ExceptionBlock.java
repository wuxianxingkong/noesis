package sandbox.software;

import ikor.util.xml.XmlWriter;

public class ExceptionBlock extends Block 
{
	private TypeProxy type;    // Null for finally blocks
	
	private String    start;   // Scope
	private String    end;
	
	private String    handler; // Exception handler
	
	
	public ExceptionBlock ()
	{
	}

	
	public Proxy getType() {
		return type;
	}

	public void setType(TypeProxy type) {
		this.type = type;
	}
	
	public void setType(String type) {
		
		this.type = new TypeProxy(type);
	}


	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getStart() {
		return start;
	}
	
	@Override
	protected void outputLocalInfo (XmlWriter writer)
	{
		if (handler!=null)
			writer.write("handler", handler);

		if (start!=null)
			writer.write("start", start);
		
		if (end!=null)
			writer.write("end", end);
		
		if (type!=null)
			type.output(writer);
		
		super.outputLocalInfo(writer);
	}	
	
	protected String outputID ()
	{
		return "Exception";
	}

	public void setStart(String start) {
		this.start = start;
	}	
}
