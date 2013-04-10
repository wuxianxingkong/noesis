package sandbox.mdsd.test;

import ikor.model.Subject;
import ikor.model.data.DataModel;
import ikor.model.data.Dataset;
import ikor.model.data.IntegerModel;
import ikor.model.graphics.Circle;
import ikor.model.graphics.Drawing;
import ikor.model.graphics.DrawingElement;
import ikor.model.graphics.DrawingSelectionListener;
import ikor.model.graphics.DrawingTooltipProvider;
import ikor.model.graphics.DrawingUpdateListener;
import ikor.model.graphics.Line;
import ikor.model.graphics.Style;
import ikor.model.graphics.styles.Gradient;
import ikor.model.graphics.styles.GradientKeyframe;
import ikor.model.graphics.styles.LinearGradient;
import ikor.model.graphics.styles.RadialGradient;
import ikor.model.ui.Application;
import ikor.model.ui.DatasetComponent;
import ikor.model.ui.DatasetEditor;
import ikor.model.ui.Figure;
import ikor.model.ui.UIModel;
import ikor.util.log.Log;

import java.awt.Color;



public class FigureTestModel extends UIModel
{
	public FigureTestModel (Application app)
	{
		super(app, "Network viewer..." );

		setIcon( TestApplication.url("chart.png") );

		
		NetworkMVCData mvc = new NetworkMVCData();
		
		NetworkDataset dataset = new NetworkDataset(13);

		mvc.setDataset( dataset );
	
		Figure figure = new NetworkFigure (mvc);
		
		DatasetEditor table = new DatasetEditor("Dataset editor", mvc, dataset.getModel());
		// DatasetViewer table = new DatasetViewer("Dataset viewer", mvc, dataset.getModel());
		
		setupDatasetComponent(table,dataset);
		
		
		UIModel panel = new UIModel(app);
		panel.add(figure);
		panel.add(table);
		
		this.add(panel);
		
	}
	
	public void setupDatasetComponent (DatasetComponent component, NetworkDataset dataset)
	{
		component.setData(dataset);

		for (int i=0; i<dataset.getColumnCount(); i++)
			component.addHeader( dataset.getName(i));
	}

	
	// Model in MVC
	
	class NetworkMVCData extends Subject<Dataset>
	{
		NetworkDataset dataset;
		
		public NetworkDataset getDataset() 
		{
			return dataset;
		}

		public void setDataset(NetworkDataset dataset) 
		{
			this.dataset = dataset;
		
			notifyObservers(dataset);
		}
		
		@Override
		public void update (Subject subject, Dataset object) 
		{
			Log.info("MVC dataset - "+object+" @ "+subject);
			
			if (object!=null)
				setDataset( (NetworkDataset) object );
			else
				notifyObservers();
		}
	}
	
	// Dataset
	
	public class NetworkDataset extends Dataset
	{
		int data[][];
		
		DataModel models[] = new DataModel[] {
		    new IntegerModel(),
		    new IntegerModel(),
		};
		
		String names[] = new String[] { 
			"X", 
			"Y", 
		};

		private final static int X_FIELD = 0;
		private final static int Y_FIELD = 1;
		
		
		public NetworkDataset (int K)
		{
			data = new int[K][2];
			
			for (int i=1; i<=K; i++) {
				data[i-1][X_FIELD] = (int) Math.round( 300 + 200*Math.cos(i*2*Math.PI/K));
				data[i-1][Y_FIELD] = (int) Math.round( 300 + 200*Math.sin(i*2*Math.PI/K));
			}
		}

		public String getName(int column) 
		{
			return names[column];
		}
		
		@Override
		public DataModel getModel(int column) 
		{
			return models[column];
		}

		@Override
		public Object get(int row, int column) 
		{
			return data[row][column];
		}

		@Override
		public void set(int row, int column, Object object) 
		{
			data[row][column] = (int) object;
		}

		@Override
		public int getColumnCount() 
		{
			return models.length;
		}

		@Override
		public int getRowCount() 
		{
			if (data!=null)
				return data.length;
			else
				return 0;
		}

		
		public int getX (int node)
		{
			return data[node][X_FIELD];
		}
		
		public int getY (int node)
		{
			return data[node][Y_FIELD];
		}
		
		public void setCoordinates (int node, int x, int y)
		{
			data[node][X_FIELD] = x;
			data[node][Y_FIELD] = y;
		}
		
	}
	
	
	// Figure
	
	public class NetworkFigure extends Figure
	{	
		private NetworkMVCData data;
		
		private boolean flat = false;
		private boolean radial = true;
		
		public NetworkFigure (NetworkMVCData data) 
		{
			this.setData(data);
			
			// Network drawing
			
			Drawing drawing = createCompleteNetwork(data.getDataset());
			
			super.setDrawing(drawing);
			
			// Event handling
			
			this.setTooltipProvider( new NetworkTooltipProvider() );
			this.setDraggingListener( new NetworkDraggingListener(this) );
			this.setSelectionListener( new NetworkSelectionListener(drawing) );

			// Observer design pattern
			
			this.addObserver(data);
			data.addObserver(this);
		}
		
		public NetworkMVCData getData() 
		{
			return data;
		}

		public void setData(NetworkMVCData data) 
		{
			this.data = data;
		}

		// Subject/Observer interface
		
		boolean updating = false;
		
		@Override
		public void update (Subject subject, Object object)
		{
			if (!updating) {
				
				updating = true;
				
				NetworkDataset data = (NetworkDataset) object;
				
				for (int i=1; i<=data.getRowCount(); i++) {
					moveNode(i);
				}
				
				notifyObservers(data);
				updating = false;
			}
		}
		
