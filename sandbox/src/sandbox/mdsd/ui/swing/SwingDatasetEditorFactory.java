package sandbox.mdsd.ui.swing;

import javax.swing.JTable;
import javax.swing.JScrollPane;

import sandbox.mdsd.ui.DatasetEditor;
import sandbox.mdsd.ui.UIFactory;

public class SwingDatasetEditorFactory  implements UIFactory<SwingUI,DatasetEditor>
{
	@Override
	public void build(SwingUI ui, DatasetEditor editor) 
	{	
		SwingDatasetModel model;
		JTable table;
		
		model = new SwingDatasetModel(editor,true);
		table = new JTable(model);
				
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);  // AUTO_RESIZE_OFF enables horizontal scrolling when needed
		table.setAutoCreateRowSorter(true);
		
		model.setRenderers(table);
		model.setEditors(table);

		JScrollPane scroll = new JScrollPane(table);
		ui.addComponent ( scroll );	
	}

}
