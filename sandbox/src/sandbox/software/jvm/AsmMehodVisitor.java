package sandbox.software.jvm;

import ikor.collection.*;

import sandbox.software.Annotation;
import sandbox.software.Instruction;
import sandbox.software.Block;
import sandbox.software.ExceptionBlock;
import sandbox.software.Data;
import sandbox.software.Module;
import sandbox.software.Value;

import sandbox.software.FieldProxy;
import sandbox.software.LocalProxy;
import sandbox.software.MethodProxy;
import sandbox.software.TypeProxy;

import org.objectweb.asm.*;


/**
 * Method visitor for ASM.
 * 
 * A visitor to visit a Java method. 
 * The methods of this class are called in the following order:
 * - [ visitAnnotationDefault ] 
 * - ( visitAnnotation | visitParameterAnnotation | visitAttribute )* 
 * - [ visitCode 
 *       ( visitFrame 
 *       | visitXInsn 
 *       | visitLabel 
 *       | visitTryCatchBlock 
 *       | visitLocalVariable 
 *       | visitLineNumber )* visitMaxs ] 
 * - visitEnd. 
 * 
 * In addition, the visitXInsn and visitLabel methods 
 * must be called in the sequential order of the bytecode
 * instructions of the visited code, visitTryCatchBlock must be 
 * called before the labels passed as arguments have been visited, 
 * and the visitLocalVariable and visitLineNumber methods must be 
 * called after the labels passed as arguments have been visited.
 *  
 * @author Fernando Berzal
 */

class AsmMethodVisitor extends AsmVisitor implements MethodVisitor 
{

	Block block;
	
	Dictionary<Integer,Data>      locals;
	
	Label currentLabel;
	int   maxLocals;

	/* Constructor */
	
	public AsmMethodVisitor(Module module) {
		super(module);
	}


	/* Local references */
	
	private LocalProxy getLocalReference (int i)
	{		
		return new LocalProxy(i);
	}
	
	
	
	/* Annotations */

	public AnnotationVisitor visitAnnotationDefault() {
		// PPD Annotation defaults
		return null;
	}
	
	public AnnotationVisitor visitParameterAnnotation(int parameter,
			String desc, boolean visible) {
		// PPD Parameter annotations
		return null;
	}

	
	/* General visits */
	
	/**
	 * Start of visit
	 */

	public void visitCode() {	
		
		block = new Block();
		
		locals = new DynamicDictionary<Integer,Data>();		
		
		currentLabel = null;
	}
	
	/**
	 * End of visit
	 */
	
	public void visitEnd() {
		
		int         i;
		Data        data;
		
		// Local data
		
		for (i=0; i<maxLocals; i++) {
			
			data = locals.get(i);
			
			if (data==null) {
				data = new Data();
				data.setID("<local>["+i+"]");
			}
			
			block.add(data);
		}
		
		// Add block to current module
			
		this.module.add(block);
	}		



	public void visitFrame(int type, int nLocal, Object[] local, int nStack,
			Object[] stack) {
		// Current state of local variables and stack operands (nothing to do)
		/*
		int i;
		
		System.out.println("- Frame: "+nLocal+" locals / "+nStack+" stack");
		
		for (i=0; i<nLocal; i++) {
			System.out.println("    local "+i+": #"+ local[i]);
		}
		
		for (i=0; i<nStack; i++) {
			System.out.println("    stack "+i+": #"+ stack[i]);
		}
		*/
	}
	
	public void visitMaxs(int maxStack, int maxLocals) 
	{
		// Maximum stack size & maximum number of local variables 
		// System.out.println("- Max: "+maxLocals+" locals / "+maxStack+" stack");
		
		this.maxLocals = maxLocals;
	}
	
	
	/* Instructions */
	/* ------------ */
	
