package ikor.util.xml.rss;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

/**
 * RSS feed reader, adapted from http://www.vogella.com/tutorials/RSSFeed/article.html
 * 
 * @author Lars Vogel
 */

public class RSSFeedReader 
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

	private URL url;

	public RSSFeedReader (String feedUrl) 
	{
		try {
			this.url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public RSSFeed read()
	{
		RSSFeed feed = null;
		
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

			XMLEventReader eventReader = reader();
			
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
							feed = new RSSFeed(title, link, description, language, copyright, pubdate);
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
						RSSMessage message = new RSSMessage();
						message.setAuthor(author);
						message.setDescription(description);
						message.setGuid(guid);
						message.setLink(link);
						message.setTitle(title);
						feed.getMessages().add(message);
						event = eventReader.nextEvent();
						continue;
					}
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return feed;
	}

	
	private XMLEventReader reader() 
		throws FactoryConfigurationError, XMLStreamException, IOException 
	{
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();

		return inputFactory.createXMLEventReader(url.openStream());
	}

	
	private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
		throws XMLStreamException 
	{
		String result = "";
		event = eventReader.nextEvent();
		if (event instanceof Characters) {
			result = event.asCharacters().getData();
		}
		return result;
	}


	// Test program
	
	public static void main(String[] args) 
	{
		RSSFeedReader reader = new RSSFeedReader(args[0]);
		RSSFeed feed = reader.read();
		
		System.out.println(feed);
		
		for (RSSMessage message: feed.getMessages()) {
			System.out.println(message);
		}

	}	
}
