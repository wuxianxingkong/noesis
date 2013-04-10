package ikor.model.ui.swing;

import ikor.model.ui.Label;
import ikor.model.ui.UIFactory;

import javax.swing.JLabel;


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
