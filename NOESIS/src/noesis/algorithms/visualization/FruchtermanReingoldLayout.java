package noesis.algorithms.visualization;

/**
 * Fruchterman-Reingold graph layout algorithm: O(n^2)
 * 
 * Fruchterman, T.M.J & Reingold, E.M (1991):
 * "Graph Drawing by Force-directed Placement"
 * Software-Practice and Experience 21(11):1129-1164
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class FruchtermanReingoldLayout extends IterativeNetworkLayout 
{
	public static final int    MAX_ITERATIONS = 100;
	public static final double MIN_TEMPERATURE = 0.005;
	public static final double INITIAL_TEMPERATURE = 0.10;
	public static final double COOLING_FACTOR = 0.95; // 0.95^100 ~ 0.005
	
	double area;
	double k;
	int    iteration;
	double temperature;  // Simulated-annealing-like algorithm
	
	double dx[];
	double dy[];
	
	double px[];
	double py[];
	
	@Override
	public void init() 
	{
		area = (1-2*MARGIN)*(1-2*MARGIN);
		k = Math.sqrt(area/network.size());
		iteration = 0;
		temperature = INITIAL_TEMPERATURE;
		
		dx = new double[network.size()];
		dy = new double[network.size()];

		px = new double[network.size()];
		py = new double[network.size()];
		
		for (int i=0; i<network.size(); i++) {
			px[i] = x.get(i) - 0.5;
			py[i] = y.get(i) - 0.5;
		}
		
	}

	@Override
	public boolean stop() 
	{
		return !( (temperature>MIN_TEMPERATURE) && (iteration<MAX_ITERATIONS) );
	}

	@Override
	public void iterate() 
	{
		double deltax, deltay, distance;
		double force;
		double magnitude;
		
		// Repulsive forces: O(n^2)
		
		for (int v=0; v<network.size(); v++) {
			
			dx[v] = 0;
			dy[v] = 0;
			
			for (int w=v+1; w<network.size(); w++) {
				
				deltax = px[v] - px[w];
				deltay = py[v] - py[w];
				
				if ( (deltax!=0) || (deltay!=0)) {
					distance = Math.sqrt(deltax*deltax + deltay*deltay);
					force = repulsiveForce(distance);
					
					dx[v] += (deltax/distance)*force;
					dy[v] += (deltay/distance)*force;
					
					dx[w] -= (deltax/distance)*force;
					dy[w] -= (deltay/distance)*force;
				}
			}
		}
		
		// Attractive forces: O(m)
		
		for (int v=0; v<network.size(); v++) {
			
			for (int index=0; index<network.outDegree(v); index++) {
				
				int w = network.outLink(v, index);
				
				deltax = px[v] - px[w];
				deltay = py[v] - py[w];
				
				if ( (deltax!=0) || (deltay!=0)) {
					distance = Math.sqrt(deltax*deltax + deltay*deltay);
					force = attractiveForce(distance);
					
					dx[v] -= (deltax/distance)*force;
					dy[v] -= (deltay/distance)*force;
					
					dx[w] += (deltax/distance)*force;
					dy[w] += (deltay/distance)*force;
				}
			}
		}
		
		// Displacement limit: O(n)
		
		double max = 0;
		
		for (int v=0; v<network.size(); v++) {
			magnitude = Math.sqrt(dx[v]*dx[v] + dy[v]*dy[v]);
						
			if (magnitude>0) {
				// Limit displacement by temperature
				px[v] += (dx[v]/magnitude) * Math.min( Math.abs(dx[v]), temperature);
				py[v] += (dy[v]/magnitude) * Math.min( Math.abs(dy[v]), temperature);
				
				// Max
				if (px[v]*px[v] + py[v]*py[v] > max)
					max = px[v]*px[v] + py[v]*py[v];
			}
		}
		
		// Rescale

		double r2 = (0.5-MARGIN)*(0.5-MARGIN);
		double rescale;
		
		if (max>r2) {
			rescale = Math.sqrt(r2/max); 
			for (int v=0; v<network.size(); v++) {
				px[v] *= rescale;
				py[v] *= rescale;
			}
		}
		
		// Next iteration
		
		temperature = cool(temperature);
		
		iteration++;
	}

	@Override
	public void end() 
	{
		dx = null;
		dy = null;
		
		// Final scale

		double minX = 1;
		double maxX = -1;
		double minY = 1;
		double maxY = -1;
		
		for (int i=0; i<network.size(); i++) {
			
			if (px[i]<minX)
				minX = px[i];
			
			if (px[i]>maxX)
				maxX = px[i];
			
			if (py[i]<minY)
				minY = py[i];
			
			if (py[i]>maxY)
				maxY = py[i];
		}
		
		double scaleX;
		double scaleY;
		
		if (maxX>minX)
			scaleX = (1-2*MARGIN)/(maxX-minX);
		else
			scaleX = 1.0;
		
		if (maxY>minY)
			scaleY = (1-2*MARGIN)/(maxY-minY);
		else
			scaleY = 1.0;
		
		// Final (x,y) coordinates

		for (int i=0; i<network.size(); i++) {
			x.set(i, MARGIN + scaleX*(px[i]-minX) );
			y.set(i, MARGIN + scaleY*(py[i]-minY) );
		}
		
		px = null;
		py = null;
	}

	private double cool (double t)
	{
		return t*COOLING_FACTOR;
	}
	
	private double attractiveForce (double x)
	{
		return x*x/k;
	}
	
	private double repulsiveForce (double x)
	{
		return k*k/x;
	}
}
