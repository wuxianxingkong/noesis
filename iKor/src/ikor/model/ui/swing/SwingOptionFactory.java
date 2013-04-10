package ikor.model.ui.swing;

import ikor.model.ui.Label;
import ikor.model.ui.Option;
import ikor.model.ui.UIFactory;

import javax.swing.JButton;


public class SwingOptionFactory implements UIFactory<SwingUI,Option>
{
	
	@Override
	public void build (SwingUI ui, Option option) 
	{
		JButton jbutton = new JButton();
		Label   label = option.getLabel();
		
		jbutton.setActionCommand( option.getId() );
		jbutton.setText(label.getText());
		
		if (label.getDescription()!=null)
			jbutton.setToolTipText( label.getDescription() );

		if (label.getIcon()!=null)
			jbutton.setIcon( ui.loadIcon(label.getIcon()) );
		
		if (option.getShortcut()!=0)
			jbutton.setMnemonic( option.getShortcut() );

		jbutton.addActionListener(new SwingActionHandler(option.getAction()));
		ui.addComponent ( jbutton );	
	}	
}
