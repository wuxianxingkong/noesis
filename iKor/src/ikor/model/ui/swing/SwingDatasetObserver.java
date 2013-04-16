package ikor.model.ui.swing;

import ikor.model.Observer;
import ikor.model.Subject;
import ikor.model.data.Dataset;

import javax.swing.SwingUtilities;


// Observer design pattern

public class SwingDatasetObserver implements Observer<Dataset>
{
	private SwingDatasetModel tableModel;
	private SwingDatasetJTable table;
	
	public SwingDatasetObserver (SwingDatasetModel tableModel, SwingDatasetJTable table)
	{
		this.tableModel = tableModel;
		this.table = table;
	}

	@Override
	public void update(Subject<Dataset> subject, Dataset object) 
	{
	    SwingUtilities.invokeLater(new Runnable() 
	    {
	      public void run()
	      {
	    	  tableModel.fireTableStructureChanged();
	    	  tableModel.fireTableDataChanged();
	    	  table.setupLayout();
	    	  table.updateUI();
	      }
	    });			
	}
}	
