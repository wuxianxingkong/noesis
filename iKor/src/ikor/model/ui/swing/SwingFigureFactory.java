package ikor.model.ui.swing;

import ikor.model.Observer;
import ikor.model.Subject;
import ikor.model.graphics.swing.JDrawingComponent;
import ikor.model.ui.Figure;
import ikor.model.ui.UIFactory;

import javax.swing.SwingUtilities;



public class SwingFigureFactory implements UIFactory<SwingUI,Figure>
{
	@Override
	public void build (SwingUI ui, Figure figure) 
	{
		JDrawingComponent control = new JDrawingComponent(figure.getDrawing());
		
		if (figure.getTooltipProvider()!=null)
			control.setTooltipProvider( figure.getTooltipProvider() );
		
		if (figure.getDraggingListener()!=null)
			control.setDraggingListener( figure.getDraggingListener() );
		
		if (figure.getSelectionListener()!=null)
			control.setSelectionListener( figure.getSelectionListener() );
		
		figure.addObserver( new FigureObserver(control) );
		
		ui.addComponent ( control );	
	}
	
	
	public class FigureObserver implements Observer
	{
		private JDrawingComponent control;
		
		public FigureObserver (JDrawingComponent control)
		{
			this.control = control;
		}

		@Override
		public void update(Subject o, Object arg) 
		{
		    SwingUtilities.invokeLater(new Runnable() 
		    {
		      public void run()
		      {
		    	  control.repaint();
		      }
		    });			
		}
	}	
	
}
