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

	public Vector3D (Vector2D vector2d)
	{
		this.x = vector2d.x();
		this.y = vector2d.y();
		this.z = 0;
	}
	
	public double x()
	{
		return x;
	}
	
	public double y()
	{
		return y;
	}

	public double z()
	{
		return z;
	}
	
	public double length ()
	{
		return Math.sqrt(x*x+y*y+z*z);
	}
	
	public double squaredLength ()
	{
		return x*x + y*y + z*z;
	}
	
	public Vector3D normalize()
	{
		double length = this.length();
		
		if (length> Constants.EPSILON) {
			return new Vector3D (this.x/length, this.y/length, this.z/length);
		} else {
			return null;
		}
	}
	
	public Vector3D reverse ()
	{
		return new Vector3D(-x, -y, -z);
	}
	
	
	public Vector3D add (Vector3D v)
	{
		return new Vector3D (this.x + v.x, this.y + v.y, this.z + v.z);
	}
	
	public Vector3D substract (Vector3D v)
	{
		return new Vector3D (this.x - v.x, this.y - v.y, this.z - v.z);
	}

	public Vector3D multiply (double a)
	{
		return new Vector3D (a*this.x, a*this.y, a*this.z);
	}

	public Vector3D divide (double a)
	{
		return new Vector3D (this.x/a, this.y/a, this.z/a);
	}
	
	public double dotProduct (Vector3D v)
	{
		return this.x*v.x + this.y*v.y + this.z*v.z;
	}
	
	public double projection (Vector3D v)
	{
		double length = v.length();
		
		if (length>0)
			return dotProduct(v)/v.length();
		else
			return 0;
	}
	
	
	public double distance (Vector3D v)
	{
		double dx = this.x - v.x;
		double dy = this.y - v.y;
		double dz = this.z - v.z;
		
		return Math.sqrt(dx*dx+dy*dy+dz*dz);
	}
		

	public double squaredDistance (Vector3D v)
	{
		double dx = this.x - v.x;
		double dy = this.y - v.y;
		double dz = this.z - v.z;
		
		return dx*dx+dy*dy+dz*dz;
	}	
	
	public double angle (Vector3D v)
	{
		double a = this.length();
		double b = v.length();
		double p = this.dotProduct(v);
		
		if ( (a>0) && (b>0) ) {
			
			if ( Math.abs(p-a*b) < Constants.EPSILON )
				return 0;
			else
				return Math.acos( this.dotProduct(v) / (a*b) );
			
		} else
			return 0;
	}

	
	public Vector3D crossProduct (Vector3D other)
	{
		return new Vector3D ( this.y*other.z - this.z*other.y,
							  this.z*other.x - this.x*other.z,
							  this.x*other.y - this.y*other.x);
	}
	
	/**
	 * Given a normalized vector, reflect the vector 
	 * (as the path of a ball hitting a wall)
	 * @param normal Normal to the hitting surface
	 * @return Reflected vector
	 */
	public Vector3D reflect (Vector3D normal)
	{
		Vector3D displacement = normal.multiply ( 2*this.dotProduct(normal) ).reverse();
			
		return this.add (displacement);
	}
	
	
	// Overriden Object methods
	
	@Override
	public boolean equals (Object obj)
	{
		if (this==obj)
			return true;
		else {
			
			if (!(obj instanceof Vector3D)) {
				return false;
			} else {
				
				Vector3D v = (Vector3D) obj;
				
				return ( Math.abs(this.x-v.x) < Constants.EPSILON )
					&& ( Math.abs(this.y-v.y) < Constants.EPSILON )
					&& ( Math.abs(this.z-v.z) < Constants.EPSILON );
			}
		}
	}
	
	@Override 
	public int hashCode ()
	{
		return this.toString().hashCode(); 
	}


	@Override
	public String toString ()
	{
		return "("+x+", "+y+", "+z+")";
	}

}
