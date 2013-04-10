package ikor.model.ui.swing;

import ikor.math.Decimal;
import ikor.model.data.ColorModel;
import ikor.model.data.DateModel;
import ikor.model.data.DecimalModel;
import ikor.model.data.RealModel;
import ikor.model.ui.DatasetComponent;
import ikor.model.ui.DatasetEditor;
import ikor.model.ui.DatasetViewer;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Date;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;



public class SwingDatasetModel extends AbstractTableModel 
{
	private DatasetComponent component;
	private boolean editable;

	/**
	 * Constructor
	 * @param viewer   Dataset viewer component
	 * @param editable true if dataset is editable
	 */
	public SwingDatasetModel (DatasetViewer viewer, boolean editable) 
	{
		this.component = viewer;
		this.editable = editable;
	}

	/**
	 * Constructor
	 * @param editor   Dataset editor component
	 * @param editable true if dataset is editable
	 */
	public SwingDatasetModel (DatasetEditor editor, boolean editable) 
	{
		this.component = editor;
		this.editable = editable;
	}

	// Dataset size

	@Override
	public int getRowCount() 
	{
		return component.getData().getRowCount();
	}

	@Override
	public int getColumnCount() 
	{
		return component.getData().getColumnCount();
	}

	// Column information
	
	@Override
	public String getColumnName (int columnIndex)
	{
		return component.getHeader(columnIndex).getText();
	}
	
	@Override
	public Class getColumnClass (int columnIndex)
	{
		return component.getData().get(0,columnIndex).getClass();
	}
	
	// Data

	@Override
	public Object getValueAt (int rowIndex, int columnIndex) 
	{
		return component.getData().get(rowIndex, columnIndex);
	}
	
	@Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) 
	{
        component.getData().set(rowIndex, columnIndex, value);
        fireTableCellUpdated(rowIndex, columnIndex);
        component.notifyObservers( component.getData() );
    }	
	
	// Editable cell?
	
	public boolean isCellEditable(int row, int col) 
	{
		return editable;
	}


	// Component listener

	public static final int MINIMUM_WIDTH_PER_COLUMN = 50;
	public static final int PREFERRED_WIDTH_PER_COLUMN = 100;
	
	public JTable createTable ()
	{
		JTable table = new CustomJTable(this);
		
		// Layout
		
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);  
			// JTable.AUTO_RESIZE_OFF forces horizontal scrolling
			// JTable.AUTO_RESIZE_LAST_COLUMN only affects the last column
			// JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS does not modify columns at the left
		
		for (int i=0; i<getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setMinWidth(MINIMUM_WIDTH_PER_COLUMN);
			table.getColumnModel().getColumn(i).setPreferredWidth(PREFERRED_WIDTH_PER_COLUMN);
		}
	
		// Renderers & editors
		
		setupRenderers(table);
		setupEditors(table);
		
		return table;
	}
	
	public class CustomJTable extends JTable 
	{
		public CustomJTable (TableModel model)
		{
			super(model);
		}
		
		public boolean getScrollableTracksViewportWidth() 
		{  
			return getMinimumSize().width < getParent().getWidth();
		}  		
	}
	
	
	// Custom cell renderers
	
	public void setupRenderers (JTable table)
	{
		table.setDefaultRenderer(Float.class, new RealRenderer() );
		table.setDefaultRenderer(Double.class, new RealRenderer() );
		table.setDefaultRenderer(Decimal.class, new DecimalRenderer() );
		table.setDefaultRenderer(Date.class, new DateRenderer() );
		table.setDefaultRenderer(Color.class, new ColorRenderer() );
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
	
	public void setupEditors (JTable table)
	{
		table.setDefaultEditor(Date.class, new DateEditor() );
		table.setDefaultEditor(Color.class, new ColorEditor() );
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
