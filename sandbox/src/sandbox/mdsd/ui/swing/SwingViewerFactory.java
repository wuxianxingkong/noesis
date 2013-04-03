package sandbox.mdsd.ui.swing;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

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

		
		JLabel data = new JLabel();
		
		data.setText( "<html>"+viewer.getValue().replace("\n","<br>")+"</html>" );
		data.setBorder( BorderFactory.createEtchedBorder() );
		data.setBackground( javax.swing.UIManager.getDefaults().getColor("info"));
		data.setOpaque(true);
		
		ui.addComponent( data );
		
	}	
}
