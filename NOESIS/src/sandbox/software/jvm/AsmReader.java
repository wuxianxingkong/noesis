package sandbox.software.jvm;


import sandbox.software.*;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

public class AsmReader {
	
	Type type;
	
	public AsmReader (String classID)
		throws IOException
	{
		ClassReader  reader;  
		ClassVisitor visitor; 
		int          flags;
		
		type = new Type();
		
		reader  = new ClassReader(classID);
		visitor = new AsmClassVisitor(type);
		flags   = 0;
		
		reader.accept(visitor, flags);
	}


	public Type getType () {
		return type;
	}
	
	

}
