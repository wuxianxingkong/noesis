package ikor.util.xml.rss;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.XMLEvent;

/**
 * RSS feed reader, adapted from http://www.vogella.com/tutorials/RSSFeed/article.html
 * 
 * @author Lars Vogel & Fernando Berzal
 */

public class RSSFeedReader extends FeedReader 
{
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String CHANNEL = "channel";
	static final String LANGUAGE = "language";
	static final String COPYRIGHT = "copyright";
	static final String LINK = "link";
	static final String AUTHOR = "author";
	static final String ITEM = "item";
	static final String PUB_DATE = "pubDate";
	static final String GUID = "guid";

	public RSSFeedReader (String url) 
	{
		super(url);
	}

	public Feed read()
	{
		Feed feed = null;
		
		try {
			boolean isFeedHeader = true;
			// Set header values intial to the empty string
			String description = "";
			String title = "";
			String link = "";
			String language = "";
			String copyright = "";
			String author = "";
			String pubdate = "";
			String guid = "";

			XMLEventReader eventReader = getStAXreader();
			
			// read the XML document
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					String localPart = event.asStartElement().getName()
							.getLocalPart();
					switch (localPart) {
					case ITEM:
						if (isFeedHeader) {
							isFeedHeader = false;
							feed = new Feed(title, link, description, language, copyright, pubdate);
						}
						event = eventReader.nextEvent();
						break;
					case TITLE:
						title = getCharacterData(event, eventReader);
						break;
					case DESCRIPTION:
						description = getCharacterData(event, eventReader);
						break;
					case LINK:
						link = getCharacterData(event, eventReader);
						break;
					case GUID:
						guid = getCharacterData(event, eventReader);
						break;
					case LANGUAGE:
						language = getCharacterData(event, eventReader);
						break;
					case AUTHOR:
						author = getCharacterData(event, eventReader);
						break;
					case PUB_DATE:
						pubdate = getCharacterData(event, eventReader);
						break;
					case COPYRIGHT:
						copyright = getCharacterData(event, eventReader);
						break;
					}
				} else if (event.isEndElement()) {
					if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
						FeedMessage message = new FeedMessage(title,description,author,pubdate,link,guid);
						feed.add(message);
						event = eventReader.nextEvent();
					}
				}
			}
			
			eventReader.close();
			
		} catch (Exception e) {
			feed = null;
		}
		
		return feed;
	}

	

	// Test program
	
	public static void main(String[] args) 
	{
		RSSFeedReader reader = new RSSFeedReader(args[0]);
		Feed feed = reader.read();
		
		System.out.println(feed);
		
		for (FeedMessage message: feed.getMessages()) {
			System.out.println(message);
		}

	}	
}
