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
	private Attribute x;
	private Attribute y;
	
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
		
		this.x = network.getNodeAttribute("x");
		this.y = network.getNodeAttribute("y");
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
		
		for (int node=0; node<network.size(); node++) {
			for (int index=0; index<network.outDegree(node); index++) {
				linkRenderer.render(this, node, network.outLink(node,index));
			}
		}
		
		for (int node=0; node<network.size(); node++) {
			nodeRenderer.render(this, node);
		}
	}
	
	/**
	 * Update network
	 */
	
	public void update ()
	{
		for (int node=0; node<network.size(); node++) {
			for (int index=0; index<network.outDegree(node); index++) {
				linkRenderer.update(this, node, network.outLink(node,index));
			}
		}
		
		for (int node=0; node<network.size(); node++) {
			nodeRenderer.update(this, node);
		}
	}

	/**
	 * Update node
	 */
	
	public void update (int node)
	{		
		for (int index=0; index<network.outDegree(node); index++) {
			linkRenderer.update(this, node, network.outLink(node,index));
		}
	
		for (int index=0; index<network.inDegree(node); index++) {
			linkRenderer.update(this, network.inLink(node,index), node);
		}		
	
		nodeRenderer.update(this, node);
	}
	
	
	/**
	 * Node ID
	 * @param node index
	 * @return node ID
	 */
	
	protected String getNodeId (int node)
	{
		return "node"+node;
	}
	
	/**
	 * Link ID
	 * @param source link source index
	 * @param target link target index
	 * @return link ID
	 */
	
	protected String getLinkId (int source, int target)
	{
		return "link-"+getNodeId(source)+"-"+getNodeId(target);
	}
	
	
	public int getX (int node)
	{
		return (int) ( getWidth() * (Double) x.get(node) );
	}
	
	public int getY (int node)
	{
		return (int) ( getHeight() * (Double) y.get(node) );
	}
	
	
	
	public void createCompleteNetwork (AttributeNetwork network)
	{
	
	}




}
