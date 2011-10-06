package sandbox.software.jvm;

import sandbox.software.Module;
import sandbox.software.Value;

import org.objectweb.asm.*;


/**
 * Annotation visitor for ASM
 * 
 * @author Fernando Berzal
 */
class AsmAnnotationVisitor extends AsmVisitor implements AnnotationVisitor {

	public AsmAnnotationVisitor(Module module) {
		super(module);
	}

	public void visit(String name, Object value) {
		
		Value  annotation = new Value();
		String typeName = value.getClass().getCanonicalName();
		
		annotation.setID(name);
		annotation.setType( typeName );
		annotation.setValue( value );
		
		this.module.add(annotation);
		
		// System.out.println(" - " + name +" ("+typeName+"): "+value);
	}
	
	/**
	 * Nested annotation
	 */

	public AnnotationVisitor visitAnnotation(String name, String desc) {
		// PPD Nested annotations (?)
		return null;
	}

	public AnnotationVisitor visitArray(String name) {
		// Nothing to do	
		return null;
	}

	public void visitEnum(String name, String desc, String value) {
		// Nothing to do (?)
	}

}

