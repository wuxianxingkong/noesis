package ikor.model.graphics.swing;

import ikor.model.graphics.DrawingElement;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;


/**
 * JDrawing Component mouse event handling
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class JDrawingMouseListener implements MouseInputListener
{
	private JDrawingComponent control;
	
	private DrawingElement draggedElement;
	
	public JDrawingMouseListener (JDrawingComponent control)
	{
		this.control = control;
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY();
		
		if (control.getSelectionListener()!=null) {
			
			String id = control.getDrawing().getElement(x,y);
			
			if (e.isControlDown())
				control.getSelectionListener().addSelection(id);
			else
				control.getSelectionListener().setSelection(id);
			
			control.repaint();
			
		} else if (control.getActionListener()!=null) {
			
			String id = control.getDrawing().getElement(x,y);
			
			if (id!=null) {
				control.getActionListener().update(id,x,y);
				control.repaint();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{			
		if (control.getDraggingListener()!=null) {
			draggedElement = control.getDrawing().getDrawingElement( e.getX(), e.getY() );
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		draggedElement = null;
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{
		mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		if ((control.getDraggingListener()!=null) && (draggedElement!=null)) {
			
			if (draggedElement.getId()!=null) {
				control.getDraggingListener().update( draggedElement.getId(), e.getX(), e.getY());
				control.repaint();
			}
			
		} else {  // Update tooltip
			
			String id;
			String tooltip;			

			if (control.getTooltipProvider()!=null) {

				control.setToolTipText("");

				id = control.getDrawing().getElement( e.getX(), e.getY());

				if (id!=null) {
					tooltip = control.getTooltipProvider().get(id);

					if (tooltip!=null)
						control.setToolTipText(tooltip);
				}
			}
		}
	}
}
