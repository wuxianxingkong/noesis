package sandbox.software.test.jvm.mock;

public class Fields {
	
	// Standard fields
	
	public int integer;
	public float real;
	public String string;	
	public Object object;
	
	// Arrays
	
	public int[] vector;
	public float[][] matrix;
	
	// Static
	
	public static int global;
	
	// Static + Final (constants)
	
	public static final int magicNumber = 7;
	public static final float pi = 3.1416f;
	public static final String msg = "Mensaje";
	
	public static final Object initializedAtRuntime = new StringBuffer();

}
