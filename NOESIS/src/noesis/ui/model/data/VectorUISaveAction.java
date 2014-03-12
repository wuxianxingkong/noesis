package noesis.ui.model.data;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import ikor.math.Vector;
import ikor.model.graphics.io.DrawingWriter;
import ikor.model.graphics.io.DrawingWriterFactory;

import ikor.model.ui.Action;
import ikor.model.ui.Figure;
import ikor.model.ui.File;

import ikor.util.log.Log;


public class VectorUISaveAction extends Action 
{
	private VectorUIModel ui;
	private String format;
	private File file;
	
	public VectorUISaveAction (VectorUIModel ui, String format)
	{
		this.ui = ui;
		this.format = format;
		this.file = new File(ui.getApplication(), "Save histogram...", "Save", new FileCommandAction() );
	}
		
	@Override
	public void run() 
	{
		file.setUrl("histogram."+format);
		file.getApplication().run(file);
	}
	
	
	public class FileCommandAction extends Action
	{
		@Override
		public void run() 
		{
			String filename = file.getUrl();
			
			if (!checkFilename(filename))
				filename = null;
			
			if (filename!=null) {
				
				try {
					
					if ( format.equals("txt") || format.equals("csv") )
						saveData(filename, ui.getData(), format);
					else if ( format.equals("png") || format.equals("jpg") || format.equals("svg") )
						saveFigure(filename, ui.getFigure(), format);
					else
						throw new IOException("Unknown file format.");

				} catch (IOException ioe) {
					Log.error("IO error - "+ioe);
				}
				
			}
		}
		
		private boolean checkFilename (String filename) 
		{
			if (filename!=null) {
				
				java.io.File file = new java.io.File(filename);
				
				if (file.exists())
					return ui.confirm("Overwrite existing file?");
				else
					return true;
				
			} else {
				return false;
			}
		}

		private void saveData (String filename, Vector data, String format)
				throws IOException
		{
			FileWriter output = new FileWriter(filename);
			BufferedWriter writer = new BufferedWriter(output);

			for (int i=0; i<data.size(); i++)
				writer.write(data.get(i)+"\n");

			writer.close();
		}
		
		private void saveFigure (String filename, Figure figure, String format)
			throws IOException
		{
			FileOutputStream stream = new FileOutputStream(filename);
			BufferedOutputStream writer = new BufferedOutputStream(stream);
			DrawingWriter dw = DrawingWriterFactory.create(figure.getDrawing(),format);

			dw.write(writer);
			writer.close();
		}
		
		
	}

}
