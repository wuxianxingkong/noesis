package sandbox.mdsd.ui.swing;

import javax.swing.SwingUtilities;

import sandbox.mdsd.Observer;
import sandbox.mdsd.Subject;
import sandbox.mdsd.data.Dataset;

// Observer design pattern

public class SwingDatasetObserver implements Observer<Dataset>
{
	private SwingDatasetModel tableModel;
	
	public SwingDatasetObserver (SwingDatasetModel tableModel)
	{
		this.tableModel = tableModel;
	}

	@Override
	public void update(Subject<Dataset> subject, Dataset object) 
	{
	    SwingUtilities.invokeLater(new Runnable() 
	    {
	      public void run()
	      {
	    	  tableModel.fireTableDataChanged();
	      }
	    });			
	}
}	
