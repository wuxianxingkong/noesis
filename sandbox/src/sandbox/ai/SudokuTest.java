package sandbox.ai;

//Title:       Sudoku
//Version:     0.9
//Copyright:   2011
//Author:      Fernando Berzal Galiano
//E-mail:      berzal@acm.org

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test suite for Sudoku class.
 * 
 * @author Fernando Berzal
 */
public class SudokuTest {

	public static final String SUDOKU 
		= "97.3.4.65"
        + ".2.5.6.8."
        + "........."
        + "..58.29.."
        + "..2.4.3.."
        + "..87.51.."
        + "........."
        + ".6.2.8.3."
        + "84.1.9.27";	

	public static final String SUDOKU_RESUELTO 
		= "971384265"
		+ "324516789"
		+ "586927413"
		+ "415832976"
		+ "792641358"
		+ "638795142"
		+ "257463891"
		+ "169278534"
		+ "843159627";	// Unique solution, intermediate difficulty = 16ms !! 
	                    // (from  http://www.sudoku-solutions.com/)
	
	

	public static final String SUDOKU_X          = "008704900020005000065080400000000007003000600500000000059060130000800040001309700";
	public static final String SUDOKU_X_RESUELTO = "318724965427695813965183472184936257293571684576248391759462138632817549841359726";
	
	Sudoku sudoku;
	
	@Before
	public void setUp() throws Exception {
		sudoku = new Sudoku(SUDOKU);	
	}
	
	@Test
	public void testSudoku ()
	{		
		assertTrue("Sudoku válido", sudoku.check());
		assertFalse("No es un Sudoku-X", sudoku.checkX());
		
		assertEquals("Casillas vacías", 81-3*6-4*2-5, sudoku.emptyCells());
		assertFalse("Sudoku no resuelto", sudoku.isSolved());
		
		assertEquals("970304065", Sudoku.toString(sudoku.row(0)));
		assertEquals("020506080", Sudoku.toString(sudoku.row(1)));
		assertEquals("000000000", Sudoku.toString(sudoku.row(2)));
		assertEquals("005802900", Sudoku.toString(sudoku.row(3)));
		assertEquals("002040300", Sudoku.toString(sudoku.row(4)));
		assertEquals("008705100", Sudoku.toString(sudoku.row(5)));
		assertEquals("000000000", Sudoku.toString(sudoku.row(6)));
		assertEquals("060208030", Sudoku.toString(sudoku.row(7)));
		assertEquals("840109027", Sudoku.toString(sudoku.row(8)));	

		assertEquals("900000008", Sudoku.toString(sudoku.column(0)));
		assertEquals("720000064", Sudoku.toString(sudoku.column(1)));
		assertEquals("000528000", Sudoku.toString(sudoku.column(2)));
		assertEquals("350807021", Sudoku.toString(sudoku.column(3)));
		assertEquals("000040000", Sudoku.toString(sudoku.column(4)));
		assertEquals("460205089", Sudoku.toString(sudoku.column(5)));
		assertEquals("000931000", Sudoku.toString(sudoku.column(6)));
		assertEquals("680000032", Sudoku.toString(sudoku.column(7)));
		assertEquals("500000007", Sudoku.toString(sudoku.column(8)));	

		assertEquals("970020000", Sudoku.toString(sudoku.unit(0)));
		assertEquals("304506000", Sudoku.toString(sudoku.unit(1)));
		assertEquals("065080000", Sudoku.toString(sudoku.unit(2)));
		assertEquals("005002008", Sudoku.toString(sudoku.unit(3)));
		assertEquals("802040705", Sudoku.toString(sudoku.unit(4)));
		assertEquals("900300100", Sudoku.toString(sudoku.unit(5)));
		assertEquals("000060840", Sudoku.toString(sudoku.unit(6)));
		assertEquals("000208109", Sudoku.toString(sudoku.unit(7)));
		assertEquals("000030027", Sudoku.toString(sudoku.unit(8)));	
		
		assertEquals("920845037", Sudoku.toString(sudoku.diagonal1()));
		assertEquals("580247068", Sudoku.toString(sudoku.diagonal2()));	
	}
	
	@Test
	public void testSudokuX ()
	{		
		sudoku = new Sudoku(SUDOKU_X);
		
		assertTrue(sudoku.check());
		assertTrue(sudoku.checkX());
		
		assertEquals("Casillas vacías", 56, sudoku.emptyCells());
		assertFalse("Sudoku no resuelto", sudoku.isSolvedX());
	}	

	@Test
	public void testSudokuResuelto ()
	{		
		sudoku = new Sudoku(SUDOKU_RESUELTO);
		
		assertTrue(sudoku.check());
		assertFalse(sudoku.checkX());
		
		assertEquals("Casillas vacías", 0, sudoku.emptyCells());
		assertTrue("Sudoku resuelto", sudoku.isSolved());
	}	

	
	@Test
	public void testSudokuXResuelto ()
	{		
		sudoku = new Sudoku(SUDOKU_X_RESUELTO);
		
		assertTrue(sudoku.check());
		assertTrue(sudoku.checkX());
		
		assertEquals("Casillas vacías", 0, sudoku.emptyCells());
		assertTrue("Sudoku resuelto", sudoku.isSolved());
		assertTrue("Sudoku-X resuelto", sudoku.isSolvedX());
	}	
	
	
	@Test
	public void testCheckRow ()
	{		
		for (int i=0; i<9; i++)
			assertTrue(sudoku.checkRow(i));
			
		for (int i=0; i<9; i++) {
			sudoku.set(i,7,3);
			sudoku.set(i,5,3);
			assertFalse(sudoku.checkRow(i));
		}
	}

	@Test
	public void testCheckColumn ()
	{		
		for (int i=0; i<9; i++)
			assertTrue(sudoku.checkColumn(i));
			
		for (int i=0; i<9; i++) {
			sudoku.set(7,i,3);
			sudoku.set(5,i,3);
			assertFalse(sudoku.checkColumn(i));
		}
	}

	@Test
	public void testCheckUnit ()
	{		
		for (int i=0; i<9; i++)
			assertTrue(sudoku.checkUnit(i));
			
		for (int i=0; i<9; i++) {
			sudoku.set( 3*(i/3),   3*(i%3)+2, 3);
			sudoku.set( 3*(i/3)+1, 3*(i%3)+1, 3);
			assertFalse(sudoku.checkUnit(i));
		}
	}
	
	@Test
	public void testCheckDiagonal ()
	{		
		assertTrue(sudoku.checkDiagonal1());
		assertFalse(sudoku.checkDiagonal2());
		
		Sudoku sudokux = new Sudoku(SUDOKU_X_RESUELTO);

		assertTrue(sudokux.checkDiagonal1());
		assertTrue(sudokux.checkDiagonal2());

		sudokux.set( 4, 4, 4);
		sudokux.set( 2, 2, 4);
		sudokux.set( 2, 6, 4);
		
		assertFalse(sudokux.checkDiagonal1());
		assertFalse(sudokux.checkDiagonal2());
	}	
	
}
