package ikor.parallel;

// Scan pattern
// ------------

public class Scan 
{
	public static void scan (Getter getter, Combiner combiner, Setter setter, int start, int end)
	{
		int depth = Parallel.getDecompositionDepth();
		int tileWidth = Parallel.getTileWidth();
		
		int n = end-start+1;
		int tile = Math.max(tileWidth, n>>depth);  // Automatic tile width adjustment (max. 2^depth tiles)
		
		if (n>0) {
			
			int      m = (n-1)/tile;
			Object[] tmp = new Object[m+1];
			Object   initial = combiner.identity();

			Upsweep   up   = new Scan.Upsweep  (getter,combiner,setter, 0, m+1, tile, n-m*tile, tmp);
			Downsweep down = new Scan.Downsweep(getter,combiner,setter, 0, m+1, tile, n-m*tile, tmp, initial);

			up.call();
			down.call();
		}
	}

	// Greatest power of two less than the given value
	
	private static int split (int tiles)
	{
		int value = tiles;
		int power = 0;
		
		while (value>2) {
			value >>= 1;
			power++;
		}
		
		return 1<<power;
	}
	
	// Upsweep
	
	static class Upsweep<T> extends Task<T>
	{
		Combiner<T> combiner;
		Getter<T>   getter;
		Setter<T>   setter;
		
		T   tmp[];
		int offset;
		
		int first;
		int tiles;
		int tilesize;
		int last;
		
		public Upsweep (Getter<T> getter, Combiner<T> combiner, Setter<T> setter, int first, int tiles, int tilesize, int last, T tmp[])
		{
			this.combiner = combiner;
			this.getter = getter;
			this.setter = setter;
			
			this.first    = first;
			this.tiles    = tiles;
			this.tilesize = tilesize;
			this.last     = last;
			
			this.tmp      = tmp;
		}
		
		
		// Reduce 

		private T reduce (int start, int n)
		{
			T result = combiner.identity();
			
			for (int i=0; i<n; i++)
				result = combiner.combine(result, getter.get(start+i));
			
			return result;
		}

		@Override
		public T call() 
		{
			if (tiles==1) {
				
				tmp[first] = reduce(first*tilesize, last);
						
			} else {
				
				int k = split(tiles);
				
				Upsweep<T> task1 = new Upsweep (getter, combiner, setter, first,   k,       tilesize, tilesize, tmp);
				Upsweep<T> task2 = new Upsweep (getter, combiner, setter, first+k, tiles-k, tilesize, last,     tmp);

				Parallel.forkjoin(task1, task2);
				
				if (tiles>=2*k) {
					tmp[first+tiles-1] = combiner.combine ( tmp[first+tiles-1], tmp[first+k-1] );
				}
			}
			
			return null;
		}
	}
	
	
	// Downsweep

	static class Downsweep<T> extends Task<T>
	{
		Combiner<T> combiner;
		Getter<T>   getter;
		Setter<T>   setter;
		
		T   tmp[];
		T   initial;
		
		int first;
		int tiles;
		int tilesize;
		int last;
		
		public Downsweep (Getter<T> getter, Combiner<T> combiner, Setter<T> setter, int first, int tiles, int tilesize, int last, T tmp[], T initial)
		{
			this.combiner = combiner;
			this.getter = getter;
			this.setter = setter;
			
			this.first    = first;
			this.tiles    = tiles;
			this.tilesize = tilesize;
			this.last     = last;
			
			this.tmp = tmp;
			this.initial = initial;
		}
		
		
		// Reduce 

		private void scan (int start, int n, T initial)
		{
			T result = initial;
			
			for (int i=0; i<n; i++) {
				result = combiner.combine(result, getter.get(start+i));
				setter.set(start+i, result);
			}
		}

		@Override
		public T call() 
		{
			if (tiles==1) {
				
				scan(first*tilesize, last, initial);
						
			} else {
				
				int k = split(tiles);
				
				T intermediate = combiner.combine(initial, tmp[first+k-1]);
				
				Downsweep<T> task1 = new Downsweep (getter, combiner, setter, first,   k,       tilesize, tilesize, tmp, initial);
				
				Downsweep<T> task2 = new Downsweep (getter, combiner, setter, first+k, tiles-k, tilesize, last,     tmp, intermediate);

				Parallel.forkjoin(task1, task2);
			}
			
			return null;
		}
	}
}
