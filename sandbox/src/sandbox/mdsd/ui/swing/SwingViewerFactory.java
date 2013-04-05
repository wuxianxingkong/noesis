package sandbox.mdsd.ui.swing;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import sandbox.mdsd.Subject;
import sandbox.mdsd.Observer;

import sandbox.mdsd.data.DataModel;
import sandbox.mdsd.data.NumberModel;
import sandbox.mdsd.data.DateModel;

import sandbox.mdsd.ui.Label;
import sandbox.mdsd.ui.Viewer;
import sandbox.mdsd.ui.UIFactory;

public class SwingViewerFactory implements UIFactory<SwingUI,Viewer>
{
	
	@Override
	public void build (SwingUI ui, Viewer viewer) 
	{
		Label  label = viewer.getLabel();
		JLabel title = new JLabel();
		
		title.setText( label.getText() );
				
		if (label.getDescription()!=null)
			title.setToolTipText( label.getDescription() );

		if (label.getIcon()!=null)
			title.setIcon( ui.loadIcon(label.getIcon()) );
		
		ui.addComponent ( title );	

		
		JLabel    control = new JLabel();
		DataModel model = viewer.getModel();
		
		if ((model instanceof NumberModel) || (model instanceof DateModel)) {
			control.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		
		control.setText( "<html>"+viewer.getValue().replace("\n","<br>")+"</html>" );
		control.setBorder( BorderFactory.createEtchedBorder() );
		control.setBackground( javax.swing.UIManager.getDefaults().getColor("info"));
		control.setOpaque(true);
		
		viewer.addObserver(new LabelObserver(viewer, control));	
		
		ui.addComponent( control );
		
	}	
	
	
	// Observer design pattern
	
	public class LabelObserver implements Observer
	{
		private JLabel control;
		private Viewer viewer;
		
		public LabelObserver (Viewer viewer, JLabel control)
		{
			this.viewer = viewer;
			this.control = control;
		}

		@Override
		public void update(Subject o, Object arg) 
		{
		    SwingUtilities.invokeLater(new Runnable() 
		    {
		      public void run()
		      {
		    	  control.setText( "<html>"+viewer.getValue().replace("\n","<br>")+"</html>" );
		      }
		    });			
		}
	}	
}
