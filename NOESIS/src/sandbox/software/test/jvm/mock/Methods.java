package sandbox.software.test.jvm.mock;

public class Methods {
	
	
	
	// Simple methods
	
	public void nullMethod ()
	{
	}
	
	public void arrayMethod (int array[])
	{
	}
	
	public int constantMethod ()
	{
		return 1;
	}
	
	public int copyMethod (int i)
	{
		return i;
	}
	
	public int opMethod (int a, int b)
	{
		return a+b;
	}
	
	public int ifMethod (int a, int b)
	{
		if (a>b)
			return a;
		else
			return b;
	}
	
	public int switchMethod (int a, int b)
	{
		int result;
		
		switch (a) {
			case 1:
				result = b;
				break;
			case 2:
				result = 2*b;
				break;
			case 3:
				result = 4*b;
				break;			
			default:
				result = 0;
		}
		
		return result;
	}

	public int switch2Method (int a, int b)
	{
		int result;
		
		switch (a) {
			case 12:
				result = b;
				break;
			case 25:
				result = 2*b;
				break;
			case 37:
				result = 4*b;
				break;
			default:
				result = 0;
		}
		
		return result;
	}
	
	// Static methods
	
	public static void staticMethod ()
	{
	}
	
	public static int staticMethodWithArguments (int x)
	{
		return x;
	}	
	
	public static int staticMethodWithLocals (int x)
	{
		int y = 2;
		
		return x+y;
	}
	
	// Field access
	
	private int field = 0;
	
	public int getField ()
	{
	  return field;	
	}
	
	// Exceptions
	
	public void exceptionThrow()
		throws Exception
	{
		throw new Exception("Thrown exception...");
	}
	
	public boolean exceptionCatch(int x)
	{
		boolean result;
		
		try {
			
			if (x%2!=0)
				throw new Exception("Odd...");
			
			result = false;
			
		} catch (Exception error) {
			result = true;
		}
		
		return result;
	}
	
	public int exceptionFinally(int x)
	{
		int result;
		
		try {
			
			if (x%2!=0)
				throw new Exception("Odd...");
			
			result = 1;
			
		} catch (Exception error) {
			result = 2;
		} finally {
			result = 3;
		}
		
		return result;	
	}
	

}
