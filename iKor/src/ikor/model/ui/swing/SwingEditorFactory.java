package ikor.model.ui.swing;

import ikor.model.Observer;
import ikor.model.Subject;
import ikor.model.data.BooleanModel;
import ikor.model.data.ColorModel;
import ikor.model.data.DataModel;
import ikor.model.data.PasswordModel;
import ikor.model.data.TextModel;
import ikor.model.ui.Editor;
import ikor.model.ui.Label;
import ikor.model.ui.UIFactory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;




public class SwingEditorFactory implements UIFactory<SwingUI,Editor>
{
	
	@Override
	public void build (SwingUI ui, Editor editor) 
	{
		DataModel model = editor.getModel();
		JComponent control;
		
		if (model instanceof BooleanModel)
			control = createCheckBox(ui,editor);
		else
			control = createTextControl(ui,editor);
		
		ui.addComponent( control );
		
	}	
	
	// CheckBox
	// --------
	
	public JCheckBox createCheckBox (SwingUI ui, Editor editor)
	{
		Label label = editor.getLabel();
		JCheckBox control = new JCheckBox();
		
		control.setText( label.getText() );
		
		if (label.getDescription()!=null)
			control.setToolTipText( label.getDescription() );
		
		if (label.getIcon()!=null)
			control.setIcon( ui.loadIcon(label.getIcon()) );		
		
		
		CheckBoxMutator  mutator  = new CheckBoxMutator(editor, control);
		CheckBoxObserver observer = new CheckBoxObserver(mutator);
		CheckBoxListener listener = new CheckBoxListener(mutator);
		
		editor.addObserver(observer);	
		control.addItemListener(listener);
		
		return control;
	}
	
	// CheckBox update
	
	class CheckBoxMutator implements Runnable
	{
		Editor<Boolean> editor;
		JCheckBox       control;
		boolean         updating = false;		
		
		public CheckBoxMutator (Editor editor, JCheckBox control)
		{
			this.editor = editor;
			this.control = control;
		}

		public void updateEditor ()
		{
			if (!updating) {				
				updating = true;
				editor.setValue(Boolean.valueOf(control.isSelected()).toString());				
				updating = false;
			}
		}		
		
		public void updateControl ()
		{
			if (!updating) {
				updating = true;
				control.setSelected(Boolean.valueOf(editor.getValue()));
				updating = false;
			}
		}
		
		// for SwingUtilities.invokeLater... 
		
		public void run()
		{
			updateControl();
		}		
	}
	
	// CheckBox event listener
	
	class CheckBoxListener implements ItemListener
	{
		CheckBoxMutator mutator;
		
		public CheckBoxListener (CheckBoxMutator mutator)
		{
			this.mutator = mutator;
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) 
		{
			mutator.updateEditor();
		}			
	}		
	
	// Observer design pattern
	
	public class CheckBoxObserver implements Observer
	{
		private CheckBoxMutator mutator;
		
		public CheckBoxObserver (CheckBoxMutator mutator)
		{
			this.mutator = mutator;
		}
		
		@Override
		public void update (Subject o, Object arg) 
		{
			if (SwingUtilities.isEventDispatchThread()) {
				mutator.updateControl();
			} else {
				SwingUtilities.invokeLater(mutator);
			}
		}
	}
	
	
	// Text control
	// ------------
	
	public JComponent createTextControl (SwingUI ui, Editor editor)
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
	    } else if (model instanceof ColorModel) {
	    	control = new JColorTextField();
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
			return control;
		} else { // JTextArea
			control.addKeyListener( listener );
			return scrollPane;
		}		
	}
	
	// Text control update
	
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
	
	// Text field event handling
	
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
		public void update (Subject o, Object arg) 
		{
			if (SwingUtilities.isEventDispatchThread()) {
				mutator.updateControl();
			} else {
				SwingUtilities.invokeLater(mutator);
			}
		}
	}
	
	
	/**
	 * Custom color editor
	 */
	
	public class JColorTextField extends JTextField
	{
		ColorModel model = new ColorModel();
		
		public Color getColor ()
		{
			Color color = model.fromString(getText());
			
			if (color==null)
				color = Color.black;
			
			return color;
		}
		
		@Override
		protected void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			
			g.setColor(getColor());
			g.fillRect(getWidth()-24,3,20, getHeight()-7);
			g.setColor(Color.black);
			g.drawRect(getWidth()-24,3,20, getHeight()-7);
		}
		
	}
	
}
