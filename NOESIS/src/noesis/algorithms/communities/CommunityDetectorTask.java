package noesis.algorithms.communities;

import ikor.math.Vector;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.AttributeNetwork;
import noesis.analysis.NodeScore;
import noesis.analysis.NodeScoreTask;

public class CommunityDetectorTask extends NodeScoreTask 
{
	private CommunityDetector detector;
	private Vector best;
	
	public CommunityDetectorTask (CommunityDetector detector, AttributeNetwork network)
	{
		super(NodeScore.INTEGER_MODEL, network);

		this.detector = detector;
		this.best = null;
	}
	
	@Override
	public void compute ()
	{
		detector.compute();
		best = detector.getBest();
	
		int size = getNetwork().size();
		
		NodeScore score = new NodeScore(this,getNetwork());
	
		for (int node=0; node<size; node++)
			score.set (node, best.get(node));
		
		setResult(score);
	}

	@Override
	public double compute(int node) 
	{
		if (best==null) 
			compute();
		
		return best.get(node);
	}

	@Override
	public String getName ()
	{
		Class type = detector.getClass();
		Label label = (Label) type.getAnnotation(Label.class);
				
		if (label!=null)
			return label.value();
		else
			return null;
	}
	
	@Override
	public String getDescription()
	{
		Class type = detector.getClass();
		Description description = (Description) type.getAnnotation(Description.class);
		
		if (description!=null)
			return description.value();
		else
			return null;
	}
}
