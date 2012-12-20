package ikor.parallel;

/**
 * Algorithm skeletons for parallel programming
 * 
 * @author Fernando Berzal (berzal@acm.org)
 *
 */
public class Parallel 
{
	// Parameters

	public static final int DEFAULT_TILE_WIDTH = 16;
	public static final int DEFAULT_DECOMPOSITION_DEPTH = 4; // i.e. 2^4 parallel tasks
	
	private static int tileWidth = DEFAULT_TILE_WIDTH;
	private static int decompositionDepth = DEFAULT_DECOMPOSITION_DEPTH;
	
	public static int getTileWidth ()
	{
		return tileWidth;
	}
	
	public static void setTileWidth (int width)
	{
		tileWidth = width;
	}
	
	public static int getDecompositionDepth ()
	{
		return decompositionDepth;
	}
	
	public static void setDecompositionDepth (int depth)
	{
		decompositionDepth = depth;
	}
	
	// Fork-join pattern
	
	public static void forkjoin (Task a, Task b)
	{
		Scheduler.get().schedule(a);
		
		b.setResult(b.call());
		
		a.getResult();
	}

	public static void forkjoin (Task tasks[])
	{
		Fork task = new Parallel.Fork(tasks,0,tasks.length-1,decompositionDepth);
		
		task.call();
		
		/* 
		// Alternative [linear-time] implementation
		  
		int n = tasks.length;
		
		for (int i=0; i<n-1; i++)
			Scheduler.get().schedule(tasks[i]);
		
		tasks[n-1].setResult(tasks[n-1].call());
		
		for (int i=0; i<n-1; i++)
			tasks[i].getResult();
		/**/
	}
	
	static class Fork extends Task
	{
		Task[] tasks;
		int    start;
		int    end;
		int    depth;
		
		public Fork (Task tasks[], int start, int end, int depth)
		{
			this.tasks  = tasks;
			this.start  = start;
			this.end    = end;
			this.depth  = depth;
		}

		public Object call ()
		{
			if ( (end-start>=tileWidth) && (depth>0)) {
				
				int middle = (start+end)/2;
				
				Fork fork1 = new Fork (tasks, start, middle, depth-1);
				Fork fork2 = new Fork (tasks, middle+1, end, depth-1);
				
				Parallel.forkjoin(fork1, fork2);

			} else {
				
				Scheduler scheduler = Scheduler.get();

				for (int i=start; i<end; i++)
					scheduler.schedule(tasks[i]);

				tasks[end].setResult(tasks[end].call());

				for (int i=start; i<end; i++)
					tasks[i].getResult();				
			}
			
			return null;
		}
	}
	
	// Map pattern
	// -----------
	// .NET-like parallel loop pattern (i.e. similar to Parallel.For)
	
	public static void map (Kernel kernel, int start, int end)
	{
		For task = new Parallel.For(kernel,start,end,decompositionDepth);
		
		task.call();
	}
	
	
	static class For extends Task
	{
		Kernel kernel;
		int    start;
		int    end;
		int    depth;
		
		public For (Kernel kernel, int start, int end, int depth)
		{
			this.start  = start;
			this.end    = end;
			this.kernel = kernel;
			this.depth  = depth;
		}

		@Override
		public Object call() 
		{
			if ( (end-start>=tileWidth) && (depth>0) ) {

				int middle = (start+end)/2;
				
				For task1 = new For (kernel, start, middle, depth-1);
				For task2 = new For (kernel, middle+1, end, depth-1);
				
				Parallel.forkjoin(task1, task2);
			
			} else {
				
				for (int i=start; i<=end; i++)
					kernel.call(i);
			}

			return null;
		}
	}

	
	// Reduce pattern
	// --------------
	
	public static Object reduce (Kernel kernel, Combiner combiner, int start, int end)
	{
		Reduce task = new Parallel.Reduce(kernel,combiner,start,end,decompositionDepth);
		
		return task.call();
	}
	
	
	static class Reduce<T> extends Task<T>
	{
		Kernel<T>   kernel;
		Combiner<T> combiner;
		
		int    start;
		int    end;
		int    depth;
		
		public Reduce (Kernel<T> kernel, Combiner<T> combiner, int start, int end, int depth)
		{
			this.kernel   = kernel;
			this.combiner = combiner;
			
			this.start  = start;
			this.end    = end;
			this.depth  = depth;
		}

		@Override
		public T call() 
		{
			if ( (end-start>=tileWidth) && (depth>0) ) {

				int middle = (start+end)/2;
				
				Reduce<T> task1 = new Reduce (kernel, combiner, start, middle, depth-1);
				Reduce<T> task2 = new Reduce (kernel, combiner, middle+1, end, depth-1);
				
				Parallel.forkjoin(task1, task2);
				
				return combiner.combine(task1.getResult(), task2.getResult());
			
			} else {
				
				T result = combiner.identity();
				
				for (int i=start; i<=end; i++)
					result = combiner.combine(result, kernel.call(i));
				
				return result;
			}
		}
	}	
}
