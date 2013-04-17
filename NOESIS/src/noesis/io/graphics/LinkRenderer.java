package noesis.io.graphics;

public interface LinkRenderer 
{
	// Link width
	
	public int getWidth ();

	public void setWidth (int width);
	
	// Link rendering

	public void render (NetworkRenderer drawing, int source, int target);
	
	public void update (NetworkRenderer drawing, int source, int target);
	
}