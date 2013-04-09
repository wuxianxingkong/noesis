package sandbox.mdsd.graphics;

/**
 * Interface for update operations on drawings. 
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public interface DrawingUpdateListener 
{
	public void update (String id, int x, int y);
}
