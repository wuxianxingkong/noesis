package sandbox.parallel;

public class Parallel 
{
	
	// Fork-join pattern
	
	public static void forkjoin (Task a, Task b)
	{
		Scheduler.get().schedule(b);
		
		a.call();
		
		b.getResult();
	}
	
	// .NET-like parallel loop pattern (i.e. similar to Parallel.For)
	
	public static final int MAX_DEPTH = 4; // i.e. 2^(MAX_DEPTH-1) parallel tasks
	
	public static void map (int start, int end, TaskKernel kernel)
	{
		ForTask task = new Parallel.ForTask(start,end,kernel,MAX_DEPTH);
		task.call();
	}
	
	
	static class ForTask extends FutureTask
	{
		int        start;
		int        end;
		TaskKernel kernel;
		int        depth;
		
		public ForTask(int start, int end, TaskKernel kernel, int depth)
		{
			this.start  = start;
			this.end    = end;
			this.kernel = kernel;
			this.depth  = depth;
		}

		@Override
		public Object call() 
		{
			if ((start<end) && (depth>1)) {

				int     middle = (start+end)/2;
				ForTask task1 = new ForTask (start, middle, kernel, depth-1);
				ForTask task2 = new ForTask (middle+1, end, kernel, depth-1);
				
				Parallel.forkjoin(task1, task2);
			
			} else {
				
				for (int i=start; i<=end; i++)
					kernel.call(i);
			}

			return null;
		}
	}

}
