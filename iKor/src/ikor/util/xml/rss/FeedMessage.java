package ikor.util.xml.rss;

/**
 * Feed message, adapted from http://www.vogella.com/tutorials/RSSFeed/article.html
 * 
 * @author Lars Vogel & Fernando Berzal
 */

public class FeedMessage 
{
	private String title;
	private String content;
	private String author;
	private String date;
	private String link;
	private String id;

	// Constructor
	
	public FeedMessage (String title, String content, String author, String date, String link, String id)
	{
		this.title = title;
		this.content = content;
		this.author = author;
		this.date = date;
		this.link = link;
		this.id = id;
	}
	
	// Getters & setters
	
	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title) 
	{
		this.title = title;
	}

	public String getContent() 
	{
		return content;
	}

	public void setContent(String content) 
	{
		this.content = content;
	}

	public String getLink() 
	{
		return link;
	}

	public void setLink(String link) 
	{
		this.link = link;
	}

	public String getAuthor() 
	{
		return author;
	}

	public void setAuthor(String author) 
	{
		this.author = author;
	}

	public String getDate() 
	{
		return date;
	}

	public void setDate(String date) 
	{
		this.date = date;
	}
	
	public String getID() 
	{
		return id;
	}

	public void setID(String id) 
	{
		this.id = id;
	}
	
	// Standard output

	@Override
	public String toString() 
	{
		return "FeedMessage"
	         + "\n - title: " + title 
	         + "\n - content: " + content
	         + "\n - author: " + author 
	         + "\n - date: " + date 
	         + "\n - link: " + link 
	         + "\n - id: " + id;
	}
}
