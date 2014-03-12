package ikor.model.ui;

import ikor.model.Subject;

import ikor.model.graphics.Drawing;
import ikor.model.graphics.DrawingSelectionListener;
import ikor.model.graphics.DrawingTooltipProvider;
import ikor.model.graphics.DrawingUpdateListener;

/**
 * Figure component (for displaying & editing drawings)
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class Figure<M> extends Component<M> 
{
	private Drawing drawing;
	private M       model;
	
	private DrawingTooltipProvider tooltipProvider;
	private DrawingUpdateListener draggingListener;
	private DrawingSelectionListener selectionListener;
	
	public Figure ()
	{
	}
	
	public Figure (String id)
	{
		this.setId(id);
	}
	
	
	public M getModel ()
	{
		return model;
	}
	
	public void setModel (M model)
	{
		this.model = model;
	}
	
	
	public Drawing getDrawing() 
	{
		return drawing;
	}

	public void setDrawing(Drawing drawing) 
	{
		this.drawing = drawing;
	}

	
	public void setSize (int width, int height)
	{
		drawing.setWidth(width);
		drawing.setHeight(height);
	}

	
	// Subject/observer interface
	
	boolean updating = false;
	
	public void update ()
	{
		drawing.update();
		update(null, null);		
	}
		
	@Override
	public void update (Subject subject, M object)
	{
		if (!updating) {			
			updating = true;
			
			if (object!=null) {
				setModel(object);
				notifyObservers(object);
			} else {
				setModel(null);
				notifyObservers ();
			}

			updating = false;
		}
	}
	
	
	// Event handling
	
	public DrawingTooltipProvider getTooltipProvider() 
	{
		return tooltipProvider;
	}

	public void setTooltipProvider(DrawingTooltipProvider tooltipProvider) 
	{
		this.tooltipProvider = tooltipProvider;
	}

	public DrawingUpdateListener getDraggingListener() 
	{
		return draggingListener;
	}

	public void setDraggingListener(DrawingUpdateListener draggingListener) 
	{
		this.draggingListener = draggingListener;
	}

	public DrawingSelectionListener getSelectionListener() 
	{
		return selectionListener;
	}

	public void setSelectionListener(DrawingSelectionListener selectionListener) 
	{
		this.selectionListener = selectionListener;
	}
}
