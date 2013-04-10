package ikor.model.ui.swing;

import ikor.model.ui.DatasetEditor;
import ikor.model.ui.UIFactory;

import javax.swing.JTable;
import javax.swing.JScrollPane;


public class SwingDatasetEditorFactory  implements UIFactory<SwingUI,DatasetEditor>
{
	@Override
	public void build(SwingUI ui, DatasetEditor editor) 
	{	
		SwingDatasetModel model = new SwingDatasetModel(editor,true);
		JTable table = model.createTable();
		JScrollPane scroll = new JScrollPane(table);
		
		editor.addObserver(new SwingDatasetObserver(model));
		
		ui.addComponent ( scroll );	
	}

}
