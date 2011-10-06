package sandbox.software.jvm;

import sandbox.software.Module;

import org.objectweb.asm.*;

/**
 * Field visitor for ASM
 * 
 * @author Fernando Berzal
 */

class AsmFieldVisitor extends AsmVisitor implements FieldVisitor {

	public AsmFieldVisitor(Module module) {
		super(module);
	}
}

