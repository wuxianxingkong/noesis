package sandbox.mdsd.graphics.styles;

public class RadialGradient extends Gradient 
{
	private float centerX;
	private float centerY;
	
	private float radius;
	
	public RadialGradient (float centerX, float centerY, float radius)
	{
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
	}

	public float getCenterX() 
	{
		return centerX;
	}

	public void setCenterX(float centerX) 
	{
		this.centerX = centerX;
	}

	public float getCenterY() 
	{
		return centerY;
	}

	public void setCenterY(float centerY) 
	{
		this.centerY = centerY;
	}

	public float getRadius() 
	{
		return radius;
	}

	public void setRadius(float radius) 
	{
		this.radius = radius;
	}


	public String toString()
	{
		return String.format("gc(%.2f,%.2f)r%.2f", centerX, centerY, radius) + super.toString();
	}	
}
