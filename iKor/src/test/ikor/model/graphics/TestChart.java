package test.ikor.model.graphics;

import ikor.math.DenseVector;
import ikor.math.Vector;
import ikor.model.graphics.*;
import ikor.model.graphics.io.DrawingWriter;
import ikor.model.graphics.io.JPGDrawingWriter;
import ikor.model.graphics.io.PNGDrawingWriter;
import ikor.model.graphics.io.SVGDrawingWriter;
import ikor.model.graphics.swing.JDrawingComponent;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ikor.model.graphics.charts.BarRenderer;
import ikor.model.graphics.charts.Chart;
import ikor.model.graphics.charts.DotRenderer;
import ikor.model.graphics.charts.LineRenderer;
import ikor.model.graphics.charts.LinearScale;
import ikor.model.graphics.charts.LogarithmicScale;
import ikor.model.graphics.charts.Series;


public class TestChart 
{
	private Chart drawing;
	private JDrawingComponent control;

	public TestChart ()
	{
	/*	
		Vector data = new DenseVector( new double[]{1,2,3,4,5,6,7,8,9,10} );
		drawing = new Chart(600,600);
		drawing.addSeries(data, BarRenderer.class);
		drawing.addSeries(data, LineRenderer.class);
		drawing.addSeries(data, DotRenderer.class);
		drawing.setYScale(new LogarithmicScale(0,100));
		//drawing.setYScale(new LinearScale(0,10));
	/* */	
		Vector x = new DenseVector ( new double[]{3,4,5,1,2,9,8,7,10,6} );
		Vector y = new DenseVector ( new double[]{1,2,3,4,5,6,7,8,9,10} );
		Series series = new Series (x,y);		
		drawing = new Chart(600,600);
		drawing.addSeries(series, BarRenderer.class);
		drawing.addSeries(series, LineRenderer.class);
		drawing.addSeries(series, DotRenderer.class);
		drawing.setXScale(new LinearScale(0,10));
		drawing.setXScale(new LogarithmicScale(0,100));
		drawing.setYScale(new LinearScale(0,10));
		drawing.setYScale(new LogarithmicScale(0,100));
	/*	*/

		drawing.render();
		
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
		TestChart test = new TestChart();

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
