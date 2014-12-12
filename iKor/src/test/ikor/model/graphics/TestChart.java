package test.ikor.model.graphics;

import ikor.math.DenseVector;
import ikor.math.Vector;
import ikor.math.util.LinearScale;
import ikor.math.util.LogarithmicTransformation;
import ikor.model.graphics.*;
import ikor.model.graphics.io.DrawingWriter;
import ikor.model.graphics.io.JPGDrawingWriter;
import ikor.model.graphics.io.PNGDrawingWriter;
import ikor.model.graphics.io.SVGDrawingWriter;
import ikor.model.graphics.swing.JDrawingComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ikor.model.graphics.charts.AxisRenderer;
import ikor.model.graphics.charts.BarRenderer;
import ikor.model.graphics.charts.Chart;
import ikor.model.graphics.charts.DotRenderer;
import ikor.model.graphics.charts.LineRenderer;
import ikor.model.graphics.charts.Series;


public class TestChart 
{
	private Chart drawing;
	private JDrawingComponent control;
	
	
	public static Chart multipleSeriesChart ()
	{
		Chart drawing = new Chart(600,600);

		Vector data = new DenseVector( new double[]{1,2,3,4,5,6,7,8,9,10} );
		drawing.addSeries(data, BarRenderer.class);
		drawing.addSeries(data, LineRenderer.class);
		drawing.addSeries(data, DotRenderer.class);
		drawing.setYScale(new LogarithmicTransformation(0,100));
		//drawing.setYScale(new LinearScale(0,10));
		
		return drawing;
	}

	public static Chart multipleVectorChart ()
	{
		Chart drawing = new Chart(600,600);
		
		Vector x = new DenseVector ( new double[]{3,4,5,1,2,9,8,7,10,6} );
		Vector y = new DenseVector ( new double[]{1,2,3,4,5,6,7,8,9,10} );
		Series series = new Series (x,y);		
		drawing = new Chart(600,600);
		drawing.addSeries(series, BarRenderer.class);
		drawing.addSeries(series, LineRenderer.class);
		drawing.addSeries(series, DotRenderer.class);
		drawing.setXScale(new LinearScale(0,10));
		//drawing.setXScale(new LogarithmicScale(0,100));
		drawing.setYScale(new LinearScale(0,10));
		//drawing.setYScale(new LogarithmicScale(0,100));

		return drawing;
	}

	public static Chart multipleColumnChart ()
	{
		Chart drawing = new Chart(600,600);
		
		Vector x1 = new DenseVector ( new double[]{3,4,5,1,2,9,8,7,10,6} );
		Vector x2 = new DenseVector ( new double[]{1,2,3,4,5,6,7,8,9,10} );
		BarRenderer b1 = new BarRenderer( drawing, x1 );
		b1.setColumnWidth(0.4);
		b1.setColumnOffset(0.2);
		b1.setStyle( new Style ( new Color(0xB0, 0x00, 0x00, 0xFF), 3) );
		BarRenderer b2 = new BarRenderer( drawing, x2);
		b2.setColumnWidth(0.4);
		b2.setColumnOffset(0.6);
		b2.setStyle( new Style ( new Color(0x00, 0x00, 0xB0, 0xFF), 3) );
		drawing.addSeries(x1, b1);
		drawing.addSeries(x2, b2);
		drawing.setYScale(new LinearScale(0,11));
		drawing.setBackgroundRenderer(null);
		drawing.setAxisRenderer(null);
		
		return drawing;
	}
	
	
	public static Chart customGridChart ()
	{
		Chart drawing = new Chart(600,600);
		Vector x1 = new DenseVector ( new double[]{3,4,5,1,2,9,8,7,10,6} );
		Vector x2 = new DenseVector ( new double[]{1,2,3,4,5,6,7,8,9,10} );
		LineRenderer r1 = new LineRenderer( drawing, x1 );
		r1.setStyle( new Style ( new Color(0xB0, 0x00, 0x00, 0xFF), 4) );
		DotRenderer r2 = new DotRenderer( drawing, x2);
		r2.setStyle( new Style ( new Color(0x00, 0xB0, 0xB0, 0xFF), 3) );
		r2.setSize(10);
		drawing.addSeries(x1, r1);
		drawing.addSeries(x2, r2);
		drawing.setYScale(new LinearScale(0,100));
		drawing.setYScale(new LogarithmicTransformation(0,100));
		drawing.getBackgroundRenderer().setStyle ( new Style ( new Color(0xFF, 0xFF, 0xFF, 0xFF), 3) );;
		drawing.getAxisRenderer().displayAxis(true);
		drawing.getAxisRenderer().setGridLines(50);
		//drawing.getAxisRenderer().grid(AxisRenderer.GridStyle.None, AxisRenderer.GridStyle.Linear);
		drawing.getAxisRenderer().grid(AxisRenderer.GridStyle.Linear, AxisRenderer.GridStyle.Logarithmic);
		
		return drawing;
	}
	
	
	public TestChart (Chart chart)
	{
		this.drawing = chart;
		
		drawing.render();
		
		System.out.println("Drawing elements...");
		for (DrawingElement element: drawing.getElements())
			System.out.println(element.toString());
		
		System.out.println("Drawing styles...");
		for (Style style: drawing.getStyles())
			System.out.println(style.toString());		
	}


	// Test program
	
	public static void main(String[] args) 
	{
		TestChart test;
		
		test = new TestChart(multipleSeriesChart());
		test.display();
		
		test = new TestChart(multipleVectorChart());
		test.display();
		
		test = new TestChart(multipleColumnChart());
		test.display();
		
		test = new TestChart(customGridChart());
		test.display();
	}
	
	public void display ()
	{
		JFrame  jframe = new JFrame("Chart...");
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
