package ikor.model.graphics.io;

import ikor.model.graphics.Drawing;

public class DrawingWriterFactory 
{
	public static DrawingWriter create (Drawing drawing, String format)
	{
		if (format.equals("svg"))
			return new SVGDrawingWriter(drawing);
		else if (format.equals("png"))
			return new PNGDrawingWriter(drawing);
		else // "jpg" | "jpeg"
			return new JPGDrawingWriter(drawing);
	}

}
