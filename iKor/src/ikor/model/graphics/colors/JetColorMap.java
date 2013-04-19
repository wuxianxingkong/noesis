package ikor.model.graphics.colors;

import java.util.Arrays;


/**
 * Matlab's jet color map
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class JetColorMap extends ColorMap 
{
	private int r[];
	private int g[];
	private int b[];
	
	public JetColorMap (int size)
	{
		r = new int[size];
		g = new int[size];
		b = new int[size];
		
		createJet(size);
	}
		
	private void createJet (int n)
	{
		int maxval = 255;

        Arrays.fill(g, 0, n/8, 0);

        for(int x = 0; x < n/4; x++)
            g[x+n/8] = (maxval*x*4/n);
        
        Arrays.fill(g, n*3/8, n*5/8, maxval);

        for(int x = 0; x < n/4; x++)
            g[x+n*5/8] = (maxval-(maxval*x*4/n));

        Arrays.fill(g, n*7/8, n, 0);

        for(int x = 0; x < g.length; x++)
            b[x] = g[(x+n/4) % g.length];

        Arrays.fill(b, n*7/8, n, 0);

        Arrays.fill(g, 0, n/8, 0);

        for(int x = n/8; x < g.length; x++)
            r[x] = g[(x+n*6/8) % g.length];		
	}

	@Override
	public int size() 
	{
		return r.length;
	}

	@Override
	public int red(int index) 
	{
		return r[index];
	}

	@Override
	public int green(int index) 
	{
		return g[index];
	}

	@Override
	public int blue(int index) 
	{
		return b[index];
	}

}
