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
public class Sudoku9Test {

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
	
		
	Sudoku9 sudoku;
	
	@Before
	public void setUp() throws Exception {
		sudoku = new Sudoku9(SUDOKU);	
	}
	
	@Test
	public void testSudoku ()
	{		
		assertTrue("Sudoku válido", sudoku.check());
		
		assertEquals("Casillas vacías", 81-3*6-4*2-5, sudoku.emptyCells());
		assertFalse("Sudoku no resuelto", sudoku.isSolved());
		
		assertEquals("970304065", SudokuX.toString(sudoku.row(0)));
		assertEquals("020506080", SudokuX.toString(sudoku.row(1)));
		assertEquals("000000000", SudokuX.toString(sudoku.row(2)));
		assertEquals("005802900", SudokuX.toString(sudoku.row(3)));
		assertEquals("002040300", SudokuX.toString(sudoku.row(4)));
		assertEquals("008705100", SudokuX.toString(sudoku.row(5)));
		assertEquals("000000000", SudokuX.toString(sudoku.row(6)));
		assertEquals("060208030", SudokuX.toString(sudoku.row(7)));
		assertEquals("840109027", SudokuX.toString(sudoku.row(8)));	

		assertEquals("900000008", SudokuX.toString(sudoku.column(0)));
		assertEquals("720000064", SudokuX.toString(sudoku.column(1)));
		assertEquals("000528000", SudokuX.toString(sudoku.column(2)));
		assertEquals("350807021", SudokuX.toString(sudoku.column(3)));
		assertEquals("000040000", SudokuX.toString(sudoku.column(4)));
		assertEquals("460205089", SudokuX.toString(sudoku.column(5)));
		assertEquals("000931000", SudokuX.toString(sudoku.column(6)));
		assertEquals("680000032", SudokuX.toString(sudoku.column(7)));
		assertEquals("500000007", SudokuX.toString(sudoku.column(8)));	

		assertEquals("970020000", SudokuX.toString(sudoku.unit(0)));
		assertEquals("304506000", SudokuX.toString(sudoku.unit(1)));
		assertEquals("065080000", SudokuX.toString(sudoku.unit(2)));
		assertEquals("005002008", SudokuX.toString(sudoku.unit(3)));
		assertEquals("802040705", SudokuX.toString(sudoku.unit(4)));
		assertEquals("900300100", SudokuX.toString(sudoku.unit(5)));
		assertEquals("000060840", SudokuX.toString(sudoku.unit(6)));
		assertEquals("000208109", SudokuX.toString(sudoku.unit(7)));
		assertEquals("000030027", SudokuX.toString(sudoku.unit(8)));	
	}
	

	@Test
	public void testSudokuResuelto ()
	{		
		sudoku = new Sudoku9(SUDOKU_RESUELTO);
		
		assertTrue(sudoku.check());
		
		assertEquals("Casillas vacías", 0, sudoku.emptyCells());
		assertTrue("Sudoku resuelto", sudoku.isSolved());
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
		
}
