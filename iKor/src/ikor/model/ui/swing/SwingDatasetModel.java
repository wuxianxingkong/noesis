package ikor.model.ui.swing;

import ikor.model.ui.DatasetComponent;
import ikor.model.ui.DatasetEditor;
import ikor.model.ui.DatasetViewer;

import javax.swing.table.AbstractTableModel;



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
		Class type = null;
		Object data;
		
		for (int i=0; (type==null) && (i<getRowCount()); i++) {
			data = component.getData().get(i,columnIndex);
					
			if (data!=null)
				type = data.getClass();
		}
			
		return type;
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
	
}
