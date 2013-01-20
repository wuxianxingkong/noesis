package sandbox.np.bb;

import sandbox.ai.Solver;

import ikor.collection.*;

public class BranchAndBoundOptimizer extends Solver
{
	Evaluator<OptimizationProblem> evaluator;
	PriorityQueue<OptimizationProblem> queue;
	
	OptimizationProblem best;
	float globalBound;
	int   nodes;
	
	public BranchAndBoundOptimizer (OptimizationProblem problem, Evaluator<OptimizationProblem> evaluator)
	{
		super(problem);
		
		this.evaluator = evaluator;
		this.queue = new DynamicPriorityQueue( new BoundEvaluator() );		
		
		
		best = problem;
		best.setBound( (float) evaluator.evaluate(best));
		
		queue.add(best); // Root node
		nodes = 0;
	}
	
	public void solve ()
	{
		minimize();
	}
	
	public void minimize ()
	{
		OptimizationProblem current;
		OptimizationProblem children[];
		
		globalBound = Float.MAX_VALUE;
		
		while (queue.size()>0) {
			
			current = queue.get();
			nodes++;
			
			//System.out.println("Current: "+current);
			//System.out.println("Best: "+best);
			
			if (current.getBound() <= globalBound) {
				
				children = getChildren(current);
				
				for (int i=0; i<children.length; i++) {
					
					if ( children[i].isSolved() && children[i].getValue()<=globalBound ) {
						
						best = children[i];
						globalBound = children[i].getValue();
						
						System.out.println("Best: "+best);
						
					} else if ( children[i].getBound() <= globalBound ) {
						
						queue.add(children[i]);
						
						// if (children[i].getUpperBound() < globalBound ) {
						//    best = children[i].getUpperBoundSolution();
						//	  globalBound = children[i].getUpperBound();
						// }
					}
				}
			}
		}
	}
	
	private OptimizationProblem[] getChildren (OptimizationProblem problem)
	{
		int variable = firstVariable(problem);  // variable = mrvVariable(problem);		
		int candidates[] = problem.values(variable); // Different values for the chosen variable
		OptimizationProblem children[] = new OptimizationProblem[candidates.length];
		
		for (int i=0; i<children.length; i++) {
			children[i] = problem.child(variable, candidates[i]);
			children[i].setBound ( (float) evaluator.evaluate(children[i]));
		}
		
		return children;
	}
	
	public String toString ()
	{
		return "B&B: "+ nodes +" explored nodes -> " + best.toString();
	}
	
	
	public class BoundEvaluator implements Evaluator<OptimizationProblem>
	{
		@Override
		public double evaluate(OptimizationProblem object) 
		{
			return object.getBound();
		}		
	}
}
