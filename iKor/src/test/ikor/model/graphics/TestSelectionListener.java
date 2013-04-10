package test.ikor.model.graphics;

import ikor.model.graphics.Drawing;
import ikor.model.graphics.DrawingSelectionListener;
import ikor.model.graphics.Style;

import java.awt.Color;


public class TestSelectionListener implements DrawingSelectionListener
{
	Drawing drawing;
	Style   currentStyle;
	
	public TestSelectionListener (Drawing drawing)
	{
		this.drawing = drawing;
	}

	@Override
	public void setSelection (String id) 
	{
		clearSelection();
		
		currentStyle = new Style ( new Color(0xff, 0x00, 0x00, 0x80), 3);
		drawing.getDrawingElement(id).setBorder(currentStyle);
	}

	@Override
	public void addSelection(String id) 
	{
		if (currentStyle==null)
			currentStyle = new Style ( new Color(0xff, 0x00, 0x00, 0x80), 3);

		drawing.getDrawingElement(id).setBorder(currentStyle);
	}

	@Override
	public void clearSelection() 
	{
		if (currentStyle!=null) {
			currentStyle.setWidth(0);
			currentStyle = null;
		}
	}
	
}
