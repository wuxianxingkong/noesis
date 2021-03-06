package test.ikor.model.graphics;

import ikor.model.graphics.*;
import ikor.model.graphics.io.DrawingWriter;
import ikor.model.graphics.io.JPGDrawingWriter;
import ikor.model.graphics.io.PNGDrawingWriter;
import ikor.model.graphics.io.SVGDrawingWriter;
import ikor.model.graphics.styles.*;
import ikor.model.graphics.swing.JDrawingComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;



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
		
		// External image 
		// Bitmap bitmap = new Bitmap("http://elvex.ugr.es/image/logo/decsai.png", 100, 100, 187, 219);
		// bitmap.setAngle(Math.PI/3);
		// drawing.add( bitmap );

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
		JButton buttonSVG = new JButton("SVG");
		
		control = new JDrawingComponent(drawing);
		control.setTooltipProvider( new TestTooltipProvider() );
		control.setSelectionListener ( new TestSelectionListener(drawing) );
		control.setDraggingListener( new TestDraggingListener(drawing) );
		
		buttonJPG.addActionListener( new SaveFileActionListener( new JPGDrawingWriter(drawing) ) );
		buttonPNG.addActionListener( new SaveFileActionListener( new PNGDrawingWriter(drawing) ) );
		buttonSVG.addActionListener( new SaveFileActionListener( new SVGDrawingWriter(drawing) ) );
		
		buttons.add(buttonJPG);
		buttons.add(buttonPNG);
		buttons.add(buttonSVG);

		panel.setLayout( new BorderLayout() );
		panel.add(buttons, BorderLayout.SOUTH);
		panel.add(control, BorderLayout.CENTER);

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(panel);
		jframe.pack();
		jframe.setVisible(true);
		
	}
	
	public class SaveFileActionListener implements ActionListener
	{
		private DrawingWriter writer;
		
		public SaveFileActionListener ( DrawingWriter writer)
		{
			this.writer = writer;
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			JFileChooser fileChooser = new JFileChooser();
			
			if (fileChooser.showSaveDialog(control) == JFileChooser.APPROVE_OPTION)
				try {
					writer.write(fileChooser.getSelectedFile());
				} catch (IOException error) {
					System.err.println(error);
				}
		}
		
	}
	

	

}
