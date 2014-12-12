package noesis.ui.model.data;

import java.text.DecimalFormat;

import noesis.CollectionFactory;
import ikor.collection.List;
import ikor.math.DenseMatrix;
import ikor.math.DenseVector;
import ikor.math.Histogram;
import ikor.math.Matrix;
import ikor.math.Vector;
import ikor.math.statistics.Distribution;
import ikor.math.util.LinearScale;
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
	
			Vector x = new DenseVector(DISTRIBUTION_POINTS+1);
			Matrix y = new DenseMatrix(distributions.size(), DISTRIBUTION_POINTS+1);

			double step = 1.0 / DISTRIBUTION_POINTS;

			for (int i=0; i<=DISTRIBUTION_POINTS; i++) {
				double value = dataScale.inverse(i*step);
				x.set(i, value);

				for (int d=0; d<distributions.size(); d++)
					y.set(d, i, distributions.get(d).pdf( value ));
			}

			for (int d=0; d<distributions.size(); d++) {

				Series series = new Series("distribution_"+d, x, y.getRow(d));

				LineRenderer lineRenderer = new LineRenderer (chart, series);

				lineRenderer.setXScale( histogram.getScale() );
				
				// Identical y-scale for the different probability distributions 
				
				if (histogram.getScale() instanceof LogarithmicTransformation) {
					lineRenderer.setYScale( new LogarithmicTransformation(minPositive(y), y.max()) );
				} else {
					lineRenderer.setYScale( new LinearScale(0.0, y.max()));
				}
				
				chart.addSeries(series, lineRenderer);
			}
		}		
	}
	
	
	private double minPositive (Matrix data)
	{
		double min = Double.MAX_VALUE;
		
		for (int i=0; i<data.rows(); i++) {
			for (int j=0; j<data.columns(); j++) {
				double value = data.get(i,j);
				
				if (value>0.0 && value<min)
					min = value;
			}
		}
		
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
			DecimalFormat df = new DecimalFormat("#.####");

			if ( tooltip == null ) {

				if (id.startsWith("distribution")) {
					
					int d = Integer.parseInt(id.substring(13,14));
					
					tooltip = "<html><b>"+labels.get(d)+"</b>";
					
					int i = Integer.parseInt(id.substring(15,id.length()-1));
					double x = getModel().getScale().inverse((double)i/(double)DISTRIBUTION_POINTS);

					tooltip += "<br/>x = "+ df.format(x);
					tooltip += "<br/>pdf(x) = "+df.format(distributions.get(d).pdf(x));
					tooltip += "<br/>cdf(x) = "+df.format(distributions.get(d).cdf(x));
					tooltip += "</html>";
				}					
			}

			return tooltip;
		}
	}

}
