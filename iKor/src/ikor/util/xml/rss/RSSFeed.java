package ikor.util.xml.rss;

import ikor.collection.*;

/**
 * RSS feed, adapted from http://www.vogella.com/tutorials/RSSFeed/article.html
 * 
 * @author Lars Vogel
 */
public class RSSFeed 
{
	private String title;
	private String link;
	private String description;
	private String language;
	private String copyright;
	private String pubDate;

	final List<RSSMessage> entries = CollectionFactory.createList();

	public RSSFeed(String title, String link, String description, String language, String copyright, String pubDate) 
	{
		this.title = title;
		this.link = link;
		this.description = description;
		this.language = language;
		this.copyright = copyright;
		this.pubDate = pubDate;
	}

	public List<RSSMessage> getMessages() 
	{
		return entries;
	}

	public String getTitle() 
	{
		return title;
	}

	public String getLink() 
	{
		return link;
	}

	public String getDescription() 
	{
		return description;
	}

	public String getLanguage() 
	{
		return language;
	}

	public String getCopyright() 
	{
		return copyright;
	}

	public String getPubDate() 
	{
		return pubDate;
	}

	@Override
	public String toString() 
	{
		return "RSS Feed"
	         + "\n - copyright: " + copyright 
	         + "\n - description: " + description
			 + "\n - language: " + language 
			 + "\n - link: " + link 
			 + "\n - pubDate: " + pubDate 
			 + "\n - title: " + title;
	}
}
