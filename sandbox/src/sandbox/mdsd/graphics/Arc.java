package sandbox.mdsd.graphics;

public class Arc extends Ellipse 
{
	private double startAngle;
	private double extent;

	public Arc (String id, Style style, Style border, 
			int centerX, int centerY,
			int radius, 
			double startAngle, double extent) 
	{
		super(id, style, border, centerX, centerY, radius, radius);
		
		this.startAngle = startAngle;
		this.extent = extent;
	}
	
	public Arc (String id, Style style, Style border, 
			int centerX, int centerY,
			int radiusX, int radiusY, 
			double startAngle, double extent) 
	{
		super(id, style, border, centerX, centerY, radiusX, radiusY);
		
		this.startAngle = startAngle;
		this.extent = extent;
	}

	
	public double getStartAngle() 
	{
		return startAngle;
	}

	public void setStartAngle(double startAngle) 
	{
		this.startAngle = startAngle;
	}
	
	public double getExtent ()
	{
		return extent;
	}
	
	public void setExtent (double extent)
	{
		this.extent = extent;
	}

	public double getEndAngle() 
	{
		return startAngle+extent;
	}

	public void setEndAngle (double endAngle) 
	{
		this.extent = endAngle - this.startAngle;
	}

	public String toString ()
	{
		return String.format("a%.3f+%.3f", startAngle, extent) + super.toString();
	}
	
	@Override
	public boolean containsPoint(int rx, int ry) 
	{
		boolean in = super.containsPoint(rx, ry);
		
		if (in) {
			double cx = getCenterX();
			double cy = getCenterY();
			double px = getUnrotatedX(cx, cy, rx, ry);
			double py = getUnrotatedY(cx, cy, rx, ry);
			
			double angle = Math.atan2(cy-py, px-cx);
			
			if (angle<0)
				angle += 2*Math.PI;
				
			in = (angle>=startAngle) && (angle<=startAngle+extent);
		}
		
		return in;
	}
	
}