	private Instruction visitInstruction (int opcode)
	{
		Instruction op = new Instruction("jvm",opcode);
		
		if (currentLabel!=null) {
			op.setLabel(currentLabel.toString());
			currentLabel = null;
		}
		
		block.addInstruction(op);
			
		return op; 
	}
	
	
	
	
	

	public void visitFieldInsn(int opcode, String owner, String name, String typeDescriptor) 
	{
		// Opcodes GETSTATIC, PUTSTATIC, GETFIELD & PUTFIELD		
		// System.out.println(owner + " " + name + " " + typeDescriptor);
		
		Instruction op;
		TypeProxy   type; 
		FieldProxy  field;
		
		type = new TypeProxy ( getClassName(owner) );
		field = new FieldProxy( type, name );
		
		op = visitInstruction(opcode);
		op.setReference(field);
	}

	public void visitInsn(int opcode) 
	{
		// Zero-operand opcodes
		// --------------------
		// NOP, ACONST_NULL, 
		// ICONST_M1, ICONST_0, ICONST_1, ICONST_2, ICONST_3, ICONST_4, ICONST_5,
		// LCONST_0, LCONST_1, FCONST_0, FCONST_1, FCONST_2, DCONST_0, DCONST_1, 
		// IALOAD, LALOAD, FALOAD, DALOAD, AALOAD, BALOAD, CALOAD, SALOAD, 
		// IASTORE, LASTORE, FASTORE, DASTORE, AASTORE, BASTORE, CASTORE, SASTORE,
		// POP, POP2, DUP, DUP_X1, DUP_X2, DUP2, DUP2_X1, DUP2_X2,
		// SWAP, IADD, LADD, FADD, DADD, ISUB, LSUB, FSUB, DSUB, 
		// IMUL, LMUL, FMUL, DMUL, IDIV, LDIV, FDIV, DDIV, IREM, LREM, FREM, DREM,
		// INEG, LNEG, FNEG, DNEG, ISHL, LSHL, ISHR, LSHR, IUSHR, LUSHR, 
		// IAND, LAND, IOR, LOR, IXOR, LXOR,
		// I2L, I2F, I2D, L2I, L2F, L2D, F2I, F2L, F2D, D2I, D2L, D2F, I2B, I2C, I2S,
		// LCMP, FCMPL, FCMPG, DCMPL, DCMPG,
		// IRETURN, LRETURN, FRETURN, DRETURN, ARETURN, RETURN,
		// ARRAYLENGTH, ATHROW, MONITORENTER, or MONITOREXIT
		visitInstruction(opcode);
	}


	public void visitIntInsn(int opcode, int operand) 
	{
		// Single-int operand instruction:
		// Opcodes BIPUSH, SIPUSH, or NEWARRAY
		
		Instruction op;
		
		op = visitInstruction(opcode);
		op.setOperand(operand);
	}


	public void visitLdcInsn(Object cst) 
	{
		// Opcode LDC
		Instruction op;
		
		op = visitInstruction(Opcodes.LDC);
		op.setOperand(cst);
	}

	public void visitTypeInsn(int opcode, String descriptor) 
	{
		// Opcodes NEW, ANEWARRAY, CHECKCAST or INSTANCEOF
		
		Instruction op;
		TypeProxy   type; 
		
		type = new TypeProxy ( getClassName(descriptor) );
		
		op = visitInstruction(opcode);
		op.setReference(type);		
	}

	public void visitMultiANewArrayInsn(String desc, int dims)
	{
		// Opcode MULTIANEWARRAY
		
		Instruction op;
		
		op = visitInstruction(Opcodes.MULTIANEWARRAY);
		op.setOperand(dims);		
	}
	
	/* Jump instructions */