		// Network drawing
		
		public void moveNode (int index)
		{
			NetworkDataset dataset = data.getDataset();
			Drawing drawing = getDrawing();

			Circle node;
			Line link;
			int x = dataset.getX(index-1);
			int y = dataset.getY(index-1);
				
			node = (Circle) drawing.getDrawingElement("node"+index);

			if (node!=null) {
				node.setCenterX(x);
				node.setCenterY(y);
			}
			
			for (int i=1; i<=dataset.getRowCount(); i++) {
				
				if (i!=index) {
					
					link = (Line) drawing.getDrawingElement("link-node"+index+"-node"+i);
					
					if (link!=null) {
						link.setStartX(x);
						link.setStartY(y);
					}

					link = (Line) drawing.getDrawingElement("link-node"+i+"-node"+index);
					
					if (link!=null) {
						link.setEndX(x);
						link.setEndY(y);
					}
				}
			}
			
		}
		
		public Drawing createCompleteNetwork (NetworkDataset dataset)
		{
			Drawing drawing = new Drawing(600,600);
			Style color;
			Style border;
			
			int K = dataset.getRowCount();
			
			// Links
			
			color = new Style(new Color(0x70, 0x70, 0x70, 0xFF),3);

			for (int i=1; i<=K; i++) {
			    for (int j=i+1; j<=K; j++) {
					drawing.add(
						new Line ("link-node"+i+"-node"+j, color,
								dataset.getX(i-1), dataset.getY(i-1),
								dataset.getX(j-1), dataset.getY(j-1) ) );
				}
			}
			
			// Nodes

			if (flat) {

				Style colors[] = new Style[] {
						new Style ( new Color(0xB0, 0x00, 0x00, 0xFF), 3),
						new Style ( new Color(0x00, 0xB0, 0x00, 0xFF), 3),
						new Style ( new Color(0x00, 0x00, 0xB0, 0xFF), 3)
				};

				Style borders[] = new Style[] {
						new Style ( new Color(0x33, 0x00, 0x00, 0xFF), 3),
						new Style ( new Color(0x00, 0x33, 0x00, 0xFF), 3),
						new Style ( new Color(0x00, 0x00, 0x33, 0xFF), 3)				
				};

				for (int i=1; i<=K; i++)
					drawing.add( new Circle ("node"+i, colors[i%colors.length], borders[i%borders.length], dataset.getX(i-1), dataset.getY(i-1), 30) );

			} else {
				
				Gradient grad;
				
				if (radial)
					grad = new RadialGradient(0.3f, 0.3f, 0.5f);
				else
					grad = new LinearGradient(0.4f, 0.4f, 0.8f, 0.8f);
				
				grad.addKeyframe( new GradientKeyframe(0.0f, new Color(0xC0, 0xC0, 0xF0, 0xFF) ) );
				grad.addKeyframe( new GradientKeyframe(1.0f, new Color(0x00, 0x00, 0xB0, 0xFF) ) );
				
				border = new Style ( new Color(0x33, 0x00, 0x00, 0xFF), 0);
				
				grad.setWidth(10);
				
				for (int i=1; i<=K; i++)
					drawing.add( new Circle ("node"+i, grad, border, dataset.getX(i-1), dataset.getY(i-1), 30) );
			}
			
			return drawing;
		}

	}

	// Events
	
	public class NetworkTooltipProvider implements DrawingTooltipProvider
	{
		@Override
		public String get(String id) 
		{
			return id;
		}
	}
	
	public class NetworkDraggingListener implements DrawingUpdateListener
	{
		NetworkFigure  figure;
		Drawing drawing;
		
		public NetworkDraggingListener (NetworkFigure figure)
		{
			this.figure = figure;
			this.drawing = figure.getDrawing();
		}
		
		@Override
		public void update(String id, int x, int y) 
		{
			DrawingElement current = drawing.getDrawingElement(id);
			
			if (current.getId().startsWith("node")) {
				
				// Update dataset
				
				int node = Integer.parseInt(id.substring(4));
				
				figure.getData().getDataset().setCoordinates(node-1, x, y);
											
				// Update node

				((Circle)current).setCenterX(x);
				((Circle)current).setCenterY(y);
				
				// Update links
				
				String name;
				
				for (DrawingElement element: drawing.getElements()) {
				
					name = element.getId();
					
					if ( (element instanceof Line) && (name!=null) ) {
						
						if (name.endsWith(id)) {
							((Line)element).setEndX(x);
							((Line)element).setEndY(y);
						} else if (name.contains(id)) {
							((Line)element).setStartX(x);
							((Line)element).setStartY(y);
						}
					}
				}
				
				// Notify observers
				
				figure.notifyObservers(figure.getData().getDataset());
			}
		}
		
	}

	public class NetworkSelectionListener implements DrawingSelectionListener
	{
		Drawing drawing;
		Style   currentStyle;
		
		public NetworkSelectionListener (Drawing drawing)
		{
			this.drawing = drawing;
		}

		@Override
		public void setSelection (String id) 
		{
			clearSelection();
			
			currentStyle = new Style ( new Color(0xff, 0x00, 0x00, 0x80), 3);
			drawing.getDrawingElement(id).setBorder(currentStyle);
		}

		@Override
		public void addSelection(String id) 
		{
			if (currentStyle==null)
				currentStyle = new Style ( new Color(0xff, 0x00, 0x00, 0x80), 3);

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

}
