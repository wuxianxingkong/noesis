package sandbox.ai;

public class Sudoku9 extends Sudoku 
{
	public Sudoku9 (String plantilla)
	{
		sudoku = parse(plantilla);
	}

	public Sudoku9 (int[][] tablero)
	{
		sudoku = tablero.clone();
	}

	// Tamaño
	
	public int size()
	{
		return 9;
	}
	

	// Accessors
	
	public boolean isEmpty(int i, int j)
	{
		return (sudoku[i][j] == 0);
	}
	
	public int emptyValue ()
	{
		return 0;
	}
	
	
	// Candidates
	
	private int[]     DOMAIN = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private boolean[] valid  = new boolean[10];
    
	public int[] values (int i, int j)
	{
		boolean[] valid = checkCandidates(i,j);
		int       count = 0;
		
		for (int v=0; v<DOMAIN.length; v++)
			if (valid[DOMAIN[v]])
				count++;
		
		int[] candidates = new int[count];
		int   position = 0;
		
		for (int v=0; v<DOMAIN.length; v++) 
			if (valid[DOMAIN[v]]) {
				candidates[position] = DOMAIN[v];
				position++;
			}
		
		return candidates; // candidates vs. DOMAIN
	}
	
	private boolean[] checkCandidates(int i, int j)
	{
		for (int v=0; v<valid.length; v++)
			valid[v] = true;
		
		// Row
		
		for (int c=0; c<size(); c++)
			if (!isEmpty(i,c))
				valid[sudoku[i][c]] = false;

		// Column
		
		for (int r=0; r<size(); r++)
			if (!isEmpty(r,j))
				valid[sudoku[r][j]] = false;

		// Block

		int row = 3*(i/3);
		int column = 3*(j/3);
		
		for (int r=row; r<row+3; r++)
			for (int c=column; c<column+3; c++)
				if (!isEmpty(r,c))
					valid[sudoku[r][c]] = false;		
		
		return valid;
	}

	
	
	// Comprobaciones
	
	protected int[]     data = new int[9];
	private   boolean[] flag = new boolean[10];	
	
	protected boolean checkSet (int[] set)
	{
		int i;
		
		for (i=0; i<10; i++)
			flag[i] = false;
		
		for (i=0; i<9; i++)
			if ((set[i]>0) && flag[set[i]])
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
		
		for (i=0; i<9; i++)
			data[i] = sudoku[i][j];
		
		return data;
	}
	
	public boolean checkColumn (int j)
	{		
		return checkSet(column(j));
	}
	
	protected int[] unit (int k)
	{
		int i;
		int row = 3*(k/3);
		int column = 3*(k%3);
		
		for (i=0; i<9; i++)
			data[i] = sudoku[row+(i/3)][column+(i%3)];

		return data;
	}
	
	public boolean checkUnit (int k)
	{	
		return checkSet(unit(k));		
	}
	
	public boolean check ()
	{
		boolean ok = true;
		
		for (int i=0; ok && (i<9); i++)
			ok = checkRow(i);
		
		for (int j=0; ok && (j<9); j++)
			ok = checkColumn(j);
		
		for (int k=0; ok && (k<9); k++)
			ok = checkUnit(k);
		
		return ok;
	}

	public boolean check (int i, int j)
	{
		boolean ok = true;

		ok = checkRow(i);
		
		if (ok)
			ok = checkColumn(j);
		
		if (ok)
			ok = checkUnit(3*(i/3)+(j/3));
		
		return ok;
	}
	
	// E/S
	
	public static int[][] parse (String template) 
	{
		int[][] sudoku = new int[9][9];
		int i = 0;
		int j = 0;
		char c;
		
		for (int p=0; p<template.length(); p++) {
			
			c = template.charAt(p);
			
			if ((c>='0') && (c<='9')) {
				sudoku[i][j] = c-'0';
			} else if ((c=='.') || (c=='-')) {
				sudoku[i][j] = 0;
			}
			
			if (  ((c>='0') && (c<='9'))
			   || (c=='.') 
			   || (c=='-') ) {
				
				j++;
				
				if (j==9) {
					i++;
					j = 0;
				}
			}			
		}
		
		return sudoku;
	}

	public String toString() 
	{
		return toString(sudoku);
	}
		
	public static String toString(int[][] sudoku) 
	{
		StringBuffer buffer = new StringBuffer();
		
		for (int i=0; i<9; i++) {
			
			for (int j=0; j<9; j++)
				buffer.append(sudoku[i][j]);
			
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

}