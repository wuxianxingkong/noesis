package sandbox.software.jvm;


import ikor.collection.*;

import sandbox.software.Module;
import sandbox.software.TypeProxy; 
import sandbox.software.Annotation;
import sandbox.software.Method;
import sandbox.software.MethodProxy;
import sandbox.software.Data;
import sandbox.software.Value;

import org.objectweb.asm.*;



/**
 * Class visitor for ASM
 * 
 * @author Fernando Berzal
 * 
 */
class AsmClassVisitor extends AsmVisitor implements ClassVisitor {

	public AsmClassVisitor(Module module) {
		super(module);
	}

	/**
	 * Class header
	 */

	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {

		String superclass;

		// Class name
		
		module.setID(getClassName(name));

		System.out.println("Class " + module.getID());

		// Superclasses

		if (superName != null) {
			
			superclass = getClassName(superName);		
			// System.out.println(" extends " + superclass);
			((sandbox.software.Type)module).addSuperclass(superclass);
		}

		if ((interfaces != null) && (interfaces.length > 0)) {
			// System.out.println(" implements");

			for (int i = 0; i < interfaces.length; i++) {
				superclass = getClassName(interfaces[i]);
				// System.out.println("  " + superclass);
				((sandbox.software.Type)module).addSuperclass(superclass);
			}
		}

		// Class version
		
		parseVersion(version);
			
		// Access flags

		parseFlags(this.module, access);

		// Signature

		parseSignature(this.module, signature);
	}
	
	
	/**
	 * Class version
	 * @param version Version code
	 */

	private void parseVersion (int version)
	{
		Value platform = new Value();
		platform.setID("platform");
		platform.setType("java.lang.String");
		platform.setValue("jvm");

		Value variant = new Value();
		variant.setID("version");
		variant.setType("java.lang.String");
		
		switch (version) {
		
			case Opcodes.V1_1:
				variant.setValue("1.1");
				break;
				
			case Opcodes.V1_2:
				variant.setValue("1.2");
				break;
				
			case Opcodes.V1_3:
				variant.setValue("1.3");
				break;
				
			case Opcodes.V1_4:
				variant.setValue("1.4");
				break;
				
			case Opcodes.V1_5:
				variant.setValue("5");
				break;
				
			case Opcodes.V1_6:
				variant.setValue("6");
				break;
				
			default:
				variant.setValue("#"+version);
		}
			
		
		Annotation annotation = new Annotation();
		annotation.setID("platform");
		annotation.add(platform);
		annotation.add(variant);
		
		module.add(annotation);

	}

	/**
	 * Flags
	 * 
	 * @param access
	 *            Access flags
	 */
	private void parseFlags(Module module, int access) {
		
		List<String> flags;
		
		if (module!=null) {
			
			flags = getFlags(access);
			for (int i=0; i<flags.size(); i++) {
				module.add( new Annotation(flags.get(i)));
			}
		}
	}
	

	/**
	 * Field
	 */

	public FieldVisitor visitField(int access, String name, String fieldDescriptor,
			String signature, Object value) {

		System.out.println("Field " + name);
		
		Data field;
		
		if (value==null) {
			field = new Data();
		} else {
			// Static field value (initial values and constants)
			field = new Value(); 
			((Value)field).setValue(value);
		}		
		
		field.setID(name);
		module.add(field);

		// Field descriptor (a.k.a. type)
		
		field.setType ( parseType ( Type.getType(fieldDescriptor)) );

		// Field signature (generics)
		
		parseSignature(field, signature);
		
		// Flags	
		
		parseFlags(field, access);

		// Field visitor for annotations and attributes

		FieldVisitor fv = new AsmFieldVisitor(field);

		return fv;
	}
	
	/**
	 * Inner class
	 */

	public void visitInnerClass(String name, String outerName,
			String innerName, int access) {

		String      id = getClassName(name);
		
		try {
			ClassReader reader = new ClassReader(id);
			ClassVisitor visitor = new AsmClassVisitor (new sandbox.software.Type());
			reader.accept(visitor, 0);
		} catch (Exception error) {
			System.err.println("ERROR - Parsing inner class: "+id);
			System.err.println(error);
		}
	}
	
