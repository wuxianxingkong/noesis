package ikor.util.xml.rss;

import ikor.collection.*;

/**
 * XML data feed, adapted from http://www.vogella.com/tutorials/RSSFeed/article.html
 * 
 * @author Lars Vogel & Fernando Berzal
 */
public class Feed 
{
	private String title;
	private String link;
	private String content;
	private String language;
	private String copyright;
	private String date;

	final List<FeedMessage> entries = CollectionFactory.createList();

	// Constructor
	
	public Feed(String title, String link, String content, String language, String copyright, String date) 
	{
		this.title = title;
		this.link = link;
		this.content = content;
		this.language = language;
		this.copyright = copyright;
		this.date = date;
	}

	// Messages
	
	public void add (FeedMessage message)
	{
		entries.add(message);
	}
	
	public List<FeedMessage> getMessages() 
	{
		return entries;
	}
	
	public int getMessageCount ()
	{
		return entries.size();
	}
	
	public FeedMessage getMessage (int i)
	{
		return entries.get(i);
	}
	
	// Feed metadata

	public String getTitle() 
	{
		return title;
	}

	public String getLink() 
	{
		return link;
	}

	public String getContent() 
	{
		return content;
	}

	public String getLanguage() 
	{
		return language;
	}

	public String getCopyright() 
	{
		return copyright;
	}

	public String getDate() 
	{
		return date;
	}

	// Standard output
	
	@Override
	public String toString() 
	{
		return "Feed"
			 + "\n - title: " + title
	         + "\n - copyright: " + copyright 
	         + "\n - content: " + content
			 + "\n - language: " + language 
			 + "\n - link: " + link 
			 + "\n - date: " + date; 
	}
}
