package ikor.model.ui;

import ikor.model.graphics.Drawing;
import ikor.model.graphics.DrawingSelectionListener;
import ikor.model.graphics.DrawingTooltipProvider;
import ikor.model.graphics.DrawingUpdateListener;

/**
 * Figure component (for displaying & editing drawings)
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class Figure extends Component 
{
	private Drawing drawing;
	
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
	
	
	public Drawing getDrawing() 
	{
		return drawing;
	}

	public void setDrawing(Drawing drawing) 
	{
		this.drawing = drawing;
	}

	
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
