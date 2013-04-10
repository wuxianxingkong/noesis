package ikor.model.ui.swing;

import ikor.model.ui.Image;
import ikor.model.ui.UIFactory;

public class SwingIconFactory implements UIFactory<SwingUI,Image>
{
	@Override
	public void build(SwingUI ui, Image image) 
	{
		ui.setIconImage( ui.loadIcon(image.getUrl()).getImage() );	
	}

}
