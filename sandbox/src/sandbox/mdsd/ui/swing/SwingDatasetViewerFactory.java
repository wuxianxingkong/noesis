package sandbox.mdsd.ui.swing;

import javax.swing.JTable;
import javax.swing.JScrollPane;

import sandbox.mdsd.ui.DatasetViewer;
import sandbox.mdsd.ui.UIFactory;

public class SwingDatasetViewerFactory implements UIFactory<SwingUI,DatasetViewer>
{
	
	@Override
	public void build(SwingUI ui, DatasetViewer viewer) 
	{	
		SwingDatasetModel model = new SwingDatasetModel(viewer,false);
		JTable table = model.createTable();
		JScrollPane scroll = new JScrollPane(table);
		
		ui.addComponent ( scroll );	
	}
	
	
	
}
