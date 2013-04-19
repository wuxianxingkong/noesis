package noesis.ui.model.actions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.Network;
import noesis.algorithms.visualization.NetworkLayout;
import noesis.algorithms.visualization.NormalizedLayout;
import noesis.algorithms.visualization.RandomLayout;
import noesis.io.ASCIINetworkReader;
import noesis.io.GDFNetworkReader;
import noesis.io.GMLNetworkReader;
import noesis.io.GraphMLNetworkReader;
import noesis.io.NetworkReader;
import noesis.io.PajekNetworkReader;
import noesis.io.SNAPGZNetworkReader;
import noesis.io.SNAPNetworkReader;
import noesis.ui.model.NetworkViewerUIModel;
import ikor.math.Decimal;
import ikor.model.data.RealModel;
import ikor.model.ui.Action;
import ikor.model.ui.File;
import ikor.util.log.Log;

public class ViewerOpenAction extends Action 
{
	private NetworkViewerUIModel ui;
	private File file;
	
	public ViewerOpenAction (NetworkViewerUIModel ui)
	{
		this.ui = ui;
		this.file = new File(ui.getApplication(), "Open network...", "Open", new FileCommandAction() );
	}
		
	@Override
	public void run() 
	{
		file.getApplication().run(file);
	}
	
	
	public class FileCommandAction extends Action
	{
		@Override
		public void run() 
		{
			String filename = file.getUrl();
			
			if (filename!=null) {
				
				AttributeNetwork net = read(filename);
				
				Attribute<Double> x = net.getNodeAttribute("x");
				Attribute<Double> y = net.getNodeAttribute("y");
				
				if ((x==null) || (y==null)){
					
					RealModel coordinateModel = new RealModel();
					coordinateModel.setMinimumValue(0.0);
					coordinateModel.setMaximumValue(1.0);										
					
					x = new Attribute<Double>("x", coordinateModel);
					y = new Attribute<Double>("y", coordinateModel);
					
					net.addNodeAttribute(x);
					net.addNodeAttribute(y);
					
					NetworkLayout display = new RandomLayout ();
					
					display.layout(net);
					
				} else {
					
					NetworkLayout normalize = new NormalizedLayout ();
					
					normalize.layout(net);		
				}
				
				ui.set("network", net);
			}
		}
		
		
		private AttributeNetwork read (String url)
		{
			NetworkReader<String,Decimal> reader; 
			Network net = null;
			
			try {
			
				if (url.endsWith(".net"))
					reader = new PajekNetworkReader(new FileReader(url));
				else if (url.endsWith(".dat"))
					reader = new ASCIINetworkReader(new FileReader(url));
				else if (url.endsWith(".txt"))
					reader = new SNAPNetworkReader(new FileReader(url));
				else if (url.endsWith(".gz"))
					reader = new SNAPGZNetworkReader(new FileInputStream(url));
				else if (url.endsWith(".gml"))
					reader = new GMLNetworkReader(new FileReader(url));
				else if (url.endsWith(".graphml"))
					reader = new GraphMLNetworkReader(new FileInputStream(url));
				else if (url.endsWith(".gdf"))
					reader = new GDFNetworkReader(new FileReader(url));
				else
					throw new IOException("Unknown network file format.");

				reader.setType(noesis.ArrayNetwork.class);     // NDwww.net 5.2s @ i5
				// reader.setType(noesis.GraphNetwork.class);  // NDwww.net 9.6s @ i5

				net = reader.read();
				
				reader.close();
			
			} catch (FileNotFoundException fnfe) {
				
				Log.error("File not found - "+url);
				
			} catch (IOException ioe) {
				
				Log.error("IO error - "+ ioe);
			}
			
			if (net!=null) {
			
				if (net.getID()!=null)
					ui.message("Network '"+net.getID()+"' loaded with "+net.size()+" nodes and "+net.links()+" links.");
				else
					ui.message("Network loaded with "+net.size()+" nodes and "+net.links()+" links.");

				if (!(net instanceof AttributeNetwork))
					net = new AttributeNetwork(net);
				
			} else {
				
				ui.message("Unable to read network, sorry :-(");
			}
			
			return (AttributeNetwork) net;
		}
		
	}

}
