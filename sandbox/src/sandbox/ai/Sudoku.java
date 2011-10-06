package sandbox.ai;

//Title:       Sudoku
//Version:     0.9
//Copyright:   2011
//Author:      Fernando Berzal Galiano
//E-mail:      berzal@acm.org

public class Sudoku {
	
	private int[][] sudoku;	
	
	public Sudoku (String plantilla)
	{
		sudoku = parse(plantilla);
	}

	public Sudoku (int[][] tablero)
	{
		sudoku = tablero.clone();
	}
	
	// Accessors
	
	public int get(int i, int j)
	{
		return sudoku[i][j];
	}
	
	public void set(int i, int j, int v)
	{
		sudoku[i][j] = v;
	}
	
	public boolean isEmpty(int i, int j)
	{
		return (sudoku[i][j] == 0);
	}
	
	// Empty cells
	
	public int emptyCells ()
	{
		int i,j;
		int count = 0;
		
		for (i=0; i<9; i++)
			for (j=0; j<9; j++)
				if (isEmpty(i,j))
					count++;
		
		return count;
	}
	
	// Comprobaciones
	
	private int[]     data = new int[9];
	private boolean[] flag = new boolean[10];
	
	
	private boolean checkSet (int[] set)
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
	
	
	protected int[] diagonal1 ()
	{
		int i;
		
		for (i=0; i<9; i++)
			data[i] = sudoku[i][i];
		
		return data;
	}
	
	public boolean checkDiagonal1 ()
	{
		return checkSet(diagonal1());		
	}
	
	protected int[] diagonal2 ()
	{
		int i;
		
		for (i=0; i<9; i++)
			data[i] = sudoku[i][8-i];
		
		return data;
	}
	
	public boolean checkDiagonal2 ()
	{
		return checkSet(diagonal2());		
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

	public boolean checkX ()
	{
		boolean ok = check();
		
		if (ok)
		    ok = checkDiagonal1();
		
		if (ok)
			ok = checkDiagonal2();
		
		return ok;
	}
	
	
	public boolean isSolved ()
	{
		return (emptyCells()==0) && check();
	}

	public boolean isSolvedX ()
	{
		return (emptyCells()==0) && checkX();
	}

	// E/S
	
	public String toString ()
	{
		return toString(sudoku);
	}
	
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
	
	
	public static String toString (int[][] sudoku)
	{
		StringBuffer buffer = new StringBuffer();
		
		for (int i=0; i<9; i++) {
			
			for (int j=0; j<9; j++)
				buffer.append(sudoku[i][j]);
			
			buffer.append("\n");
		}
		
		return buffer.toString();
	}	

	public static String toString (int[] set)
	{
		StringBuffer buffer = new StringBuffer();
		
		for (int i=0; i<set.length; i++) {
			buffer.append(set[i]);
		}
		
		return buffer.toString();
	}	
	
}
