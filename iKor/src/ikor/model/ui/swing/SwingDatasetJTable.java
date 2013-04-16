package ikor.model.ui.swing;

import ikor.math.Decimal;
import ikor.model.data.ColorModel;
import ikor.model.data.DateModel;
import ikor.model.data.DecimalModel;
import ikor.model.data.RealModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class SwingDatasetJTable extends JTable 
{
	public static final int MINIMUM_WIDTH_PER_COLUMN = 50;
	public static final int PREFERRED_WIDTH_PER_COLUMN = 100;
	
	
	public SwingDatasetJTable (TableModel model)
	{
		super(model);
		
		// JTable options
		
		setFillsViewportHeight(true);
		setAutoCreateRowSorter(true);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
			// JTable.AUTO_RESIZE_OFF forces horizontal scrolling
			// JTable.AUTO_RESIZE_LAST_COLUMN only affects the last column
			// JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS does not modify columns at the left

		// Layout
		
		setupLayout();
		
		// Renderers & editors
		
		setupRenderers();
		setupEditors();
	}
	
	// Layout
	
	public void setupLayout ()
	{
		for (int i=0; i<getColumnCount(); i++) {
			getColumnModel().getColumn(i).setMinWidth(MINIMUM_WIDTH_PER_COLUMN);
			getColumnModel().getColumn(i).setPreferredWidth(PREFERRED_WIDTH_PER_COLUMN);
		}
	}
	
	public boolean getScrollableTracksViewportWidth() 
	{  
		return getMinimumSize().width < getParent().getWidth();
	}  		
	
	
	// Custom cell renderers
	
	public void setupRenderers ()
	{
		setDefaultRenderer(Float.class, new RealRenderer() );
		setDefaultRenderer(Double.class, new RealRenderer() );
		setDefaultRenderer(Decimal.class, new DecimalRenderer() );
		setDefaultRenderer(Date.class, new DateRenderer() );
		setDefaultRenderer(Color.class, new ColorRenderer() );
	}

	public class RealRenderer extends DefaultTableCellRenderer 
	{
		RealModel model = new RealModel();
		
	    public void setValue (Object value) 
	    {
	    	setHorizontalAlignment(SwingConstants.RIGHT);
	        setText( (value == null) ? "" : model.toString((Number)value) );
	    }
	}		

	public class DecimalRenderer extends DefaultTableCellRenderer 
	{
		DecimalModel model = new DecimalModel();
		
	    public void setValue (Object value) 
	    {
	    	setHorizontalAlignment(SwingConstants.RIGHT);
	        setText( (value == null) ? "" : model.toString((Number)value) );
	    }
	}		
	
	/**
	 * Custom date renderer
	 */
	public class DateRenderer extends DefaultTableCellRenderer 
	{
		DateModel model = new DateModel();
		
	    public void setValue (Object value) 
	    {
	    	setHorizontalAlignment(SwingConstants.RIGHT);
	    	
	    	if (value!=null) {
	    		
	    		if (value instanceof Date)
	    			setText (model.toString((Date)value));	    		
	    	}
	    }
	}		

	/**
	 * Custom color renderer
	 */
	public class ColorRenderer extends JLabel implements TableCellRenderer 
	{
		Border unselectedBorder = null;
		Border selectedBorder = null;
		ColorModel model = new ColorModel();

		public ColorRenderer () 
		{
			setOpaque(true); //MUST do this for background to show up.
		}

		public Component getTableCellRendererComponent (JTable table, Object object, 
				boolean isSelected, boolean hasFocus, int row, int column) 
		{
			Color color = (Color) object;
			
			setBackground(color);
			
			if (isSelected) {
				if (selectedBorder == null) {
					selectedBorder = BorderFactory.createMatteBorder(2,5,2,5,table.getSelectionBackground());
				}
				setBorder(selectedBorder);
			} else {
				if (unselectedBorder == null) {
					unselectedBorder = BorderFactory.createMatteBorder(2,5,2,5,table.getBackground());
				}
				setBorder(unselectedBorder);
			}

			setToolTipText("RGB value: " + model.toString(color));
			return this;
		}
	}
	
	
	// Custom cell editors
	
	public void setupEditors ()
	{
		setDefaultEditor(Date.class, new DateEditor() );
		setDefaultEditor(Color.class, new ColorEditor() );
	}
	
	/**
	 * Custom date editor
	 */

	public class DateEditor extends DefaultCellEditor implements TableCellEditor 
	{
		private final DateModel model = new DateModel();
		private final Border red = new LineBorder(Color.red);
	    private final Border black = new LineBorder(Color.black);
	    	    
		public DateEditor ()
		{
			super(new JTextField());
		}
		
		// TableCellEditor interface
		
		public Object getCellEditorValue() 
		{
			JTextField control = (JTextField) getComponent();
			Date       date = model.fromString(control.getText());
			
			return date;
		}
		
		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) 
		{
			JTextField control = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
			
			if (value!=null) {	
				if (value instanceof Date) {
			        control.setText( model.toString((Date)value) );
				}
			}
			
			control.setBorder(black);
			return control; 
		}
		
		@Override
		public boolean stopCellEditing() 
		{
			JTextField control = (JTextField) getComponent();
			Date date = null;

			date = model.fromString(control.getText());
			
			if (date==null) {
				control.setBorder(red);
				return false;
			} else {
				return super.stopCellEditing();
			}
		}		
	}
	
	/**
	 * Custom color editor
	 */
	
	public class ColorEditor extends AbstractCellEditor implements TableCellEditor, ActionListener 
	{
		Color currentColor;
		JButton button;
		JColorChooser colorChooser;
		JDialog dialog;

		public ColorEditor() 
		{
			//Set up the editor (from the table's point of view), which is a button...
			//This button brings up the color chooser dialog, which is the editor from the user's point of view.
			
			button = new JButton();
			button.setActionCommand(EDIT);
			button.addActionListener(this);
			button.setBorderPainted(false);

			//Set up the dialog that the button brings up.
			colorChooser = new JColorChooser();
			dialog = JColorChooser.createDialog ( button, 
					"Color selection...", 
					true,  //modal
					colorChooser,
					this,  //OK button handler
					null); //no CANCEL button handler
		}

		// Event handling
		
		private static final String EDIT = "edit";
		
		public void actionPerformed (ActionEvent e) 
		{
			if (EDIT.equals(e.getActionCommand())) {
				// Show dialog
				button.setBackground(currentColor);
				colorChooser.setColor(currentColor);
				dialog.setVisible(true);
				fireEditingStopped(); // for the renderer
			} else { 
				// Color selection
				currentColor = colorChooser.getColor();
			}
		}

		// TableCellEditor interface
		
		public Object getCellEditorValue() 
		{
			return currentColor;
		}

		public Component getTableCellEditorComponent ( JTable table, Object value,
				boolean isSelected, int row, int column) 
		{
			currentColor = (Color)value;
			return button;
		}
	}	
	
}