	public void visitJumpInsn(int opcode, Label label) 
	{
		// IFEQ, IFNE, IFLT, IFGE, IFGT, IFLE,
		// IF_ICMPEQ, IF_ICMPNE, IF_ICMPLT, IF_ICMPGE, IF_ICMPGT, IF_ICMPLE,
		// IF_ACMPEQ, IF_ACMPNE, GOTO, JSR, IFNULL or IFNONNULL
		
		Instruction op =visitInstruction(opcode);
		
		op.setOperand(label.toString());

	}

	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) 
	{
		// Opcode LOOKUPSWITCH
		
		Instruction op = visitInstruction(Opcodes.LOOKUPSWITCH);
		 
		if (dflt!=null)
			op.setOperand(dflt);
		 
		for (int i=0; i<labels.length; i++) {
			op.addTarget(keys[i], labels[i].toString());
		}
	}

	public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) 
	{
		// Opcode TABLESWITCH
		
		Instruction op = visitInstruction(Opcodes.TABLESWITCH);
		 
		if (dflt!=null)
			op.setOperand(dflt);
		 
		for (int i=0; i<labels.length; i++) {
			op.addTarget(min+i, labels[i].toString());
		}
	}
	
	/* Calls */
	
	private TypeProxy[] parseMethodArguments (String methodDescriptor)
	{
		Type       arguments[] = Type.getArgumentTypes(methodDescriptor);
		TypeProxy  proxies[] = new TypeProxy[arguments.length];
		
		for (int i=0; i<proxies.length; i++)
			proxies[i] = parseType(arguments[i]);
		
		return proxies;
	}

	public void visitMethodInsn(int opcode, String owner, String name, String desc) 
	{
		// Opcodes INVOKEVIRTUAL, INVOKESPECIAL, INVOKESTATIC or INVOKEINTERFACE
		
		Instruction op;
		MethodProxy method;
		TypeProxy   type;
		TypeProxy   args[];
			
		type = new TypeProxy ( getClassName(owner) );
		args = parseMethodArguments(desc);
		
		method = new MethodProxy(type,name,args);
		
		op = visitInstruction(opcode);
		op.setReference(method);
	}


	/* Local variables */

	public void visitVarInsn(int opcode, int var) 
	{
		// Opcodes ILOAD, LLOAD, FLOAD, DLOAD, ALOAD, ISTORE, LSTORE, FSTORE, DSTORE, ASTORE or RET
		
		Instruction op;
		
		op = visitInstruction(opcode);
		op.setReference( getLocalReference(var) );
	}
	

	public void visitIincInsn(int var, int increment) 
	{
		// Opcode IINC
		
		Instruction op;
		
		op = visitInstruction(Opcodes.IINC);
		op.setOperand(increment);
		op.setReference( getLocalReference(var) );
	}
	
	
	/**
	 * Local variable declaration ([this]+[args]+[locals])
	 */

	public void visitLocalVariable(String name, String descriptor, String signature,
			Label start, Label end, int index) 
	{
		// TODO Local variable scope (start/end)	
		// System.out.println(" - Local #"+index+": "+name+" "+descriptor+" "+start+"/"+end);
		
		Data local = new Data();
		
		local.setID(name);
		local.setType( parseType(Type.getType(descriptor)) );
		
		locals.set(index, local);
		
		// Signature (generics)
		
		parseSignature(local,signature);

	}
	
	


	/* Label information */

	public void visitLabel(Label label) 
	{
		currentLabel = label;
	}
	
	/* Debug information */
	
	public void visitLineNumber(int line, Label start) 
	{
		Value file = new Value();
		file.setID("line");
		file.setValue(line);
		
		Value label = new Value();
		label.setID("label");
		label.setValue(start.toString());
		
		Annotation annotation = new Annotation();
		annotation.setID("source");
		annotation.add(file);
		annotation.add(label);
		
		block.add(annotation);	
	}

	/* Try/catch block */

	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) 
	{
		ExceptionBlock exception = new ExceptionBlock();
		
		if (type!=null)
			exception.setType( new TypeProxy ( getClassName(type) ) );
		
		exception.setStart ( start.toString() );
		exception.setEnd ( end.toString() );
		exception.setHandler ( handler.toString() );
		
		block.add(exception);
	}


}
