package sandbox.software.jvm;


import ikor.collection.*;


import sandbox.software.Annotation;
import sandbox.software.Module;
import sandbox.software.Proxy;
import sandbox.software.TypeProxy;
import sandbox.software.Value;

import org.objectweb.asm.*;


/**
 * Generic visitor for ASM
 * 
 * @author Fernando Berzal
 */
class AsmVisitor
{
	
	protected Module module;

	public AsmVisitor(Module module) {
		this.module = module;
	}
	
	/**
	 * Gets a class name from its internal representation
	 * 
	 * @param asmClassName
	 *            Class name with /'s
	 * @return Class name with .'s instead of /'s
	 */

	protected String getClassName(String asmClassName) {
		return asmClassName.replace("/", ".");
	}

	/**
	 * Gets the collection of flags associated with a JVM access flags code
	 * 
	 * @param flags
	 *            Access flags code
	 * @return Collection of flags
	 */

	protected List<String> getFlags(int flags) {
		DynamicList<String> set = new DynamicList<String>();

		if ((flags & Opcodes.ACC_ABSTRACT) != 0)
			set.add("abstract");

		if ((flags & Opcodes.ACC_ANNOTATION) != 0)
			set.add("annotation");

		if ((flags & Opcodes.ACC_BRIDGE) != 0)
			set.add("bridge");

		if ((flags & Opcodes.ACC_DEPRECATED) != 0)
			set.add("deprecated");

		if ((flags & Opcodes.ACC_ENUM) != 0)
			set.add("enum");

		if ((flags & Opcodes.ACC_FINAL) != 0)
			set.add("final");

		if ((flags & Opcodes.ACC_INTERFACE) != 0)
			set.add("interface");

		if ((flags & Opcodes.ACC_NATIVE) != 0)
			set.add("native");

		if ((flags & Opcodes.ACC_PRIVATE) != 0)
			set.add("private");

		if ((flags & Opcodes.ACC_PROTECTED) != 0)
			set.add("protected");

		if ((flags & Opcodes.ACC_PUBLIC) != 0)
			set.add("public");

		if ((flags & Opcodes.ACC_STATIC) != 0)
			set.add("static");

		if ((flags & Opcodes.ACC_STRICT) != 0)
			set.add("strict");

		if ((flags & Opcodes.ACC_SUPER) != 0)
			set.add("super");

		if ((flags & Opcodes.ACC_SYNCHRONIZED) != 0)
			set.add("synchronized");

		if ((flags & Opcodes.ACC_SYNTHETIC) != 0)
			set.add("synthetic");

		if ((flags & Opcodes.ACC_TRANSIENT) != 0)
			set.add("transient");

		if ((flags & Opcodes.ACC_VARARGS) != 0)
			set.add("varargs");

		if ((flags & Opcodes.ACC_VOLATILE) != 0)
			set.add("volatile");

		return set;
	}

	
	/**
	 * Types
	 */
	
	protected TypeProxy parseType (Type type)
	{
		TypeProxy proxy = null;
		String    typeName = null;	
		
		switch (type.getSort()) {
		
			case Type.VOID:
				typeName = null;
				break;
				
			case Type.BOOLEAN:
				typeName = "java.lang.Boolean";
				break;
				
			case Type.CHAR:
				typeName = "java.lang.Char";
				break;
				
			case Type.BYTE:
				typeName = "java.lang.Byte";
				break;
				
			case Type.SHORT:
				typeName = "java.lang.Short";
				break;
				
			case Type.INT:
				typeName = "java.lang.Integer";
				break;
				
			case Type.FLOAT:
				typeName = "java.lang.Float";
				break;
				
			case Type.LONG:
				typeName = "java.lang.Long";
				break;
				
			case Type.DOUBLE:
				typeName = "java.lang.Double";
				break;
				
			case Type.ARRAY:
				typeName = "java.lang.Array";
				break;
				
			case Type.OBJECT:
				typeName = type.getClassName();
				break;
		}
		
		if (typeName!=null) {
			
			proxy = new TypeProxy(typeName);
			
			if (type.getSort()==Type.ARRAY) {
				
				sandbox.software.Type module = new sandbox.software.Type();
				module.setID(typeName); // == "java.lang.Array"
				
				Annotation annotation = new Annotation();
				annotation.setID("dimensions");
				
				Value dimensions = new Value();
				dimensions.setID("value");
				dimensions.setType("java.lang.Integer");
				dimensions.setValue(type.getDimensions());
				
				annotation.add(dimensions);
				module.add(annotation);
				
				Proxy element = parseType( type.getElementType() );									
				
				module.addParameter(element);
				
				
				proxy.setElement(module);
			}
		}
		
		return proxy;
	}
	

	/**
	 * Signatures (generics)
	 * 
	 * @param signature
	 *            String signature
	 */
	protected void parseSignature(Module module, String signature) {
		// PPD Signatures (for generics)
		if (signature!=null) {
		  
			System.out.println(" Signature "+signature);
		/*
		 * 
		 * SignatureReader sr = new SignatureReader(signature);
		 * SignatureVisitor sv = new AsmSignatureVisitor(this.module);
		 * 
		 * sr.accept(sv);
		 */
		 }
		 
	}
	
	
	/**
	 * Annotation
	 */

	public final AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
		
		Annotation annotation = new Annotation();

		annotation.setType ( parseType ( Type.getType(descriptor)) );

		this.module.add(annotation);
		
		// Is the annotation visible at runtime?
		
		if (visible) {
			Annotation runtime = new Annotation();
			runtime.setID("runtime");
			
			annotation.add(runtime);
		}

		return new AsmAnnotationVisitor(annotation);
	}

	/**
	 * Non-standard class, field, method, or code attribute
	 */

	public final void visitAttribute(Attribute attr) 
	{
		// PPD Non-standard attributes
		// System.out.println("Attribute: " + attr);

	}

	/**
	 * The End (of the visit)
	 */
	
	public void visitEnd() {
		// End of visit (nothing to do)

	}	

	
}

