package noesis.io.graphics;

import ikor.model.graphics.swing.JDrawingComponent;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import noesis.Network;
import noesis.io.NetworkWriter;

public class ImageNetworkWriter implements NetworkWriter 
{
	private BufferedOutputStream writer;
	private NetworkRenderer renderer;
	private String format;
	
	public ImageNetworkWriter (OutputStream writer, NetworkRenderer renderer, String format)
	{
		this.writer = new BufferedOutputStream(writer);
		this.renderer = renderer;
		this.format = format;
	}	
	
	@Override
	public void write (Network net) throws IOException 
	{
		JDrawingComponent component = new JDrawingComponent(renderer);
		BackgroundRenderer background = renderer.getBackgroundRenderer();
		
		renderer.setBackgroundRenderer(null);
		
		component.setSize( renderer.getWidth(), renderer.getHeight() );
		component.save(writer, format);
		
		renderer.setBackgroundRenderer(background);
	}

	@Override
	public void close() throws IOException 
	{
		writer.close();
	}

}
