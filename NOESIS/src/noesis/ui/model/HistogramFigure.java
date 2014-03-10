package noesis.ui.model;

import java.text.DecimalFormat;

import ikor.math.Histogram;
import ikor.model.graphics.DrawingTooltipProvider;
import ikor.model.graphics.charts.AxisRenderer;
import ikor.model.graphics.charts.BarRenderer;
import ikor.model.graphics.charts.Chart;
import ikor.model.ui.Figure;

public class HistogramFigure extends Figure<Histogram> 
{
	
	public HistogramFigure (Histogram histogram)
	{
		Chart  chart = new Chart(500,300);
		
		chart.addSeries(histogram, BarRenderer.class);
		chart.setBackgroundRenderer(null);
		chart.getAxisRenderer().grid( AxisRenderer.GridStyle.None, AxisRenderer.GridStyle.None);
		chart.render();
		
		setDrawing( chart );
		setTooltipProvider( new HistogramTooltipProvider(histogram) );
		
		update();
		show();
	}
	
	
	
	// Event handling

	public class HistogramTooltipProvider implements DrawingTooltipProvider
	{
		private Histogram histogram;
		
		public HistogramTooltipProvider (Histogram histogram)
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