	/**
	 * Method
	 */

	public MethodVisitor visitMethod(int access, String name, String methodDescriptor,
			String signature, String[] exceptions) {

		System.out.println("Method " + name + " - " + methodDescriptor);

		TypeProxy owner;	
		TypeProxy proxies[] = null;
		
		Method method =  new Method();
		Data   argument;
		
		// Enclosing type
		
		method.setID(name);
		this.module.add(method);

		owner = (TypeProxy) this.module.getProxy();
		owner.setElement(null); 

		
		// this (only for non-static methods)
		
		if  ((access & Opcodes.ACC_STATIC) == 0) {

			argument = new Data();
			argument.setID("this");
			argument.setType(owner);
			argument.add( new Annotation("parameter") );
				
			method.add(argument);
			
		}
		
		// Method descriptor: arguments + return type					
		
		Type arguments[] = Type.getArgumentTypes(methodDescriptor);
				
		if (arguments!=null) {
			
			proxies = new TypeProxy[arguments.length];
			
			for (int arg=0; arg<arguments.length; arg++) {
				
				proxies[arg] = parseType(arguments[arg]);
				
				argument = new Data();
				argument.setID("<arg>["+arg+"]");
				argument.setType(proxies[arg]);
				argument.add( new Annotation("parameter") );
					
				method.add(argument);
			}
		}
		
		Type returnType = Type.getReturnType(methodDescriptor);
		
		if (returnType.getSort()!=Type.VOID) {
			
			argument = new Data();			
			argument.setID("<return>");
			argument.setType( parseType(returnType) );
			argument.add( new Annotation("return") );
			
			method.add(argument);
		}
		
		method.setProxy( new MethodProxy ( owner, name, proxies ) );

		// Method signature (generics)
		
		parseSignature(method, signature);
		
		// Flags	
		
		parseFlags(method, access);
		

		// Exceptions

		if ((exceptions != null) && (exceptions.length > 0)) {
			
			String exception;

			// System.out.println(" throws");

			for (int i = 0; i < exceptions.length; i++) {
				exception = getClassName(exceptions[i]);
				// System.out.println("  " + exception);
				method.addException(exception);
			}
		}

		// Method visitor for code analysis

		MethodVisitor mv = new AsmMethodVisitor(method);

		return mv;
	}

	
	/**
	 * Outer class
	 */
	public void visitOuterClass(String owner, String name, String desc) {
		// Nothing to do
	}
	
	/**
	 * Source annotation
	 */
	public void visitSource(String source, String debug) {

		Value file = new Value();
		file.setID("file");
		file.setType("java.lang.String");
		file.setValue(source);
		
		Annotation annotation = new Annotation();
		annotation.setID("source");
		annotation.add(file);
		
		module.add(annotation);

		if (debug!=null) {
			Value extra = new Value();
			extra.setID("debug");
			extra.setType("java.lang.String");
			extra.setValue(debug);
			annotation.add(extra);
		}

	}


	/**
	 * Test program
	 */

	public static void main(String args[]) throws Exception {
		
		Module module = new sandbox.software.Type();
		String id = // "test.ikor.model.mock.Document";  // Annotations  
			        // "sandbox.test.software.jvm.mock.Methods"; // Methods
			        // "sandbox.test.software.jvm.mock.Fields"; // Fields
					// "ikor.collection.Collection"; // Generic type
					"sandbox.software.jvm.AsmClassVisitor"; // Complex class
		ClassReader reader = new ClassReader(id);
		ClassVisitor visitor = new AsmClassVisitor(module);

		reader.accept(visitor, 0);
		
		// System.out.println(module);
		
		ikor.util.xml.StaxXmlWriter xml = new ikor.util.xml.StaxXmlWriter(id+".xml");
		
		module.output(xml);
		
		xml.close();

	}

	public static void output(String name, List collection) {
		System.out.println(name + ":");

		for (int i = 0; i < collection.size(); i++)
			System.out.println(" - " + collection.get(i));

	}

}
