package sandbox.parallel.threading;

// Title:       Thread pool
// Version:     1.0
// Copyright:   April 2001
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import java.util.Vector;

// THREAD POOL
// Adapted from "Professional Java Programming" by Brett Spell (Wrox Press Ltd., December 2000)

public class ThreadPool implements Runnable 
{
	// Constants 

	public final static int  DEFAULT_MINIMUM_SIZE  = 0;
	public final static int  DEFAULT_MAXIMUM_SIZE  = 100;       // Integer.MAX_VALUE
	public final static long DEFAULT_RELEASE_DELAY = 60 * 1000; // 1 minute

	// Instance variables

	private int minimumSize;
	private int maximumSize;
	private int currentSize;

	private int availableThreads;

	private long releaseDelay;

	private Vector taskList;

	// Constructors

	public ThreadPool (int minSize, int maxSize, long delay) 
	{
		minimumSize = minSize;
		maximumSize = maxSize;
		releaseDelay = delay;
		taskList = new Vector();
		availableThreads = 0;
	}

	public ThreadPool() 
	{
		this (DEFAULT_MINIMUM_SIZE, DEFAULT_MAXIMUM_SIZE,DEFAULT_RELEASE_DELAY);
	}

	// Accessors &  Mutators

	public synchronized void setMinimumSize(int minSize) 
	{
		minimumSize = minSize;
	}

	public synchronized int getMinimumSize() 
	{
		return minimumSize;
	}

	public synchronized void setMaximumSize(int maxSize) 
	{
		maximumSize = maxSize;
	}

	public synchronized int getMaximumSize() 
	{
		return maximumSize;
	}

	public synchronized void setReleaseDelay(long delay) 
	{
		releaseDelay = delay;
	}

	public synchronized long getReleaseDelay() 
	{
		return releaseDelay;
	}

	// Task management

	public synchronized void addTask (Runnable runnable) 
	{
		taskList.addElement(runnable);

		if (availableThreads > 0) {

			this.notifyAll();

		} else {

			if (currentSize < maximumSize) {
				Thread t = new Thread(this);
				currentSize++;
				t.start();
			}
		}
	}

	protected synchronized Runnable getNextTask() 
	{
		Runnable task = null;

		if (taskList.size() > 0) {
			task = (Runnable)(taskList.elementAt(0));
			taskList.removeElementAt(0);
		}

		return task;
	}

	// Execution

	public void run() 
	{
		Runnable task = null;;
		boolean  exit = false;

		while (!exit) {

			//  Next task

			synchronized (this) {

				if (currentSize > maximumSize) {

					currentSize--;
					task = null;
					exit = true;

				} else {

					task = getNextTask();

					if (task == null) {

						try {
							availableThreads++;
							wait(releaseDelay);
							availableThreads--;
						} catch (InterruptedException ie) {
						}

						task = getNextTask();

						if (task == null) {

							if (currentSize > minimumSize) {
								currentSize--;
								exit = true;
							}
						}
					}
				}
			}

			// Task execution

			if (!exit) {

				try {

					task.run();

				} catch (Exception e) {

					System.err.println("Thread pool: Uncaught exception");

					e.printStackTrace(System.err);
				}
			}

		} 
	}

	// Test scaffolding

	/** /
  public static void main (String args[])
  {
    System.out.println("START");

    ThreadPool pool = new ThreadPool (0,3,1000);

    System.out.println("POOL");

    Runnable mytask = new Runnable() 
    {
      public void run () 
      { 
        int x = (int) (2000*Math.random());
        System.out.println("Sleep "+x+"ms"); 
	try { 
	  Thread.sleep(x);
	} catch (Exception e) {
	}
        System.out.println("Task "+x+"ms"); 
      }
    };

    pool.addTask(mytask);
    pool.addTask(mytask);
    pool.addTask(mytask);
    pool.addTask(mytask);
    pool.addTask(mytask);

    System.out.println("END");
  }
/**/

}