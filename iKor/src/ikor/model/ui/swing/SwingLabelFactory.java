package ikor.model.ui.swing;

import java.awt.Desktop;

import ikor.model.ui.Label;
import ikor.model.ui.UIFactory;

import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


public class SwingLabelFactory implements UIFactory<SwingUI,Label>
{
	@Override
	public void build (SwingUI ui, Label label) 
	{
		JTextPane jlabel = new JTextPane();
		
		jlabel.addHyperlinkListener(new LabelHyperlinkListener());
	    jlabel.setEditable(false);
	    jlabel.setOpaque(false);
	    jlabel.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
	    
	    jlabel.setContentType("text/html");
	    jlabel.setText(label.getText());
		
		if (label.getDescription()!=null)
			jlabel.setToolTipText( label.getDescription() );

		//if (label.getIcon()!=null)
		//	jlabel.setIcon( ui.loadIcon(label.getIcon()) );
		
		ui.addComponent ( jlabel );	
	}
	
	// Hyperlink listeners
	
	class LabelHyperlinkListener implements HyperlinkListener
	{
		@Override
		public void hyperlinkUpdate(HyperlinkEvent event) 
		{
		    if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		    	
		    	if (Desktop.isDesktopSupported()) {
		    	      try {
		    	        Desktop.getDesktop().browse(event.getURL().toURI());
		    	      } catch (Exception error) {
		    	      }
		    	}
		    }			
		}
		
	}
}
