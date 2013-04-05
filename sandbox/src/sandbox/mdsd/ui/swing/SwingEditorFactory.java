package sandbox.mdsd.ui.swing;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import sandbox.mdsd.data.DataModel;
import sandbox.mdsd.data.TextModel;
import sandbox.mdsd.data.PasswordModel;

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
		
		DataModel model = editor.getModel();
		
		
		if ( (model instanceof TextModel) && ((TextModel)model).isMultiline()) {
			control = new JTextArea();
			scrollPane = new JScrollPane(control);
		} else if (model instanceof PasswordModel) {
			control = new JPasswordField();
	    } else {
			control = new JTextField();
	    }
		
		control.setText( editor.getValue() );
		control.setFont( title.getFont() );
		control.setBackground( javax.swing.UIManager.getDefaults().getColor("TextField.background"));
		control.setEditable(true);
		
		TextFieldMutator  mutator  = new TextFieldMutator(editor, control);
		TextFieldObserver observer = new TextFieldObserver(mutator);
		TextFieldListener listener = new TextFieldListener(mutator);
		
		editor.addObserver(observer);
		
		if (control instanceof JTextField) {
			control.getDocument().addDocumentListener(listener);
			ui.addComponent( control );
		} else { // JTextArea
			control.addKeyListener( listener );
			ui.addComponent( scrollPane );
		}
		
		
	}	
	
	
	class TextFieldMutator implements Runnable
	{
		Editor         editor;
		JTextComponent control;
		boolean        updating = false;		
		
		public TextFieldMutator (Editor editor, JTextComponent control)
		{
			this.editor = editor;
			this.control = control;
		}

		public void updateEditor ()
		{
			if (!updating) {
				
				updating = true;

				if (editor.setValue(control.getText()))
					control.setForeground( Color.black );
				else
					control.setForeground( Color.red );
				
				updating = false;
			}
		}		
		
		public void updateControl ()
		{
			if (!updating) {
				
				updating = true;
			
				control.setText(editor.getValue());
				
				updating = false;
			}
		}
		
		
		// for SwingUtilities.invokeLater... 
		
		public void run()
		{
			updateControl();
		}		
	}
	
	// Event handling
	
	class TextFieldListener implements DocumentListener, KeyListener
	{
		TextFieldMutator mutator;
		
		public TextFieldListener (TextFieldMutator mutator)
		{
			this.mutator = mutator;
		}

		@Override
		public void insertUpdate(DocumentEvent e) 
		{
			mutator.updateEditor();
		}

		@Override
		public void removeUpdate(DocumentEvent e) 
		{
			mutator.updateEditor();
		}

		@Override
		public void changedUpdate(DocumentEvent e) 
		{
			mutator.updateEditor();
		}

		@Override
		public void keyTyped(KeyEvent e) 
		{
			mutator.updateEditor();			
		}

		@Override
		public void keyPressed(KeyEvent e) 
		{
			// mutator.updateEditor();
		}

		@Override
		public void keyReleased(KeyEvent e) 
		{
			// mutator.updateEditor();
		}
	}	
	
	
	// Observer design pattern
	
	public class TextFieldObserver implements Observer
	{
		private TextFieldMutator mutator;
		
		public TextFieldObserver (TextFieldMutator mutator)
		{
			this.mutator = mutator;
		}
		

		@Override
		public void update(Observable o, Object arg) 
		{
			if (SwingUtilities.isEventDispatchThread()) {
				mutator.updateControl();
			} else {
				SwingUtilities.invokeLater(mutator);
			}
		}
	}
	
	
}
