package noesis.io.graphics;

import ikor.model.graphics.io.DrawingWriter;
import ikor.model.graphics.io.DrawingWriterFactory;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import noesis.Network;
import noesis.io.NetworkWriter;

public class NetworkImageWriter implements NetworkWriter 
{
	private BufferedOutputStream writer;
	private NetworkRenderer renderer;
	private String format;
	
	public NetworkImageWriter (OutputStream writer, NetworkRenderer renderer, String format)
	{
		this.writer = new BufferedOutputStream(writer);
		this.renderer = renderer;
		this.format = format;
	}
	
	@Override
	public void write (Network net) throws IOException 
	{
		DrawingWriter dw = DrawingWriterFactory.create(renderer,format);
		BackgroundRenderer background = renderer.getBackgroundRenderer();
		
		renderer.setBackgroundRenderer(null);
		
		dw.write(writer);

		renderer.setBackgroundRenderer(background);
	}

	@Override
	public void close() throws IOException 
	{
		writer.close();
	}

}
