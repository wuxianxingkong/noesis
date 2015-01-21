package noesis.algorithms.visualization;

import ikor.math.random.Random;
import noesis.LinkEvaluator;
import noesis.Network;
import noesis.algorithms.paths.AllPairsDijkstra;

/**
 * Kamada-Kawai graph layout algorithm
 * 
 * Kamada, T., & Kawai, S. (1989):
 * "An algorithm for drawing general undirected graphs" 
 * Information processing letters, 31(1):7-15
 * 
 * @author Victor Martinez (victormg@acm.org)
 */
public class KamadaKawaiLayout extends IterativeNetworkLayout {

	private static final int MAX_ITERATIONS = 100;
	private static final double EPSILON = 1e-6;
	private static final double K = 0.001;

	private int iteration;
	private double displaySideLength;
	private double distances[][];
	private double L;
	private double l[][];
	private double k[][];
	private double px[];
	private double py[];
	private boolean finished;

	@Override
	public void init() 
	{
		// Compute node distances and diameter
		AllPairsDijkstra<?, ?> dijkstra = new AllPairsDijkstra(network, new ExistingLinkEvaluator(network));
		dijkstra.run();
		distances = dijkstra.distance();
		double diameter = 0;
		for (int i = 0; i < network.nodes(); i++)
			for (int j = i + 1; j < network.nodes(); j++)
				if (diameter < distances[i][j])
					diameter = distances[i][j];

		// Get display size
		displaySideLength = 1 - 2 * MARGIN;

		// Compute L
		L = displaySideLength / diameter;

		// Compute l_ij and k_ij
		l = new double[network.nodes()][network.nodes()];
		k = new double[network.nodes()][network.nodes()];

		for (int sourceNode = 0; sourceNode < network.nodes(); sourceNode++) {
			for (int targetNode = 0; targetNode < network.nodes(); targetNode++) {
				if (sourceNode != targetNode) {
					l[sourceNode][targetNode] = L
							* distances[sourceNode][targetNode];
					k[sourceNode][targetNode] = K
							/ (distances[sourceNode][targetNode] * distances[sourceNode][targetNode]);
				}
			}
		}

		// Initialize node particles positions
		px = new double[network.size()];
		py = new double[network.size()];

		for (int i = 0; i < network.size(); i++) {
			px[i] = Random.random();
			py[i] = Random.random();
		}

		finished = false;
		iteration = 0;
	}

	@Override
	public boolean stop() 
	{
		return finished || !(iteration < MAX_ITERATIONS);
	}

	@Override
	public void iterate() 
	{
		finished = true;

		// Compute max delta
		double maxDelta = 0;
		double currentDelta = 0;
		int m = -1;
		for (int i = 0; i < network.nodes(); i++) {
			currentDelta = delta(i);
			if (currentDelta > maxDelta) {
				maxDelta = currentDelta;
				m = i;
			}
		}

		if (maxDelta > EPSILON) {
			finished = false;
			int internalIterations = 0;
			while (delta(m) > EPSILON && internalIterations < MAX_ITERATIONS) {

				// Compute system coefficients
				double diffXm = diffX(m);
				double diffYm = diffY(m);

				double diffX2m = 0;
				double diffY2m = 0;
				double diffXY2m = 0;

				for (int i = 0; i < network.nodes(); i++) {
					if (i != m) {
						double dx = px[m] - px[i];
						double dy = py[m] - py[i];
						double denominator = Math.pow(dx * dx + dy * dy,
								3.0 / 2.0);

						diffX2m += k[m][i]
								* (1 - (l[m][i] * dy * dy) / denominator);
						diffY2m += k[m][i]
								* (1 - (l[m][i] * dy * dy) / denominator);
						diffXY2m += k[m][i]
								* (1 - (l[m][i] * dx * dy) / denominator);
					}
				}

				// Solve system
				double oy = (diffX2m * (-diffYm) / diffXY2m - diffXm)
						/ (diffXY2m + diffX2m * diffY2m / diffXY2m);
				double ox = (-diffXm - diffXY2m * oy) / diffX2m;

				// Update coords
				px[m] = px[m] + ox;
				py[m] = py[m] + oy;
				
				internalIterations++;
			}

		}

		iteration++;
	}

	@Override
	public void end() 
	{
		// Final scale

		double minX = 1;
		double maxX = -1;
		double minY = 1;
		double maxY = -1;

		for (int i = 0; i < network.size(); i++) {

			if (px[i] < minX)
				minX = px[i];

			if (px[i] > maxX)
				maxX = px[i];

			if (py[i] < minY)
				minY = py[i];

			if (py[i] > maxY)
				maxY = py[i];
		}

		double scaleX;
		double scaleY;

		if (maxX > minX)
			scaleX = (1 - 2 * MARGIN) / (maxX - minX);
		else
			scaleX = 1.0;

		if (maxY > minY)
			scaleY = (1 - 2 * MARGIN) / (maxY - minY);
		else
			scaleY = 1.0;

		// Final (x,y) coordinates

		for (int i = 0; i < network.size(); i++) {
			x.set(i, MARGIN + scaleX * (px[i] - minX));
			y.set(i, MARGIN + scaleY * (py[i] - minY));
		}

		distances = null;
		l = null;
		k = null;
		px = null;
		py = null;
	}

	
	private double diffX(int m) 
	{
		double value = 0;
		for (int i = 0; i < network.nodes(); i++) {
			if (i != m) {
				double dx = px[m] - px[i];
				double dy = py[m] - py[i];
				value += k[m][i]
						* (dx - (l[m][i] * dx / Math.sqrt(dx * dx + dy * dy)));

			}
		}
		return value;
	}

	private double diffY(int m) 
	{
		double value = 0;
		for (int i = 0; i < network.nodes(); i++) {
			if (i != m) {
				double dx = px[m] - px[i];
				double dy = py[m] - py[i];
				value += k[m][i]
						* (dy - (l[m][i] * dy / Math.sqrt(dx * dx + dy * dy)));

			}
		}
		return value;
	}

	private double delta(int m) 
	{
		double diffXm = diffX(m);
		double diffYm = diffY(m);
		return Math.sqrt(diffXm * diffXm + diffYm * diffYm);
	}
	
	
	// Link evaluator
	
	class ExistingLinkEvaluator implements LinkEvaluator
	{
		private Network net;
		
		public ExistingLinkEvaluator (Network net)
		{
			this.net = net;
		}
		
		@Override
		public double evaluate (int source, int destination) 
		{
			return net.contains(source, destination)? 1: Double.POSITIVE_INFINITY;
		}
	}	
}
