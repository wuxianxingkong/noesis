package ikor.model.ui.swing;

import ikor.model.Observer;
import ikor.model.Subject;
import ikor.model.data.ColorModel;
import ikor.model.data.DataModel;
import ikor.model.data.DateModel;
import ikor.model.data.NumberModel;
import ikor.model.ui.Label;
import ikor.model.ui.UIFactory;
import ikor.model.ui.Viewer;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;




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
		} else if (model instanceof ColorModel) {
			control.setHorizontalAlignment(SwingConstants.CENTER);
		}
		
		control.setBackground( javax.swing.UIManager.getDefaults().getColor("info"));
		control.setOpaque(true);
		
		if (viewer.getData() instanceof Color) {
			control.setBorder( BorderFactory.createMatteBorder(2,5,2,5,ui.getBackground()) );
		} else {
			control.setBorder( BorderFactory.createEtchedBorder() );
		}

		updateLabel(viewer,control);
		
		viewer.addObserver(new LabelObserver(viewer, control));	
		
		ui.addComponent( control );
		
	}	
	
	public static void updateLabel (Viewer viewer, JLabel control)
	{
		if (viewer.getData() instanceof Color) {
			Color color = (Color) viewer.getData();
			control.setBackground(color);
			// YIQ color space to enhance contrast...
			control.setForeground( (((color.getRed()*299)+(color.getGreen()*587)+(color.getBlue()*114))/1000>=128) ? Color.black: Color.white );
		}
		
		control.setText( "<html>"+viewer.getValue().replace("\n","<br>")+"</html>" );		
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
		    	  updateLabel(viewer,control);
		      }
		    });			
		}
	}	
}
