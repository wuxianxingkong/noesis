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
		SwingDatasetModel model;
		JTable table;
		
		model = new SwingDatasetModel(viewer,false);
		table = new JTable(model);
		
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);  // AUTO_RESIZE_OFF enables horizontal scrolling when needed
		table.setAutoCreateRowSorter(true);
		
		model.setRenderers(table);

		JScrollPane scroll = new JScrollPane(table);
		
		ui.addComponent ( scroll );	
	}

}
