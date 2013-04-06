package sandbox.mdsd.graphics;

import ikor.collection.List;
import ikor.collection.Dictionary;
import ikor.collection.DynamicList;
import ikor.collection.DynamicDictionary;
import ikor.collection.ReadOnlyCollection;


public class Drawing 
{
	private List<DrawingElement> elements;
	
	public Drawing ()
	{
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

	
}
