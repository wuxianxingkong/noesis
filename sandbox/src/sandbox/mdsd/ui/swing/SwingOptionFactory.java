package sandbox.mdsd.ui.swing;

import javax.swing.JButton;

import sandbox.mdsd.ui.Option;
import sandbox.mdsd.ui.Label;
import sandbox.mdsd.ui.UIFactory;

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
