package sandbox.mdsd.ui.swing;

import javax.swing.JLabel;

import sandbox.mdsd.ui.Label;
import sandbox.mdsd.ui.UIFactory;

public class SwingLabelFactory implements UIFactory<SwingUI,Label>
{
	@Override
	public void build (SwingUI ui, Label label) 
	{
		JLabel jlabel = new JLabel();
		
		jlabel.setText( label.getText() );
				
		if (label.getDescription()!=null)
			jlabel.setToolTipText( label.getDescription() );

		if (label.getIcon()!=null)
			jlabel.setIcon( ui.loadIcon(label.getIcon()) );
		
		ui.addComponent ( jlabel );	
	}
}
