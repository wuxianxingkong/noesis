package noesis.io.graphics;


public interface NodeRenderer 
{
	// Node size
	
	public int getSize ();

	public void setSize (int size);
	
	// Node rendering
	
	public void render (NetworkRenderer drawing, int node);
	
	public void update (NetworkRenderer drawing, int node);
	
}
