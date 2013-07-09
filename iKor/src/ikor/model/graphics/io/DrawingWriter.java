package ikor.model.graphics.io;

import ikor.model.graphics.Drawing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class DrawingWriter 
{
	protected Drawing drawing;
	
	public DrawingWriter (Drawing drawing)
	{
		this.drawing = drawing;
	}
	
	public void write (File file) 
		throws IOException
	{
		FileOutputStream writer = new FileOutputStream(file);
		
		write ( writer );
		
		writer.close();
	}
	
	public abstract void write (OutputStream writer)
		throws IOException;

}
