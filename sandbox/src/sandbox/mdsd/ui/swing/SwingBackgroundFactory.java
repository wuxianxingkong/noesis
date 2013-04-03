package sandbox.mdsd.ui.swing;

import sandbox.mdsd.ui.Image;
import sandbox.mdsd.ui.UIFactory;

public class SwingBackgroundFactory implements UIFactory<SwingUI,Image>
{
	@Override
	public void build(SwingUI ui, Image image) 
	{
		ui.setContentPane( new ImagePanel( ui.loadImage(image.getUrl()) ) );	
	}
	
	// Image panel
	class ImagePanel extends javax.swing.JComponent 
	{
	    private java.awt.Image image;
	    
	    public ImagePanel (java.awt.Image image) 
	    {
	        this.image = image;
	    }
	    
	    @Override
	    protected void paintComponent (java.awt.Graphics g) 
	    {
	        if (image!=null) {
	            
	        	java.awt.Dimension d = getSize();
	            
	            g.drawImage (image, (d.width-image.getWidth(null))/2, (d.height-image.getHeight(null))/2, this);
	        }
	    }
	}

}
