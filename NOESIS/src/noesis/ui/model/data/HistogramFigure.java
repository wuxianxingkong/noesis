package noesis.ui.model.data;

import java.text.DecimalFormat;

import ikor.math.Histogram;
import ikor.math.util.LogarithmicTransformation;
import ikor.model.graphics.DrawingTooltipProvider;
import ikor.model.graphics.charts.AxisRenderer;
import ikor.model.graphics.charts.BarRenderer;
import ikor.model.graphics.charts.Chart;
import ikor.model.ui.Figure;

/**
 * Histogram figure
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class HistogramFigure extends Figure<Histogram> 
{
	private Chart chart;
	
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 300;
	
	public HistogramFigure (Histogram histogram)
	{
		this.chart = new Chart(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.chart.setBackgroundRenderer(null);

		setTooltipProvider( new HistogramTooltipProvider(histogram) );
		setModel(histogram);				
	}
	
	public void setModel (Histogram histogram)
	{
		super.setModel(histogram);
		
		if (histogram!=null) {
			
			// Update chart
			
			reset();

			// Update tooltip provider 
			
			if (getTooltipProvider()!=null)
				((HistogramTooltipProvider)getTooltipProvider()).setHistogram(histogram);

			// Render
			
			render();
		}
	}
	
	
	protected void reset ()
	{
		Histogram histogram = getModel();
		
		chart.clear();
		chart.addSeries("data",histogram, BarRenderer.class);
		chart.getAxisRenderer().grid( AxisRenderer.GridStyle.None, AxisRenderer.GridStyle.None);
		
		if (histogram.getScale() instanceof LogarithmicTransformation) {
			chart.setYScale( new LogarithmicTransformation(0.1, histogram.max()) );
			chart.getAxisRenderer().grid( AxisRenderer.GridStyle.None, AxisRenderer.GridStyle.Logarithmic);
		} else { // Linear scale, no grid
			chart.getAxisRenderer().grid( AxisRenderer.GridStyle.None, AxisRenderer.GridStyle.None);
		}		
	}
	
	
	public void render ()
	{
		chart.render();
		
		super.setDrawing(chart);		

		update();
	}
	
	
	protected Chart getChart() 
	{
		return chart;
	}
	
	// Event handling

	public class HistogramTooltipProvider implements DrawingTooltipProvider
	{
		private Histogram histogram;
		
		public HistogramTooltipProvider (Histogram histogram)
		{
			this.histogram = histogram;
		}
		
		public void setHistogram (Histogram histogram)
		{
			this.histogram = histogram;
		}
		
		@Override
		public String get(String id) 
		{
			DecimalFormat df = new DecimalFormat("#.####");
			
			String tooltip = null;
			
			if (id.startsWith("data[")) {
				
				int bin = Integer.parseInt(id.substring(5,id.length()-1));
				
				tooltip = "<html><b>"
				        + "[" + df.format(histogram.threshold(bin))
						+ ", " + df.format(histogram.threshold(bin+1));
				
				if (bin<histogram.size()-1)
					tooltip += ")";
				else
					tooltip += "]";
				
				tooltip += "</b><br/>n = "+ (int)histogram.get(bin);
				tooltip += "</html>";
			}
			
			return tooltip;
		}
	}	
}
