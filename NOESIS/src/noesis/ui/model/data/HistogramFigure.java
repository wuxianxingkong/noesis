package noesis.ui.model.data;

import java.text.DecimalFormat;

import ikor.math.Histogram;
import ikor.math.util.LogarithmicScale;

import ikor.model.graphics.DrawingTooltipProvider;
import ikor.model.graphics.charts.AxisRenderer;
import ikor.model.graphics.charts.BarRenderer;
import ikor.model.graphics.charts.Chart;
import ikor.model.ui.Figure;

public class HistogramFigure extends Figure<Histogram> 
{
	private Chart chart;
	
	public HistogramFigure (Histogram histogram)
	{
		setTooltipProvider( new HistogramTooltipProvider(histogram) );

		setModel(histogram);
	}
	
	public void setModel (Histogram histogram)
	{
		super.setModel(histogram);
		
		if (histogram!=null) {
			
			// Update chart

			chart = new Chart(500,300);

			chart.addSeries(histogram, BarRenderer.class);
			chart.setBackgroundRenderer(null);
			chart.getAxisRenderer().grid( AxisRenderer.GridStyle.None, AxisRenderer.GridStyle.None);
			
			if (histogram.getScale() instanceof LogarithmicScale) {
				chart.setYScale( new LogarithmicScale(0, histogram.max()) );
				chart.getAxisRenderer().grid( AxisRenderer.GridStyle.None, AxisRenderer.GridStyle.Logarithmic);
			} else { // Linear scale, no grid
				chart.getAxisRenderer().grid( AxisRenderer.GridStyle.None, AxisRenderer.GridStyle.None);
			}

			chart.render();

			super.setDrawing(chart);

			update();

			// Update tooltip provider 

			((HistogramTooltipProvider)getTooltipProvider()).setHistogram(histogram);
		}
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
