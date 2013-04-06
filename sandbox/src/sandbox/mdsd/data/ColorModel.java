package sandbox.mdsd.data;

import java.awt.Color;

public class ColorModel implements DataModel<Color> 
{

	@Override
	public String toString (Color object) 
	{
		return "#"+String.format("%06x",object.getRGB()-0xff000000);
	}
	
	@Override
	public Color fromString (String string) 
	{
		Color color;
		
		try {
			color = new Color(0xff000000 + Integer.parseInt(string.substring(1),16));
		} catch (Exception error) {
			color = null;
		}
		
		return color;
	}
}