package noesis.ui.model.data;

import noesis.CollectionFactory;
import ikor.collection.List;
import ikor.math.DenseVector;
import ikor.math.Histogram;
import ikor.math.Vector;
import ikor.math.statistics.Distribution;
import ikor.math.util.LogarithmicScale;
import ikor.math.util.LogarithmicTransformation;
import ikor.math.util.Scale;
import ikor.model.graphics.charts.Chart;
import ikor.model.graphics.charts.LineRenderer;
import ikor.model.graphics.charts.Series;

/**
 * Histogram figure subclass to support distribution fitting
 * 
 * @author Victor Martinez (victormg@acm.org) & Fernando Berzal (berzal@acm.org)
 */
public class HistogramFitFigure extends HistogramFigure 
{	
	private final int DISTRIBUTION_POINTS = 500;
	
	private List<String> labels;
	private List<Distribution> distributions;
	
	public HistogramFitFigure(Histogram histogram) 
	{
		super(histogram);
		setTooltipProvider( new HistogramFitTooltipProvider(histogram) );
		
		labels = CollectionFactory.createList();
		distributions = CollectionFactory.createList();
	}

	public void clearDistributions() 
	{
		labels.clear();
		distributions.clear();
		reset();
		render();
	}

	public void addDistribution(Distribution distribution, String name) 
	{
		labels.add(name);
		distributions.add(distribution);
		reset();
		render();
	}
	
	@Override
	public void reset ()
	{
		super.reset();
		
		Chart chart = getChart();
		Histogram histogram = getModel();
		Scale dataScale = histogram.getScale();
		
		if (distributions!=null) {
	
			// TODO Uniform y-scale for different probability distributions 
			// TODO Display of confidence intervals
			
			for (int d=0; d<distributions.size(); d++) {

				Distribution distribution = distributions.get(d);

				// Compute distribution points
				Vector xPoints = new DenseVector(DISTRIBUTION_POINTS+1);
				Vector yPoints = new DenseVector(DISTRIBUTION_POINTS+1);

				for (int i = 0; i <= DISTRIBUTION_POINTS; i++) {
					double x = (dataScale.max()-dataScale.min())*(i/(double)DISTRIBUTION_POINTS)+dataScale.min();
					xPoints.set(i, x);
					yPoints.set(i, distribution.pdf( x ));
				}

				Series series = new Series("distribution["+labels.get(d)+"]", xPoints, yPoints);

				LineRenderer lineRenderer = new LineRenderer (chart, series);
				
				if (histogram.getScale() instanceof LogarithmicScale) {
					lineRenderer.setXScale( histogram.getScale() );
					lineRenderer.setYScale( new LogarithmicTransformation(minPositive(yPoints), yPoints.max()) );
				} else {
					// Nothing to do (linear scale)
				}
				
				chart.addSeries(series, lineRenderer);
			}
		}		
	}
	
	
	private double minPositive (Vector v)
	{
		double min = Double.MAX_VALUE;
		
		for (int i=0; i<v.size(); i++)
			if (v.get(i)>0.0 && v.get(i)<min)
				min = v.get(i);
		
		return min;
	}

	// Tooltip provider
	
	public class HistogramFitTooltipProvider extends HistogramTooltipProvider 
	{
		public HistogramFitTooltipProvider(Histogram histogram) 
		{
			super(histogram);
		}

		@Override
		public String get(String id) 
		{
			String tooltip = super.get(id);

			if ( tooltip == null ) {
				// Tooltip for distribution
				if (id.startsWith("distribution")) {
					String name = id.substring(id.indexOf("[")+1,id.indexOf("]"));
					tooltip = "<html><b>"+name+"</b></html>";
				}
			}

			return tooltip;
		}
	}

}
