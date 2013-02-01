package sandbox.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FunctionsTest 
{	
	public static double ERROR = 1e-7;	

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testErf() 
	{
		assertEquals( 0.0000000, Functions.erf(0.00), ERROR );
		assertEquals( 0.0563720, Functions.erf(0.05), ERROR );
		assertEquals( 0.1124629, Functions.erf(0.10), ERROR );
		assertEquals( 0.1679960, Functions.erf(0.15), ERROR );
		assertEquals( 0.2227026, Functions.erf(0.20), ERROR );
		assertEquals( 0.2763264, Functions.erf(0.25), ERROR );
		assertEquals( 0.3286268, Functions.erf(0.30), ERROR );
		assertEquals( 0.3793821, Functions.erf(0.35), ERROR );
		assertEquals( 0.4283924, Functions.erf(0.40), ERROR );
		assertEquals( 0.4754817, Functions.erf(0.45), ERROR );
		assertEquals( 0.5204999, Functions.erf(0.50), ERROR );
		assertEquals( 0.5633234, Functions.erf(0.55), ERROR );
		assertEquals( 0.6038561, Functions.erf(0.60), ERROR );
		assertEquals( 0.6420293, Functions.erf(0.65), ERROR );
		assertEquals( 0.6778012, Functions.erf(0.70), ERROR );
		assertEquals( 0.7111556, Functions.erf(0.75), ERROR );
		assertEquals( 0.7421010, Functions.erf(0.80), ERROR );
		assertEquals( 0.7706681, Functions.erf(0.85), ERROR );
		assertEquals( 0.7969082, Functions.erf(0.90), ERROR );
		assertEquals( 0.8208908, Functions.erf(0.95), ERROR );
		assertEquals( 0.8427008, Functions.erf(1.00), ERROR );
		
		assertEquals( 0.8802051, Functions.erf(1.10), ERROR );
		assertEquals( 0.9103140, Functions.erf(1.20), ERROR );
		assertEquals( 0.9340079, Functions.erf(1.30), ERROR );
		assertEquals( 0.9522851, Functions.erf(1.40), ERROR );
		assertEquals( 0.9661051, Functions.erf(1.50), ERROR );
		assertEquals( 0.9763484, Functions.erf(1.60), ERROR );
		assertEquals( 0.9837905, Functions.erf(1.70), ERROR );
		assertEquals( 0.9890905, Functions.erf(1.80), ERROR );
		assertEquals( 0.9927904, Functions.erf(1.90), ERROR );
		assertEquals( 0.9953223, Functions.erf(2.00), ERROR );
		
		assertEquals( 0.9995930, Functions.erf(2.50), ERROR );
		assertEquals( 0.9999779, Functions.erf(3.00), ERROR );
		assertEquals( 0.9999993, Functions.erf(3.50), ERROR );
		
	}
	
	@Test
	public void testErfc() 
	{	
		assertEquals( 1.0000000, Functions.erfc(0.00), ERROR );
		assertEquals( 0.9436280, Functions.erfc(0.05), ERROR );
		assertEquals( 0.8875371, Functions.erfc(0.10), ERROR );
		assertEquals( 0.8320040, Functions.erfc(0.15), ERROR );
		assertEquals( 0.7772974, Functions.erfc(0.20), ERROR );
		assertEquals( 0.7236736, Functions.erfc(0.25), ERROR );
		assertEquals( 0.6713732, Functions.erfc(0.30), ERROR );
		assertEquals( 0.6206179, Functions.erfc(0.35), ERROR );
		assertEquals( 0.5716076, Functions.erfc(0.40), ERROR );
		assertEquals( 0.5245183, Functions.erfc(0.45), ERROR );
		
		assertEquals( 0.4795001, Functions.erfc(0.50), ERROR );
		assertEquals( 0.4366766, Functions.erfc(0.55), ERROR );
		assertEquals( 0.3961439, Functions.erfc(0.60), ERROR );
		assertEquals( 0.3579707, Functions.erfc(0.65), ERROR );
		assertEquals( 0.3221988, Functions.erfc(0.70), ERROR );
		assertEquals( 0.2888444, Functions.erfc(0.75), ERROR );
		assertEquals( 0.2578990, Functions.erfc(0.80), ERROR );
		assertEquals( 0.2293319, Functions.erfc(0.85), ERROR );
		assertEquals( 0.2030918, Functions.erfc(0.90), ERROR );
		assertEquals( 0.1791092, Functions.erfc(0.95), ERROR );
		assertEquals( 0.1572992, Functions.erfc(1.00), ERROR );
		
		assertEquals( 0.1197949, Functions.erfc(1.10), ERROR );
		assertEquals( 0.0896860, Functions.erfc(1.20), ERROR );
		assertEquals( 0.0659921, Functions.erfc(1.30), ERROR );
		assertEquals( 0.0477149, Functions.erfc(1.40), ERROR );
		assertEquals( 0.0338949, Functions.erfc(1.50), ERROR );

		assertEquals( 0.0046777, Functions.erfc(2.00), ERROR );
		assertEquals( 0.0004070, Functions.erfc(2.50), ERROR );
		assertEquals( 0.0000221, Functions.erfc(3.00), ERROR );
		assertEquals( 0.0000007, Functions.erfc(3.50), ERROR );
	}
}
