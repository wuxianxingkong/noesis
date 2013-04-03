package sandbox.mdsd.ui.swing;

import sandbox.mdsd.ui.Image;
import sandbox.mdsd.ui.UIFactory;

public class SwingIconFactory implements UIFactory<SwingUI,Image>
{
	@Override
	public void build(SwingUI ui, Image image) 
	{
		ui.setIconImage( ui.loadIcon(image.getUrl()).getImage() );	
	}

}
