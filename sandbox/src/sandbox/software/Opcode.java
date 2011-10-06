package sandbox.software;

public class Opcode 
{
	private int    code;
	private String id;
	
	public Opcode ()
	{
	}
	
	public Opcode (int code, String id)
	{
		this.id = id;
	}

	
	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}
}
