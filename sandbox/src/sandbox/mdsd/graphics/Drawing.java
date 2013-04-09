package sandbox.mdsd.graphics;

import ikor.collection.List;
import ikor.collection.Dictionary;
import ikor.collection.DynamicList;
import ikor.collection.DynamicDictionary;
import ikor.collection.ReadOnlyCollection;


public class Drawing
{
	private int width;
	private int height;
	private List<DrawingElement> elements;
	
	public Drawing (int width, int height)
	{
		this.width = width;
		this.height = height;
		this.elements = new DynamicList<DrawingElement>();
	}

	// Drawing elements
	// ----------------

	public void add (DrawingElement element)
	{
		elements.add(element);
	}
	
	public void remove (DrawingElement element)
	{
		elements.remove(element);
	}
	
	public ReadOnlyCollection<DrawingElement> getElements() 
	{
		return elements;
	}
	
	
	public DrawingElement getDrawingElement (String id)
	{
		for (DrawingElement element: elements)
			if (element.getId().equals(id))
				return element;
		
		return null;
	}
	
	public DrawingElement getDrawingElement (int x, int y)
	{
		DrawingElement element;
		
		for (int i=elements.size()-1; i>=0; i--) {
			element = elements.get(i);
			
			if (element.containsPoint(x,y))
				return element;
		}
		
		return null;
	}

	public String getElement (int x, int y)
	{
		DrawingElement element = getDrawingElement(x,y);
		
		if (element!=null)
			return element.getId();
		else
			return null;
	}
	
	// Drawing styles
	// --------------
		
	public ReadOnlyCollection<Style> getStyles() 
	{
		Style style;
	
		Dictionary<String,Style> styles = new DynamicDictionary<String,Style>();
		
		for (DrawingElement element: elements) {
			
			style = element.getStyle();
			
			if (style!=null)
				styles.set (style.toString(), style);

			style = element.getBorder();

			if (style!=null)
				styles.set (style.toString(), style);
		}
		
		return styles.values();
	}

	// Dimensions
	// ----------
	
	public int getWidth() 
	{
		return width;
	}

	public void setWidth(int width) 
	{
		this.width = width;
	}

	public int getHeight() 
	{
		return height;
	}

	public void setHeight(int height) 
	{
		this.height = height;
	}	
}
