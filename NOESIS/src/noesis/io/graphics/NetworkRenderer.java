package noesis.io.graphics;

import ikor.model.graphics.Drawing;

import noesis.AttributeNetwork;
import noesis.Attribute;

/**
 * Network drawing
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class NetworkRenderer extends Drawing
{
	// Network
	
	private AttributeNetwork network;
	private Attribute<Double> x;
	private Attribute<Double> y;
	
	// Renderers
	
	private BackgroundRenderer backgroundRenderer;
	private NodeRenderer nodeRenderer;
	private LinkRenderer linkRenderer;

	
	/**
	 * Constructor
	 */

	public NetworkRenderer (AttributeNetwork network, int width, int height) 
	{
		super(width, height);
		
		setNetwork(network);
		
		this.backgroundRenderer = new DefaultBackgroundRenderer();
		this.nodeRenderer = new DefaultNodeRenderer();
		this.linkRenderer = new DefaultLinkRenderer();
	}
	
	/**
	 * Get underlying network 
	 */
	
	public AttributeNetwork getNetwork() 
	{
		return network;
	}

	/**
	 * Set underlying network 
	 */

	public void setNetwork(AttributeNetwork network) 
	{
		this.network = network;
		
		if (network!=null) {
			this.x = network.getNodeAttribute("x");
			this.y = network.getNodeAttribute("y");
		} else {
			this.x = null;
			this.y = null;
		}
	}
	
	/**
	 * Get background renderer
	 */
	
	public BackgroundRenderer getBackgroundRenderer() 
	{
		return backgroundRenderer;
	}

	/**
	 * Set background renderer
	 */
	
	public void setBackgroundRenderer(BackgroundRenderer backgroundRenderer) 
	{
		this.backgroundRenderer = backgroundRenderer;
	}
	
	/**
	 * Get node renderer
	 */

	public NodeRenderer getNodeRenderer() 
	{
		return nodeRenderer;
	}

	/**
	 * Set node renderer
	 */
	
	public void setNodeRenderer(NodeRenderer nodeRenderer) 
	{
		this.nodeRenderer = nodeRenderer;
	}
	
	/**
	 * Get link renderer
	 */

	public LinkRenderer getLinkRenderer() 
	{
		return linkRenderer;
	}

	/**
	 * Set link renderer
	 */
	
	public void setLinkRenderer(LinkRenderer linkRenderer) 
	{
		this.linkRenderer = linkRenderer;
	}	
	
	/**
	 * Draw network
	 */
	
	public void render ()
	{
		super.clear();
		
		if (backgroundRenderer!=null)
			backgroundRenderer.render(this);

		if (network!=null) {
			
			for (int node=0; node<network.size(); node++) {
				for (int index=0; index<network.outDegree(node); index++) {
					linkRenderer.render(this, node, network.outLink(node,index));
				}
			}

			for (int node=0; node<network.size(); node++) {
				nodeRenderer.render(this, node);
			}
		}
	}
	
	/**
	 * Update network
	 */
	
	public void update ()
	{
		if (backgroundRenderer!=null)
			backgroundRenderer.update(this);
		
		if (network!=null) {

			for (int node=0; node<network.size(); node++) {
				for (int index=0; index<network.outDegree(node); index++) {
					linkRenderer.update(this, node, network.outLink(node,index));
				}
			}

			for (int node=0; node<network.size(); node++) {
				nodeRenderer.update(this, node);
			}
		}
	}

	/**
	 * Update node
	 */
	
	public void update (int node)
	{
		if (network!=null) {
			
			for (int index=0; index<network.outDegree(node); index++) {
				linkRenderer.update(this, node, network.outLink(node,index));
			}

			for (int index=0; index<network.inDegree(node); index++) {
				linkRenderer.update(this, network.inLink(node,index), node);
			}		

			nodeRenderer.update(this, node);
		}
	}
	
	
	/**
	 * Node ID
	 * @param node index
	 * @return node ID
	 */
	
	public String getNodeId (int node)
	{
		return "node"+node;
	}
	
	/**
	 * Node index given figure ID
	 * @param id Figure ID
	 * @return Node index (-1 if figure ID is not a node ID)
	 */
	
	public int getNodeIndex (String id)
	{
		if (id.startsWith("node")) {
			return Integer.parseInt(id.substring(4));
		} else {
			return -1;
		}
	}
	
	/**
	 * Link ID
	 * @param source link source index
	 * @param target link target index
	 * @return link ID
	 */
	
	public String getLinkId (int source, int target)
	{
		return "link-s"+getNodeId(source)+"-t"+getNodeId(target);
	}
	
	/**
	 * Link source index given figure ID
	 * @param id Figure ID
	 * @return Link source index (-1 if figure ID is not a link ID)
	 */
	
	public int getLinkSourceIndex (String id)
	{
		if (id.startsWith("link")) {
			return getNodeIndex(id.substring(6,id.indexOf("-t")));
		} else {
			return -1;
		}
	}
	
	/**
	 * Link target index given figure ID
	 * @param id Figure ID
	 * @return Link target index (-1 if figure ID is not a link ID)
	 */
	
	public int getLinkTargetIndex (String id)
	{
		if (id.startsWith("link")) {
			return getNodeIndex(id.substring( id.indexOf("-t")+2));
		} else {
			return -1;
		}
	}	
	
	
	/**
	 * Node coordinates
	 * @param node Node index
	 * @return Node X coordinate
	 */
	
	public int getX (int node)
	{
		if (x!=null)
			return (int) ( getWidth() * x.get(node) );
		else
			return 0;
	}
	
	/**
	 * Node coordinates.
	 * (0,0) at bottom left corner.
	 * 
	 * @param node Node index
	 * @return Node Y coordinate
	 */
	
	public int getY (int node)
	{
		if (y!=null)
			return (int) ( getHeight() * ( 1.0 - y.get(node) ) );
		else
			return 0;
	}

}
