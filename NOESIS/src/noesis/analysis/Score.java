package noesis.analysis;

import ikor.math.DenseVector;
import ikor.model.data.DataModel;
import ikor.model.data.IntegerModel;
import ikor.model.data.RealModel;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import ikor.parallel.Task;

/**
 * Score.
 * 
 * Base class for node and link scores.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public abstract class Score extends DenseVector
{	
	public static final DataModel INTEGER_MODEL = new IntegerModel();
	public static final DataModel REAL_MODEL = new RealModel();

	private String id;
	private String description;
	private DataModel model = REAL_MODEL;
	
	// Constructor
	
	protected Score (int size)
	{
		super(size);
	}	
		
	// Metadata
	
	public String getName ()
	{
		return id;
	}
	
	public void setName (String id)
	{
		this.id = id;
	}
	
	public String getDescription ()
	{
		if (description!=null)
			return description;
		else
			return getName();
	}
	
	public void setDescription (String description)
	{
		this.description = description;
	}
	
	
	public DataModel getModel ()
	{
		return model;  
	}
	
	public void setModel (DataModel model)
	{
		this.model = model; 
	}
	
	
	public void setMetadata (Task creator)
	{
		Class type = creator.getClass();
		Label label = (Label) type.getAnnotation(Label.class);
		Description description = (Description) type.getAnnotation(Description.class);
		
		if (label!=null)
			setName( label.value() );
		
		if (description!=null)
			setDescription(description.value());
	}
	
	// Standard output
	
	public String toString ()
	{
		return getName() + ": " + toStringSummary();
	}

}
