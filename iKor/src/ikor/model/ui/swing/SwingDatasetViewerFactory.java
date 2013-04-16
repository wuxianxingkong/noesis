package ikor.model.ui.swing;

import ikor.model.ui.DatasetViewer;
import ikor.model.ui.UIFactory;

import javax.swing.JScrollPane;


public class SwingDatasetViewerFactory implements UIFactory<SwingUI,DatasetViewer>
{
	
	@Override
	public void build(SwingUI ui, DatasetViewer viewer) 
	{	
		SwingDatasetModel model = new SwingDatasetModel(viewer,false);
		SwingDatasetJTable table = new SwingDatasetJTable(model);
		JScrollPane scroll = new JScrollPane(table);
		
		viewer.addObserver(new SwingDatasetObserver(model,table));
		
		ui.addComponent ( scroll );	
	}	
	
}
