package sandbox.mdsd.graphics.test;

import sandbox.mdsd.graphics.Circle;
import sandbox.mdsd.graphics.Drawing;
import sandbox.mdsd.graphics.DrawingElement;
import sandbox.mdsd.graphics.DrawingUpdateListener;
import sandbox.mdsd.graphics.Line;


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
					
					if (name.endsWith(id)) {
						((Line)element).setEndX(x);
						((Line)element).setEndY(y);
					} else if (name.contains(id)) {
						((Line)element).setStartX(x);
						((Line)element).setStartY(y);
					}
				}
			}
		}
	}
	
}
