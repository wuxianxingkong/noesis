package ikor.util.xml;

/**
 * @author Fernando Berzal
 *
 */
public class MemoryXmlWriter implements XmlWriter {

	private StringBuffer buffer;
	private String       prefix;
	
	private String LEVEL_PREFIX = "  ";
	
	public MemoryXmlWriter ()
	{
		buffer = new StringBuffer();
		prefix = "";
	}
	
	public String toString ()
	{
		return buffer.toString();
	}
	
	public void close() {
		// Nothing to do
	}


	public void write(Object object) {
		buffer.append(prefix+object+"\n");
		
	}
	
	public void write(String id, Object attribute) {
		buffer.append(prefix+"- "+id+": "+attribute+"\n");

	}

	public void start(String id) {
		
		buffer.append(prefix+id+"\n");
		prefix += LEVEL_PREFIX;

	}

	public void end(String id) {
		
		if (prefix.length()>LEVEL_PREFIX.length())
			prefix = prefix.substring(0, prefix.length()-LEVEL_PREFIX.length());

	}
	
	public void comment (String comment)
	{
		buffer.append(prefix+" /* "+comment+" */");
	}
}
