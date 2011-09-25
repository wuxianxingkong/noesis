package ikor.collection.sequence;

import java.util.Iterator;

import ikor.collection.ListIterator;


public class StringSequence implements Sequence<Character>
{
	private String str;

	public StringSequence (String str)
	{
		this.str = str;
	}

	@Override
	public int size ()
	{
		return str.length();
	}
	
	@Override
	public Character get (int n)
	{
		if ((n>=0) && (n<size()) )
			return str.charAt(n);
		else
			return null;
	}

	@Override
	public boolean contains(Character c) {
		return str.indexOf(c)!=-1;
	}

	@Override
	public int index(Character c) {
		return str.indexOf(c);
	}
	
	@Override
	public Iterator<Character> iterator() {
		return new ListIterator<Character>(this);
	}

	@Override
	public String toString ()
	{
		return str;
	}

}
