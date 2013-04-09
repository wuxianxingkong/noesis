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
		SwingDatasetModel model = new SwingDatasetModel(editor,true);
		JTable table = model.createTable();
		JScrollPane scroll = new JScrollPane(table);
		
		editor.addObserver(new SwingDatasetObserver(model));
		
		ui.addComponent ( scroll );	
	}

}
