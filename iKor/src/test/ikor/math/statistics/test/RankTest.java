package test.ikor.math.statistics.test;

import static org.junit.Assert.assertEquals;
import ikor.math.MatrixFactory;
import ikor.math.Vector;
import ikor.math.statistics.test.Rank;

import org.junit.Test;

public class RankTest 
{
	// [r,tieadj] = tiedrank(mileage(:,2))
	
	@Test
	public void testMATLABexample ()
	{
		double mileage[] = new double[] { 34.5, 34.8, 33.8, 33.4, 33.7, 33.9 };
		Vector x = MatrixFactory.createVector(mileage);
		
		Rank rank = new Rank(x);
		
		assertEquals ( 6, x.size() );
		assertEquals ( 5, rank.rank(0), 0.0001 );
		assertEquals ( 6, rank.rank(1), 0.0001 );
		assertEquals ( 3, rank.rank(2), 0.0001 );
		assertEquals ( 1, rank.rank(3), 0.0001 );
		assertEquals ( 2, rank.rank(4), 0.0001 );
		assertEquals ( 4, rank.rank(5), 0.0001 );
		
		assertEquals ( 0, rank.tieAdjustment(), 0.0001 );
	}
	
	// [r,tieadj] = tiedrank([1 2 2 4 4 5])
	
	@Test
	public void testMATLABtie ()
	{
		double data[] = new double[]{1, 2, 2, 4, 4, 5};
		Vector x = MatrixFactory.createVector(data);
		
		Rank rank = new Rank(x);
		
		assertEquals ( 6, x.size() );
		assertEquals ( 1, rank.rank(0), 0.0001 );
		assertEquals ( 2.5, rank.rank(1), 0.0001 );
		assertEquals ( 2.5, rank.rank(2), 0.0001 );
		assertEquals ( 4.5, rank.rank(3), 0.0001 );
		assertEquals ( 4.5, rank.rank(4), 0.0001 );
		assertEquals ( 6, rank.rank(5), 0.0001 );
		
		assertEquals ( 6, rank.tieAdjustment(), 0.0001 );
	}

	// [r,tieadj] = tiedrank([1 1 1 4 4 5])
	
	@Test
	public void testMATLABtie3 ()
	{
		double data[] =  new double[]{1, 1, 1, 4, 4, 5};
		Vector x = MatrixFactory.createVector(data);
		
		Rank rank = new Rank(x);
		
		assertEquals ( 6, x.size() );
		assertEquals ( 2, rank.rank(0), 0.0001 );
		assertEquals ( 2, rank.rank(1), 0.0001 );
		assertEquals ( 2, rank.rank(2), 0.0001 );
		assertEquals ( 4.5, rank.rank(3), 0.0001 );
		assertEquals ( 4.5, rank.rank(4), 0.0001 );
		assertEquals ( 6, rank.rank(5), 0.0001 );
		
		assertEquals ( 15, rank.tieAdjustment(), 0.0001 );
	}

	// [r,tieadj] = tiedrank([3 3 3 3 3 3])
	
	@Test
	public void testMATLABdegenerate ()
	{
		double data[] = new double[]{3, 3, 3, 3, 3, 3};
		Vector x = MatrixFactory.createVector(data);
		
		Rank rank = new Rank(x);
		
		assertEquals ( 6, x.size() );
		assertEquals ( 3.5, rank.rank(0), 0.0001 );
		assertEquals ( 3.5, rank.rank(1), 0.0001 );
		assertEquals ( 3.5, rank.rank(2), 0.0001 );
		assertEquals ( 3.5, rank.rank(3), 0.0001 );
		assertEquals ( 3.5, rank.rank(4), 0.0001 );
		assertEquals ( 3.5, rank.rank(5), 0.0001 );
		
		assertEquals ( 105, rank.tieAdjustment(), 0.0001 );
	}
}
