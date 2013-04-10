package ikor.model.ui.swing;

import ikor.model.ui.Image;
import ikor.model.ui.UIFactory;

import java.awt.Dimension;


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
	        
	        setPreferredSize( new Dimension(2*image.getWidth(null), 2*image.getHeight(null)) );
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
