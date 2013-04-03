package sandbox.mdsd.ui.swing;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import sandbox.mdsd.ui.Label;
import sandbox.mdsd.ui.Editor;
import sandbox.mdsd.ui.UIFactory;

public class SwingEditorFactory implements UIFactory<SwingUI,Editor>
{
	
	@Override
	public void build (SwingUI ui, Editor editor) 
	{
		Label  label = editor.getLabel();
		JLabel title = new JLabel();
		
		title.setText( label.getText() );
				
		if (label.getDescription()!=null)
			title.setToolTipText( label.getDescription() );

		if (label.getIcon()!=null)
			title.setIcon( ui.loadIcon(label.getIcon()) );
		
		ui.addComponent ( title );	

		JTextComponent control = null;
		JScrollPane    scrollPane = null; 
		
		if (editor.isMultiline()) {
			control = new JTextArea();
			scrollPane = new JScrollPane(control);
		} else if (editor.isPassword()) {
			control = new JPasswordField();
	    } else {
			control = new JTextField();
	    }
		
		control.setText( editor.getValue() );
		control.setFont( title.getFont() );
		control.setBackground( javax.swing.UIManager.getDefaults().getColor("TextField.background"));
		control.setEditable(true);
		
		TextFieldListener listener = new TextFieldListener(editor, control);
		
		if (control instanceof JTextField) {
			control.getDocument().addDocumentListener(listener);
			ui.addComponent( control );
		} else { // JTextArea
			control.addKeyListener( listener );
			ui.addComponent( scrollPane );
		}
		
		
	}	
	
	// Event handling
	
	class TextFieldListener implements DocumentListener, KeyListener
	{
		Editor         editor;
		JTextComponent control;
		
		public TextFieldListener (Editor editor, JTextComponent control)
		{
			this.editor = editor;
			this.control = control;
		}

		public void update ()
		{ 
			if (editor.setValue(control.getText()))
				control.setForeground( Color.black );
			else
				control.setForeground( Color.red );
		}

		@Override
		public void insertUpdate(DocumentEvent e) 
		{
			update();
		}

		@Override
		public void removeUpdate(DocumentEvent e) 
		{
			update();
		}

		@Override
		public void changedUpdate(DocumentEvent e) 
		{
			update();
		}

		@Override
		public void keyTyped(KeyEvent e) 
		{
			update();			
		}

		@Override
		public void keyPressed(KeyEvent e) 
		{
			update();
		}

		@Override
		public void keyReleased(KeyEvent e) 
		{
			update();
		}
	}	
}
