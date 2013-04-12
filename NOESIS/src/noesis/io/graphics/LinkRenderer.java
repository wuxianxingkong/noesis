package noesis.io.graphics;

public interface LinkRenderer 
{
	public void render (NetworkRenderer drawing, int source, int target);
	
	public void update (NetworkRenderer drawing, int source, int target);
	
}