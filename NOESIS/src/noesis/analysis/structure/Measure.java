package noesis.analysis.structure;

import ikor.math.Vector;
import ikor.model.data.DataModel;
import ikor.model.data.IntegerModel;
import ikor.model.data.RealModel;

public abstract class Measure extends Vector
{	
	protected Measure (int size)
	{
		super(size);
	}	
	
	// Computation method
	
	public abstract void compute ();
	
	// Measure metadata
	
	public abstract String getName ();
	
	public String getDescription ()
	{
		return getName();
	}
	
	protected static final DataModel INTEGER_MODEL = new IntegerModel();
	protected static final DataModel REAL_MODEL = new RealModel();
	
	public DataModel getModel ()
	{
		return REAL_MODEL; 
	}
	
	// Standard output
	
	public String toString ()
	{
		return getName() + ": " + toStringSummary();
	}

}
