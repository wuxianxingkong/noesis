package noesis.ui.model.actions;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import noesis.AttributeNetwork;

import noesis.io.GDFNetworkWriter;
import noesis.io.GMLNetworkWriter;
import noesis.io.GraphMLNetworkWriter;
import noesis.io.NetworkWriter;
import noesis.io.PajekNetworkWriter;
import noesis.io.graphics.NetworkImageWriter;

import noesis.ui.model.NetworkAnalyzerUIModel;

import ikor.model.ui.Action;
import ikor.model.ui.File;

import ikor.util.log.Log;


public class AnalyzerSaveAction extends Action 
{
	private NetworkAnalyzerUIModel ui;
	private String format;
	private File file;
	
	public AnalyzerSaveAction (NetworkAnalyzerUIModel ui, String format)
	{
		this.ui = ui;
		this.format = format;
		this.file = new File(ui.getApplication(), "Save network...", "Save", new FileCommandAction() );
	}
		
	@Override
	public void run() 
	{
		file.setUrl("network."+format);
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
				
				AttributeNetwork net = (AttributeNetwork) ui.get("network");
				NetworkWriter writer;
				
				try {
					
					if (format.equals("gdf"))
						writer = new GDFNetworkWriter(new FileWriter(filename));
					else if (format.equals("gml"))
						writer = new GMLNetworkWriter(new FileWriter(filename));
					else if (format.equals("graphml"))
						writer = new GraphMLNetworkWriter(new FileWriter(filename));
					else if (format.equals("pajek"))
						writer = new PajekNetworkWriter(new FileWriter(filename));
					else if (format.equals("png"))
						writer = new NetworkImageWriter(new FileOutputStream(filename), ui.getFigure().getRenderer(), "png");
					else if (format.equals("jpg"))
						writer = new NetworkImageWriter(new FileOutputStream(filename), ui.getFigure().getRenderer(), "jpg");						
					else if (format.equals("svg"))
						writer = new NetworkImageWriter(new FileOutputStream(filename), ui.getFigure().getRenderer(), "svg");						
					else
						throw new IOException("Unknown output network file format.");

					if (writer!=null) {
						writer.write(net);
						writer.close();
					}

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
		
	}

}
