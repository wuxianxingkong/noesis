package test.ikor.math;

import static org.junit.Assert.*;
import ikor.math.Histogram;
import ikor.math.MatrixFactory;
import ikor.math.Vector;

import org.junit.Test;

public class HistogramTest 
{
	public static final int SIZE = 100;
	public static final double EPSILON = 1e-9;
	
	// 0, 1, 2, 3, 4, 5...
	
	private Vector uniformVector ()
	{
		Vector v = MatrixFactory.createVector(SIZE);
		
		for (int i=0; i<SIZE; i++)
			v.set(i,i);
		
		return v;
	}

	@Test
	public void testUniform1() 
	{
		Vector v = uniformVector();
		Histogram h = new Histogram(SIZE,v);
		
		for (int i=0; i<SIZE; i++) {
			assertEquals(1, h.get(i), EPSILON);
			assertEquals(i*(SIZE-1)/(double)SIZE, h.threshold(i), EPSILON);
		}
	}

	@Test
	public void testUniform10() 
	{
		Vector v = uniformVector();
		Histogram h = new Histogram(SIZE/10,v);
		
		for (int i=0; i<SIZE/10; i++) {
			assertEquals(10, h.get(i), EPSILON);
			assertEquals(10*i*(SIZE-1)/(double)SIZE, h.threshold(i), EPSILON);
		}
	}	

	@Test
	public void testUniform100() 
	{
		Vector v = uniformVector();
		Histogram h = new Histogram(SIZE/100,v);
		
		for (int i=0; i<SIZE/100; i++) {
			assertEquals(100, h.get(i), EPSILON);
			assertEquals(100*i*(SIZE-1)/(double)SIZE, h.threshold(i), EPSILON);
		}
	}	
	
	@Test
	public void testUniform1Minus1() 
	{
		Vector v = uniformVector();
		Histogram h = new Histogram(SIZE-1,v);
		
		for (int i=0; i<SIZE-2; i++) {
			assertEquals(1, h.get(i), EPSILON);
			assertEquals(i, h.threshold(i), EPSILON);
		}

		assertEquals(2, h.get(SIZE-2), EPSILON);
		assertEquals(SIZE-2, h.threshold(SIZE-2), EPSILON);
	}
	
	@Test
	public void testUniformSquare() 
	{
		Vector v = uniformVector();
		int bins = (int) Math.sqrt(SIZE);
		Histogram h = new Histogram(bins,v);
		
		for (int i=0; i<bins; i++) {
			assertEquals(bins, h.get(i), EPSILON);
			assertEquals(i*(SIZE-1)/(double)bins, h.threshold(i), EPSILON);
		}
	}
	

	
	@Test
	public void testUniformLogarithmic2() 
	{
		int bins = 2;
		Vector v = uniformVector();
		Histogram h = new Histogram(bins, v, Histogram.Scale.Logarithmic);
		double scale = Math.log(SIZE)/bins;
		
		assertEquals("First bin", (int)h.threshold(1), h.get(0), EPSILON);
		assertEquals(0, h.threshold(0), EPSILON);

		assertEquals("Last bin", SIZE-h.threshold(1), h.get(1), EPSILON);
		assertEquals(Math.exp(scale)-1, h.threshold(1), EPSILON);		
	}

	@Test
	public void testUniformLogarithmic10() 
	{
		int bins = 10;
		Vector v = uniformVector();
		Histogram h = new Histogram(bins, v, Histogram.Scale.Logarithmic);
		double scale = Math.log(SIZE)/bins;
		
		assertEquals(0, h.threshold(0), EPSILON);
		assertEquals("Bin 0", 1, h.get(0), EPSILON);
		
		for (int i=1; i<bins-1; i++) {
			assertEquals(Math.exp(i*scale)-1, h.threshold(i), EPSILON);
			assertEquals("Bin "+i+" ["+h.threshold(i)+","+h.threshold(i+1)+")",
					Math.floor(h.threshold(i+1)-EPSILON)-Math.floor(h.threshold(i)-EPSILON), h.get(i), EPSILON);
		}
		
		assertEquals(Math.exp((bins-1)*scale)-1, h.threshold(bins-1), EPSILON);		
		assertEquals("last bin", SIZE-1-Math.floor(h.threshold(bins-1)), h.get(bins-1), EPSILON);
	}
	
	

