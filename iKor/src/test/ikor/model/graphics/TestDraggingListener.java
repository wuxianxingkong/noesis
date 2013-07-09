package test.ikor.model.graphics;

import ikor.model.graphics.Circle;
import ikor.model.graphics.Drawing;
import ikor.model.graphics.DrawingElement;
import ikor.model.graphics.DrawingUpdateListener;
import ikor.model.graphics.Line;


public class TestDraggingListener implements DrawingUpdateListener
{
	Drawing drawing;
	
	public TestDraggingListener (Drawing drawing)
	{
		this.drawing = drawing;
	}
	
	@Override
	public void update(String id, int x, int y) 
	{
		DrawingElement current = drawing.getDrawingElement(id);
		
		if (current.getId().startsWith("node")) {
			((Circle)current).setCenterX(x);
			((Circle)current).setCenterY(y);
			
			// Update links
			
			String name;
			
			for (DrawingElement element: drawing.getElements()) {
			
				name = element.getId();
				
				if ( (element instanceof Line) && (name!=null) ) {
					
					if (name.endsWith("-"+id)) {
						((Line)element).setEndX(x);
						((Line)element).setEndY(y);
					} else if (name.contains(id+"-")) {
						((Line)element).setStartX(x);
						((Line)element).setStartY(y);
					}
				}
			}
		}
	}
	
}
