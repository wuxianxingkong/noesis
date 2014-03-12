package ikor.model.ui.swing;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

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
		
		control.addComponentListener( new JDrawingComponentListener(figure, control) );
		
		figure.addObserver( new FigureObserver(figure,control) );
		
		ui.addComponent ( control );	
		
		control.setVisible( figure.isVisible() );
	}
	
	// Figure observer
	
	public class FigureObserver implements Observer
	{
		private Figure figure;
		private JDrawingComponent control;
		
		public FigureObserver (Figure figure, JDrawingComponent control)
		{
			this.figure = figure;
			this.control = control;
		}

		@Override
		public void update(Subject o, Object arg) 
		{
		    SwingUtilities.invokeLater(new Runnable() 
		    {
		      public void run()
		      {
		    	  control.setDrawing(figure.getDrawing());
		    	  control.setVisible(figure.isVisible());
		    	  control.repaint();
		      }
		    });			
		}
	}
	
	// Component listener
	
	public class JDrawingComponentListener implements ComponentListener
	{
		private Figure figure;
		private JDrawingComponent control;

		public JDrawingComponentListener (Figure figure, JDrawingComponent control)
		{
			this.figure = figure;
			this.control = control;
		}
		
		@Override
		public void componentResized(ComponentEvent e) 
		{
			figure.setSize(control.getWidth(), control.getHeight());
			figure.update();
			
		    SwingUtilities.invokeLater(new Runnable() 
		    {
		      public void run()
		      {
		    	  control.setVisible(figure.isVisible());
		    	  control.repaint();
		      }
		    });						
		}

		@Override
		public void componentMoved(ComponentEvent e) 
		{
		}

		@Override
		public void componentShown(ComponentEvent e) 
		{
		}

		@Override
		public void componentHidden(ComponentEvent e) 
		{
		}
	}
	
}