	// 0, 1, 4, 9, 16, 25...
	
	private Vector squareVector ()
	{
		Vector v = MatrixFactory.createVector(SIZE);
		
		for (int i=0; i<SIZE; i++)
			v.set(i,i*i);
		
		return v;
	}
		
	// Let sq(x) denote the number of squares less than x.
	// sq(x) = the greatest integer less than sqrt(x)

	private int sq (double x)
	{
		return (int) Math.floor( Math.sqrt(x) ) + 1;  // +1 to include 0^2
	}
	
	@Test
	public void testSquare() 
	{
		Vector v = squareVector();
		Histogram h = new Histogram(SIZE-1,v);

		assertEquals( sq(SIZE-1), h.get(0), EPSILON);
		assertEquals( 0, h.threshold(0), EPSILON);
		
		for (int i=1; i<SIZE-2; i++) {
			assertEquals( "Number of squares @ i="+i, sq((i+1)*(SIZE-1)-1)-sq(i*(SIZE-1)-1), h.get(i), EPSILON);
			assertEquals( i*(SIZE-1), h.threshold(i), EPSILON);
		}
		
		assertEquals( 1, h.get(SIZE-2), EPSILON);
		assertEquals( (SIZE-2)*(SIZE-1), h.threshold(SIZE-2), EPSILON);		
	}

	@Test
	public void testSquare10() 
	{
		Vector v = squareVector();
		Histogram h = new Histogram(10,v);
		double width = (SIZE-1)*(SIZE-1)/10.0;

		assertEquals( sq(width), h.get(0), EPSILON);
		assertEquals( 0, h.threshold(0), EPSILON);
		
		for (int i=1; i<10; i++) {
			assertEquals( "Number of squares @ i="+i, sq((i+1)*width)-sq(i*width), h.get(i), EPSILON);
			assertEquals( i*width, h.threshold(i), EPSILON);
		}
	}

	
	@Test
	public void testSquareLogarithmic2() 
	{
		int bins = 2;
		Vector v = squareVector();
		Histogram h = new Histogram(bins, v, Histogram.Scale.Logarithmic);
		double scale = Math.log((SIZE-1)*(SIZE-1)+1)/bins;
		
		assertEquals("First bin", sq(h.threshold(1)), h.get(0), EPSILON);
		assertEquals(0, h.threshold(0), EPSILON);

		assertEquals("Last bin", SIZE-sq(h.threshold(1)), h.get(1), EPSILON);
		assertEquals(Math.exp(scale)-1, h.threshold(1), EPSILON);		
	}

	@Test
	public void testSquareLogarithmic10() 
	{
		int bins = 10;
		Vector v = squareVector();
		Histogram h = new Histogram(bins, v, Histogram.Scale.Logarithmic);
		double scale = Math.log((SIZE-1)*(SIZE-1)+1)/bins;
		
		assertEquals(0, h.threshold(0), EPSILON);
		assertEquals("Bin 0", sq(1), h.get(0), EPSILON);
		
		for (int i=1; i<bins-1; i++) {
			assertEquals(Math.exp(i*scale)-1, h.threshold(i), EPSILON);
			assertEquals("Bin "+i+" ["+h.threshold(i)+","+h.threshold(i+1)+")",
					sq(Math.floor(h.threshold(i+1)-EPSILON))-sq(Math.floor(h.threshold(i)-EPSILON)), h.get(i), EPSILON);
		}
		
		assertEquals(Math.exp((bins-1)*scale)-1, h.threshold(bins-1), EPSILON);		
		assertEquals("last bin", SIZE-sq(Math.floor(h.threshold(bins-1))), h.get(bins-1), EPSILON);
	}
	
}
