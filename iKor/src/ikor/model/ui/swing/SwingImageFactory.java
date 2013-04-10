package ikor.model.ui.swing;

import ikor.model.ui.Image;
import ikor.model.ui.UIFactory;

import javax.swing.JLabel;

public class SwingImageFactory implements UIFactory<SwingUI,Image>
{
	@Override
	public void build (SwingUI ui, Image image) 
	{
		ui.addComponent ( new JLabel( new javax.swing.ImageIcon(ui.loadImage(image.getUrl())) ) );	
	}
}
