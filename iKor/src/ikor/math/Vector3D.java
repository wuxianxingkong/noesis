package ikor.math;

public class Vector3D implements java.io.Serializable
{
	double x,y,z;

	public Vector3D (double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	// TODO Vector2D++

	
	@Override
	public String toString ()
	{
		return "("+x+", "+y+", "+z+")";
	}

}
