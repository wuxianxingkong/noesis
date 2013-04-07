package sandbox.mdsd.graphics.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import sandbox.mdsd.graphics.*;
import sandbox.mdsd.graphics.styles.*;

public class TestDrawing 
{
	private Drawing drawing;
	private JDrawingComponent control;

	public TestDrawing ()
	{
		drawing = new Drawing(600,600);	
		Style color;
		Style border;
		
		boolean flat = false;
		boolean radial = true;
		boolean background = true;
		
		// Background
		
		if (background) {
			Gradient back = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f);

			back.addKeyframe( new GradientKeyframe(0.0f, new Color(0xe0, 0xe0, 0xe0, 0xFF) ) );
			back.addKeyframe( new GradientKeyframe(1.0f, new Color(0x00, 0x70, 0x70, 0x80) ) );

			drawing.add ( new Rectangle("background", back, null, 0, 0, 600, 600));
		}
		
		// External image 
		// Bitmap bitmap = new Bitmap("http://elvex.ugr.es/image/logo/decsai.png", 100, 100, 187, 219);
		// bitmap.setAngle(Math.PI/3);
		// drawing.add( bitmap );

		// Geometrical shapes
		
		Shape shape;
		
		color = new Style(new Color(0x00, 0x70, 0x70, 0x80),3);
		shape = new Rectangle("rectangle", color, null, 10, 10, 140, 20);
		drawing.add(shape);
		color = new Style(new Color(0x70, 0x00, 0x00, 0x80),3);
		shape = new Polygon("polygon", color, color, new int[]{15, 15, 80, 145, 145}, new int[]{15,50,80,50,15});
		drawing.add(shape);
		color = new Style(new Color(0x70, 0x70, 0x70, 0x80),3);
		shape = new Ellipse("ellipse", color, null, 80, 80, 50, 30);
		drawing.add(shape);
	
		color = new Style(new Color(0x00, 0x00, 0x70, 0x80),3);
		shape = new Arc("arc1", color, null, 280, 30, 50, 30, Math.PI, Math.PI/4);
		drawing.add(shape);
		color = new Style(new Color(0x00, 0x70, 0x00, 0x80),3);
		shape = new Arc("arc2", color, null, 280, 30, 50, 30, 5*Math.PI/4, Math.PI/4);
		drawing.add(shape);
		color = new Style(new Color(0x70, 0x00, 0x00, 0x80),3);
		shape = new Arc("arc3", color, null, 280, 30, 50, 30, 3*Math.PI/2, Math.PI/4);
		drawing.add(shape);

		color = new Style(new Color(0x00, 0x70, 0x70, 0x80),3);
		drawing.add ( new Text("Figura en formato vectorial", color, 8, 10));
		
		// Rotations
		
		for (int i=0; i<=90; i+=10) {
			FontStyle font = new FontStyle ( new Color(0x70, 0x70, 0x70, 0x80), new Font("Serif", Font.PLAIN, 24), -Math.PI*i/180);
			drawing.add ( new Text("Figura en formato vectorial", font, 20, 600));
		}
		
		color = new Style(new Color(0x00, 0x70, 0x70, 0x80),3);
		border = new Style(new Color(0x70, 0x00, 0x00, 0x80),3);
		shape = new Rectangle("rotatedRectangle", color, null, 410, 10, 140, 20);
		shape.setRotation(Math.PI/6);
		drawing.add (shape);

		color = new Style(new Color(0x70, 0x00, 0x00, 0x80),3);
		shape = new Polygon("rotatedPolygon", color, color, new int[]{415, 415, 480, 545, 545}, new int[]{15,50,80,50,15});
		shape.setRotation(Math.PI/4);
		drawing.add(shape);

		color = new Style(new Color(0x70, 0x70, 0x70, 0x80),3);
		shape = new Ellipse("rotatedEllipse", color, null, 480, 80, 50, 30);
		shape.setRotation(Math.PI/4);
		drawing.add(shape);

		color = new Style(new Color(0x00, 0x00, 0x70, 0x80),3);
		shape = new Arc("rotatedArc1", color, null, 350, 30, 50, 30, Math.PI, Math.PI/4);
		shape.setRotation(Math.PI/4);
		drawing.add(shape);
		color = new Style(new Color(0x00, 0x70, 0x00, 0x80),3);
		shape = new Arc("rotatedArc2", color, null, 350, 30, 50, 30, 5*Math.PI/4, Math.PI/4);
		shape.setRotation(Math.PI/4);
		drawing.add(shape);
		color = new Style(new Color(0x70, 0x00, 0x00, 0x80),3);
		shape = new Arc("rotatedArc3", color, null, 350, 30, 50, 30, 3*Math.PI/2, Math.PI/4);
		shape.setRotation(Math.PI/4);
		drawing.add(shape);
		
