package noesis.ui.model;

import java.awt.Color;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.io.graphics.DefaultLinkRenderer;
import noesis.io.graphics.GradientNodeRenderer;
import noesis.io.graphics.NetworkRenderer;
import ikor.model.Subject;
import ikor.model.graphics.Drawing;
import ikor.model.graphics.DrawingElement;
import ikor.model.graphics.DrawingSelectionListener;
import ikor.model.graphics.DrawingTooltipProvider;
import ikor.model.graphics.DrawingUpdateListener;
import ikor.model.graphics.Style;
import ikor.model.ui.Figure;


public class NetworkFigure extends Figure<AttributeNetwork>
{	
	private NetworkModel data;
	private NetworkRenderer renderer;
	
	public static final int DEFAULT_SIZE = 500;

	public NetworkFigure (NetworkModel data) 
	{
		this.data = data;

		// Network renderer
		
		renderer = new NetworkRenderer( getNetwork(), DEFAULT_SIZE, DEFAULT_SIZE);
		
		renderer.setLinkRenderer(  new DefaultLinkRenderer () );
		renderer.setNodeRenderer( new GradientNodeRenderer() );

		render();
				
		// Event handling

		this.setTooltipProvider( new NetworkTooltipProvider() );
		this.setDraggingListener( new NetworkDraggingListener(this) );
		//this.setSelectionListener( new NetworkSelectionListener(this) );

		// Observer design pattern
		
		this.addObserver(data);
		data.addObserver(this);
	}
	
	
	// Network model
	
	public NetworkModel getData() 
	{
		return data;
	}

	public void setData(NetworkModel data) 
	{
		this.data = data;
	}
	
	public AttributeNetwork getNetwork ()
	{
		if (data!=null)
			return data.getNetwork();
		else
			return null;
	}
	
	public void setNetwork (AttributeNetwork network)
	{
		data.setNetwork(network);
		renderer.setNetwork(network);
	}
	
	// Network renderer
	
	public NetworkRenderer getRenderer() 
	{
		return renderer;
	}

	public void setRenderer(NetworkRenderer renderer) 
	{
		this.renderer = renderer;
	}
	
	public void render ()
	{
		renderer.render();
		
		super.setDrawing(renderer);		

		show();
	}

	// Subject/observer interface
	
	boolean updating = false;
	
	@Override
	public void update (Subject subject, AttributeNetwork object)
	{
		if (!updating) {			
			updating = true;
			
			if (object!=null) {
				setNetwork(object);
				notifyObservers(object);
			} else {
				notifyObservers ();
			}

			updating = false;
		}
	}
	
	// Event handling

	public class NetworkTooltipProvider implements DrawingTooltipProvider
	{
		@Override
		public String get(String id) 
		{
			return id;
		}
	}
	
	public class NetworkSelectionListener implements DrawingSelectionListener
	{
		Drawing drawing;
		Style   currentStyle;
		
		public NetworkSelectionListener (NetworkFigure figure)
		{
			this.drawing = figure.getDrawing();
		}

		@Override
		public void setSelection (String id) 
		{
			clearSelection();
			
			currentStyle = new Style ( new Color(0xff, 0x00, 0x00, 0x80), 3);
			
			if (drawing!=null)
				drawing.getDrawingElement(id).setBorder(currentStyle);
		}

		@Override
		public void addSelection (String id) 
		{
			if (currentStyle==null)
				currentStyle = new Style ( new Color(0xff, 0x00, 0x00, 0x80), 3);

			if (drawing!=null)
				drawing.getDrawingElement(id).setBorder(currentStyle);
		}

		@Override
		public void clearSelection() 
		{
			if (currentStyle!=null) {
				currentStyle.setWidth(0);
				currentStyle = null;
			}
		}
	}	
	
	
	
	public class NetworkDraggingListener implements DrawingUpdateListener
	{
		NetworkFigure  figure;
		
		public NetworkDraggingListener (NetworkFigure figure)
		{
			this.figure = figure;
		}
		
		@Override
		public void update (String id, int x, int y) 
		{
			DrawingElement current = figure.getDrawing().getDrawingElement(id);
			
			if (current.getId().startsWith("node")) {
				
				// Update node
					
				int node = Integer.parseInt(id.substring(4));
				
				System.err.println(figure.getData());
				System.err.println(figure.getData().getNetwork());
				
				AttributeNetwork net = figure.getData().getNetwork();
				
				Attribute<Double> xAttribute = net.getNodeAttribute("x");
				Attribute<Double> yAttribute = net.getNodeAttribute("y");
				
				xAttribute.set(node, ((double)x)/figure.getDrawing().getWidth() );
				yAttribute.set(node, ((double)y)/figure.getDrawing().getHeight() );
				
				figure.renderer.update(node);
						
				// Notify observers
				
				figure.notifyObservers(figure.getData().getNetwork());
			}
		}
		
	}	
}
