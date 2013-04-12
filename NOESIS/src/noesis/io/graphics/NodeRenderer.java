package noesis.io.graphics;


public interface NodeRenderer 
{
	public void render (NetworkRenderer drawing, int node);
	
	public void update (NetworkRenderer drawing, int node);
	
}
