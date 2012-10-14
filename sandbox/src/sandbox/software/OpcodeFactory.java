package sandbox.software;

import java.io.*;
import javax.xml.stream.*;

import ikor.collection.*;


public class OpcodeFactory 
{
	Dictionary<Integer,Opcode>  codes;
	Dictionary<String,Opcode>   opcodes;
	
	/**
	 * Opcode factory for a given platform
	 * @param platform Software platform (e.g. jvm, clr)
	 */
	public OpcodeFactory (String platform)
	{
		codes = new DynamicDictionary<Integer,Opcode>();
		opcodes = new DynamicDictionary<String,Opcode>();
		
		load(platform);
	}
	
	// Data load
	
	private void addOpcode (Opcode opcode)
	{
		codes.set( opcode.getCode(), opcode);
		opcodes.set( opcode.getID(), opcode);
	}
	
	private void load (String platform)
	{
		InputStream     in;
		XMLInputFactory factory;
		XMLStreamReader parser;
		String          label;
		Opcode          opcode = null;
		String          text;
		int             code;
		
	    try {
	        in = getClass().getResourceAsStream("/sandbox/software/opcodes/"+platform+".xml");
	        factory = XMLInputFactory.newInstance();
	        parser = factory.createXMLStreamReader(in);
	          	     
	        for (int event = parser.next();  
	         	event != XMLStreamConstants.END_DOCUMENT;
	         	event = parser.next()) {
	        	
	        	switch (event) {
	        	
	            	case XMLStreamConstants.START_ELEMENT:
	            		label = parser.getLocalName();
	            		
	            		if (label.equals("instruction")) {
	            			
	            			opcode = new Opcode();
	            			
	            		} else if (label.equals("hex")) {
	            			
	            			text = parser.getElementText();
	            			code = Integer.parseInt(text,16);
	            			opcode.setCode(code);
	            			
	            		} else if (label.equals("opcode")) {
	            			
	            			opcode.setID( parser.getElementText() );
	            		}
	            		break;
	            		
	            	case XMLStreamConstants.END_ELEMENT:            		
	            		label = parser.getLocalName();
	            		
	            		if (label.equals("instruction"))
	            			addOpcode(opcode);
	            		
	            		break;
	            		
	            	case XMLStreamConstants.CHARACTERS:
	            		break;
	            		
	            	case XMLStreamConstants.CDATA:
	            		break;
	          }
	        }
	        
	        parser.close();
	      
	      } catch (Exception error) {
	    	  
	        System.err.println("Exception while parsing opcodes for '" + platform +"'");
	        System.err.println(error);
	        error.printStackTrace();
	      }
		
	}
	
	// Query
	
	public Opcode get (int code)
	{
		return codes.get(code);
	}
	
	public Opcode get (String opcode)
	{
		return opcodes.get(opcode);
	}
	
	public static Opcode getOpcode (String platform, int code)
	{
		OpcodeFactory factory = getOpcodeFactory(platform);
		Opcode        opcode = null;
		
		if (factory!=null)
			opcode = factory.get(code);
		
		if (opcode==null)
			opcode = new Opcode(code,platform+"#"+code);
		
		return opcode;
	}
	
	// Factories
	
	private static DynamicDictionary<String,OpcodeFactory> factories = new DynamicDictionary<String,OpcodeFactory>();
	
	public static OpcodeFactory getOpcodeFactory(String platform)
	{
		OpcodeFactory factory;
		
		factory = factories.get(platform);
		
		if (factory == null) {
			factory = new OpcodeFactory(platform);
			factories.set(platform, factory);
		}
		
		return factory;
	}

}
