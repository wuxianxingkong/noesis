package ikor.model.ui;

import ikor.model.ui.Component;

/**
 * Image component.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class Image extends Component 
{
	private String url;
	
	public Image ()
	{
	}
	
	public Image (String url)
	{
		this(url,url);
	}
	
	public Image (String id, String url)
	{
		this.setId(id);
		this.setUrl(url);
	}
	
	/**
	 * Get image URL.
	 * 
	 * @return Image URL
	 */
	public String getUrl() 
	{
		return url;
	}

	/**
	 * Set image URL.
	 * 
	 * @param url URL to be set
	 */
	public void setUrl(String url) 
	{
		this.url = url;
	}
}
