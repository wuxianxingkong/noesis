package ikor.math;

/**
 * Immutable 2D vector.
 * 
 * @author Fernando Berzal
 *
 */

public class Vector2D implements java.io.Serializable 
{
	double x,y;
	
	public Vector2D (double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	
	public double x()
	{
		return x;
	}
	
	public double y()
	{
		return y;
	}
	
	public double length ()
	{
		return Math.sqrt(x*x+y*y);
	}
	
	public double squaredLength ()
	{
		return x*x + y*y;
	}
	
	public Vector2D normalize()
	{
		double length = this.length();
		
		if (length> Constants.EPSILON) {
			return new Vector2D (this.x/length, this.y/length);
		} else {
			return null;
		}
	}
	
	public Vector2D reverse ()
	{
		return new Vector2D(-x, -y);
	}
	
	
	public Vector2D add (Vector2D v)
	{
		return new Vector2D (this.x + v.x, this.y + v.y);
	}
	
	public Vector2D substract (Vector2D v)
	{
		return new Vector2D (this.x - v.x, this.y - v.y);
	}

	public Vector2D multiply (double a)
	{
		return new Vector2D (a*this.x, a*this.y);
	}

	public Vector2D divide (double a)
	{
		return new Vector2D (this.x/a, this.y/a);
	}
	
	public double dotProduct (Vector2D v)
	{
		return this.x*v.x + this.y*v.y;
	}
	
	public double distance (Vector2D v)
	{
		double dx = this.x - v.x;
		double dy = this.y - v.y;
		
		return Math.sqrt(dx*dx+dy*dy);
	}

	public double squaredDistance (Vector2D v)
	{
		double dx = this.x - v.x;
		double dy = this.y - v.y;
		
		return dx*dx+dy*dy;
	}	
	
	
	/**
	 * Given a normalized vector, reflect the vector 
	 * (as the path of a ball hitting a wall)
	 * @param normal Normal to the hitting surface
	 * @return Reflected vector
	 */
	public Vector2D reflect (Vector2D normal)
	{
		Vector2D displacement = normal.multiply ( 2*this.dotProduct(normal) ).reverse();
			
		return this.add (displacement);
	}
	
	
	// Overriden Object methods
	
	@Override
	public boolean equals (Object obj)
	{
		if (this==obj)
			return true;
		else {
			
			if (!(obj instanceof Vector2D)) {
				return false;
			} else {
				
				Vector2D v = (Vector2D) obj;
				
				return ( Math.abs(this.x-v.x) < Constants.EPSILON )
					&& ( Math.abs(this.y-v.y) < Constants.EPSILON );
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
		return "("+x+", "+y+")";
	}

}
