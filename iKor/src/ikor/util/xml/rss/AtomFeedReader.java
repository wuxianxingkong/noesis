package ikor.util.xml.rss;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Atom feed reader, adapted from the RSS feed reader at http://www.vogella.com/tutorials/RSSFeed/article.html
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class AtomFeedReader extends FeedReader 
{
	public AtomFeedReader (String url) 
	{
		super(url);
	}

	public Feed read()
	{
		String title, content, author, link, id, date, language, copyright;
		Feed feed = null;
		
		try {

			InputStream input = getInputStream();
			
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(input);
			
			XPath xpath = XPathFactory.newInstance().newXPath();

			date = xpath.evaluate("/feed/updated", xmlDocument);
			title = xpath.evaluate("/feed/title", xmlDocument);
			content = xpath.evaluate("/feed/content", xmlDocument);
			author = xpath.evaluate("/feed/author", xmlDocument);
			link = xpath.evaluate("/feed/link[@rel='self']/@href", xmlDocument);
			id = xpath.evaluate("/feed/id", xmlDocument);
			language = xpath.evaluate("/feed/language", xmlDocument);
			copyright = xpath.evaluate("/feed/rights", xmlDocument);

			feed = new Feed(title, link, content, language, copyright, date);
			
				
			NodeList entries = (NodeList) xpath.evaluate("/feed/entry", xmlDocument, XPathConstants.NODESET);

			for (int i = 0; i < entries.getLength(); i++) {
				Element entry = (Element) entries.item(i);
				title = xpath.evaluate("title", entry);
				content = ((Node)xpath.evaluate("content", entry, XPathConstants.NODE)).getTextContent();
				author = xpath.evaluate("author/email", entry)+" ("+xpath.evaluate("author/name", entry)+")";
				date = xpath.evaluate("updated", entry);
				link = xpath.evaluate("link[@rel='self']/@href", entry);
				id = xpath.evaluate("id", entry); 

				FeedMessage message = new FeedMessage(title,content,author,date,link,id);
				feed.add(message);
			}

			input.close();
			
		} catch (Exception e) {
			feed = null;
		}
		
		return feed;
	}


	// XML to string...  vs. node.getTextContent()
	
	public static String nodeToString (Node node) 
		throws TransformerException
	{
		StringWriter sw = new StringWriter();
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.transform(new DOMSource(node), new StreamResult(sw));

		return sw.toString();
	}	

	// Test program
	
	public static void main(String[] args) 
	{
		AtomFeedReader reader = new AtomFeedReader(args[0]);
		Feed feed = reader.read();
		
		System.out.println("URL "+args[0]);
		System.out.println(feed);
		
		for (FeedMessage message: feed.getMessages()) {
			System.out.println(message);
		}

	}	
}
