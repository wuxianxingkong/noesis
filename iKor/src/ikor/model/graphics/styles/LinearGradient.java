package ikor.model.graphics.styles;

public class LinearGradient extends Gradient 
{
	private float startX;
	private float startY;
	
	private float endX;
	private float endY;
	
	public LinearGradient (float startX, float startY, float endX, float endY)
	{
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	public float getStartX() 
	{
		return startX;
	}

	public void setStartX(int startX) 
	{
		this.startX = startX;
	}

	public float getStartY() 
	{
		return startY;
	}

	public void setStartY(float startY) 
	{
		this.startY = startY;
	}

	public float getEndX() 
	{
		return endX;
	}

	public void setEndX(float endX) 
	{
		this.endX = endX;
	}

	public float getEndY() 
	{
		return endY;
	}

	public void setEndY(float endY) 
	{
		this.endY = endY;
	}
	
	public String toString()
	{
		return String.format("gl(%.2f,%.2f)(%.2f,%.2f)", startX, startY, endX, endY) + super.toString();
	}

}
