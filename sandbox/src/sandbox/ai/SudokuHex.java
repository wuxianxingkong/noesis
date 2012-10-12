package sandbox.ai;

// Title:       SudokuHex
// Version:     0.1
// Copyright:   2012
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org


/**
 * Implementación base para la resolución de un Sudoku hexadecimal.
 * 
 * @author Fernando Berzal
 */

public class SudokuHex extends Sudoku
{
	public static final int SIZE = 16;
	public static final int EMPTY = -1;
	
	/**
	 * Constructor
	 * @param template Cadena representando una plantilla de sudoku
	 */
	public SudokuHex (String template)
	{
		sudoku = parse(template);
	}

	public SudokuHex (int[][] tablero)
	{
		sudoku = tablero.clone();
	}
		
	// Tamaño
	
	public int size()
	{
		return SIZE;
	}
	
	// Accessors
	
	public boolean isEmpty(int i, int j)
	{
		return (sudoku[i][j] == EMPTY);
	}
	
	public int emptyValue ()
	{
		return EMPTY;
	}
	
	
	// Candidate generation
	// - DOMAIN: 7s
	// - Valid values: 2.5s
	// - MRV heuristic:
	
	private int[] DOMAIN = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
	
	public int[] values (int i, int j)
	{
		boolean[] valid = checkCandidates(i,j);
		int       count = 0;
		
		for (int v=0; v<valid.length; v++)
			if (valid[DOMAIN[v]])
				count++;
		
		int[] candidates = new int[count];
		int   position = 0;
		
		for (int v=0; v<valid.length; v++) 
			if (valid[DOMAIN[v]]) {
				candidates[position] = DOMAIN[v];
				position++;
			}
		
		return candidates; // candidates vs. DOMAIN
	}
	
	private boolean[] checkCandidates(int i, int j)
	{
		for (int v=0; v<SIZE; v++)
			flag[v] = true;
		
		// Row
		
		for (int c=0; c<SIZE; c++)
			if (sudoku[i][c]!=EMPTY)
				flag[sudoku[i][c]] = false;

		// Column
		
		for (int r=0; r<SIZE; r++)
			if (sudoku[r][j]!=EMPTY)
				flag[sudoku[r][j]] = false;

		// Block

		int row = 4*(i/4);
		int column = 4*(j/4);
		
		for (int r=row; r<row+4; r++)
			for (int c=column; c<column+4; c++)
				if (sudoku[r][c]!=EMPTY)
					flag[sudoku[r][c]] = false;		
		
		return flag;
	}

	
	// Check constraints
	
	private int[]     data = new int[SIZE];
	private boolean[] flag = new boolean[SIZE];	
	
	private boolean checkSet (int[] set)
	{
		int i;
		
		for (i=0; i<SIZE; i++)
			flag[i] = false;
		
		for (i=0; i<SIZE; i++)
			if (set[i]!=EMPTY)
			   if (flag[set[i]])
				   return false;
			   else
				   flag[set[i]] = true;

		return true;
	}
	
	protected int[] row (int i)
	{
		return sudoku[i];
	}	
	
	
	public boolean checkRow (int i)
	{	
		return checkSet(row(i));
	}
	
	protected int[] column (int j)
	{
		int i;
		
		for (i=0; i<SIZE; i++)
			data[i] = sudoku[i][j];
		
		return data;
	}
	
	public boolean checkColumn (int j)
	{		
		return checkSet(column(j));
	}
	
	
	protected int[] block (int k)
	{
		int i;
		int row = 4*(k/4);
		int column = 4*(k%4);
		
		for (i=0; i<SIZE; i++)
			data[i] = sudoku[row+(i/4)][column+(i%4)];

		return data;
	}
	
	public boolean checkBlock (int k)
	{	
		return checkSet(block(k));		
	}
	
	
	public boolean check ()
	{
		boolean ok = true;
		
		for (int i=0; ok && (i<16); i++)
			ok = checkRow(i);
		
		for (int j=0; ok && (j<16); j++)
			ok = checkColumn(j);
		
		for (int k=0; ok && (k<16); k++)
			ok = checkBlock(k);
		
		return ok;
	}
	
	
	
	// E/S
	
	public static int[][] parse (String template) 
	{
		int[][] sudoku = new int[16][16];
		int i = 0;
		int j = 0;
		char c;
		
		for (int p=0; p<template.length(); p++) {
			
			c = template.charAt(p);
			
			if ((c>='0') && (c<='9')) {
				sudoku[i][j] = c-'0';
			} else if ((c>='A') && (c<='F')) {
				sudoku[i][j] = 10+c-'A';
			} else if ((c>='a') && (c<='f')) {
				sudoku[i][j] = 10+c-'a';
			} else if ((c=='.') || (c=='-')) {
				sudoku[i][j] = -1;
			}
			
			if (  ((c>='0') && (c<='9'))
			   || ((c>='A') && (c<='F')) 
			   || ((c>='a') && (c<='f'))
               || (c=='.') 
			   || (c=='-') ) {
				
				j++;
				
				if (j==16) {
					i++;
					j = 0;
				}
			}			
		}
		
		return sudoku;
	}

	
	
	/**
     * Representación del sudoku como una cadena de caracteres
	 * @see java.lang.Object#toString()
	 */

	public String toString() 
	{
		return toString(sudoku);
	}
		
	public static String toString(int[][] sudoku) 
	{
		StringBuffer buffer = new StringBuffer();
		
		for (int i=0; i<16; i++) {
			
			for (int j=0; j<16; j++)
				buffer.append(cell(sudoku[i][j]));
			
			buffer.append("\n");
		}
		
		return buffer.toString();
	}

	public static String toString(int[] set) {
		StringBuffer buffer = new StringBuffer();
		
		for (int i=0; i<set.length; i++) {
			buffer.append(set[i]);
		}
		
		return buffer.toString();
	}
	
	public static char cell (int i)
	{
		if ((i>=0) && (i<=9))
		    return (char)('0'+i);
		else if ((i>=10) && (i<=15))
			return (char)('a'+i-10);
		else
			return '.';
	}
}
 
                
 