		// A network
		
		int K = 15;
		
		// Links
		
		color = new Style(new Color(0x70, 0x70, 0x70, 0xFF),3);

		for (int i=1; i<=K; i++) {
		    for (int j=i+1; j<=K; j++) {
				drawing.add(
					new Line ("link-node"+i+"-node"+j, color,
							(int) Math.round( 300 + 200*Math.cos(i*2*Math.PI/K)),
							(int) Math.round( 300 + 200*Math.sin(i*2*Math.PI/K)),
							(int) Math.round( 300 + 200*Math.cos(j*2*Math.PI/K)),
							(int) Math.round( 300 + 200*Math.sin(j*2*Math.PI/K)) ) );
			}
		}
		

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
				drawing.add(
						new Circle ("node"+i, colors[i%colors.length], borders[i%borders.length],
								(int) Math.round( 300 + 200*Math.cos(i*2*Math.PI/K)),
								(int) Math.round( 300 + 200*Math.sin(i*2*Math.PI/K)), 30) );

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
				drawing.add(
						new Circle ("node"+i, grad, border,
								(int) Math.round( 300 + 200*Math.cos(i*2*Math.PI/K)),
								(int) Math.round( 300 + 200*Math.sin(i*2*Math.PI/K)), 30) );
		}
		
		System.out.println("Drawing elements...");
		for (DrawingElement element: drawing.getElements())
			System.out.println(element.toString());
		
		System.out.println("Drawing styles...");
		for (Style style: drawing.getStyles())
			System.out.println(style.toString());		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		TestDrawing test = new TestDrawing();

		test.display();
	}
	
	public void display ()
	{
		JFrame  jframe = new JFrame("Drawing...");
		JPanel  panel = new JPanel();
		JPanel  buttons = new JPanel();
		JButton buttonJPG = new JButton("JPG");
		JButton buttonPNG = new JButton("PNG");
		
		control = new JDrawingComponent(drawing);
		control.setTooltipProvider( new TooltipProvider() );
		control.setSelectionListener ( new SelectionListener() );
		control.setDraggingListener( new DragListener() );
		
		buttonJPG.addActionListener( new SaveActionListener(control,"jpg") );
		buttonPNG.addActionListener( new SaveActionListener(control,"png") );
		
		buttons.add(buttonJPG);
		buttons.add(buttonPNG);

		panel.setLayout( new BorderLayout() );
		panel.add(buttons, BorderLayout.SOUTH);
		panel.add(control, BorderLayout.CENTER);

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(panel);
		jframe.pack();
		jframe.setVisible(true);
		
	}
	
	public class SaveActionListener implements ActionListener
	{
		private JDrawingComponent component;
		private String format;
		
		public SaveActionListener (JDrawingComponent component, String format)
		{
			this.component = component;
			this.format = format;
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			JFileChooser fileChooser = new JFileChooser();
			
			if (fileChooser.showSaveDialog(control) == JFileChooser.APPROVE_OPTION)
				component.save(fileChooser.getSelectedFile().getPath(), format);
		}
		
	}
	
	public class TooltipProvider implements JDrawingTooltipProvider
	{
		@Override
		public String getTooltip(String id) 
		{
			return id;
		}
	}
	
	public class DragListener implements JDrawingListener
	{
		@Override
		public void update(String id, int x, int y) 
		{
			DrawingElement current = drawing.getDrawingElement(id);
			
			if (current.getId().startsWith("node")) {
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
			}
				
			control.repaint();
		}
		
	}
	
	public class SelectionListener implements JDrawingSelectionListener
	{
		Style currentStyle;

		@Override
		public void setSelection (String id) 
		{
			clearSelection();
			
			currentStyle = new Style ( new Color(0xff, 0x00, 0x00, 0x80), 3);
			drawing.getDrawingElement(id).setBorder(currentStyle);
			control.repaint();
		}

		@Override
		public void addSelection(String id) 
		{
			if (currentStyle==null)
				currentStyle = new Style ( new Color(0xff, 0x00, 0x00, 0x80), 3);

			drawing.getDrawingElement(id).setBorder(currentStyle);
			control.repaint();
		}

		@Override
		public void clearSelection() 
		{
			if (currentStyle!=null) {
				currentStyle.setWidth(0);
				currentStyle = null;
				control.repaint();
			}
		}
		
	}

}
