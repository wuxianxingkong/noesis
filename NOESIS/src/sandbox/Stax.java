import java.io.*;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import javax.xml.stream.XMLOutputFactory;


public class Stax
{
	// XML input

	public static void parseXMLDocument()
	{

		try {
			XMLInputFactory inputFactory=XMLInputFactory.newInstance();
			InputStream input=new FileInputStream(new File("INTC.xml"));
			XMLStreamReader  xmlStreamReader = inputFactory.createXMLStreamReader(input);

	
			while(xmlStreamReader.hasNext()){
				int event=xmlStreamReader.next();
				
				if(event==XMLStreamConstants.START_DOCUMENT){
					System.out.println("Event Type:START_DOCUMENT");
					System.out.println("Document Encoding:"+xmlStreamReader.getEncoding());
					System.out.println("XML Version:"+xmlStreamReader.getVersion());

				}
				
				if (event==XMLStreamConstants.START_ELEMENT){
					System.out.println("Event Type: START_ELEMENT");
					System.out.println("Element Prefix:"+xmlStreamReader.getPrefix());
					System.out.println("Element Local Name:"+xmlStreamReader.getLocalName());
					System.out.println("Namespace URI:"+xmlStreamReader.getNamespaceURI());
					
					for(int i=0; i<xmlStreamReader.getAttributeCount();i++){
						System.out.println("Attribute Prefix:"+xmlStreamReader.getAttributePrefix(i));
						System.out.println("Attribute Namespace:"+xmlStreamReader.getAttributeNamespace(i));
						System.out.println("Attribute Local Name:"+xmlStreamReader.getAttributeLocalName(i));
						System.out.println("Attribute Value:"+xmlStreamReader.getAttributeValue(i));
					}
				}	
				
				if(event==XMLStreamConstants.ATTRIBUTE){
					System.out.println("Event Type:ATTRIBUTE");
				}
				if(event==XMLStreamConstants.CHARACTERS){
					System.out.println("Event Type: CHARACTERS");
					System.out.println("Text:"+xmlStreamReader.getText());
				}
				if(event==XMLStreamConstants.COMMENT){
					System.out.println("Event Type:COMMENT");
					System.out.println("Comment Text:"+xmlStreamReader.getText());
				}
				if(event==XMLStreamConstants.END_DOCUMENT){
					System.out.println("Event Type:END_DOCUMENT");
				}
				if(event==XMLStreamConstants.END_ELEMENT){
					System.out.println("Event Type: END_ELEMENT");
				}
			
				if(event==XMLStreamConstants.NAMESPACE){
					System.out.println("Event Type:NAMESPACE");
				}
				if(event==XMLStreamConstants.PROCESSING_INSTRUCTION){
					System.out.println("Event Type: PROCESSING_INSTRUCTION");
					System.out.println("PI Target:"+xmlStreamReader.getPITarget());
					System.out.println("PI Data:"+xmlStreamReader.getPIData());
				}

				if(event==XMLStreamConstants.SPACE){
					System.out.println("Event Type: SPACE");
					System.out.println("Text:"+xmlStreamReader.getText());

				}
			}

		} catch (FactoryConfigurationError e){
			System.out.println("FactoryConfigurationError"+e.getMessage());
		} catch (XMLStreamException e) {
			System.out.println("XMLStreamException"+e.getMessage());
		} catch(IOException e){
			System.out.println("IOException"+e.getMessage());
		}

	}


	// XML output

	public static void generateXMLDocument()
	{
		try {
			XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
			FileOutputStream output=new FileOutputStream(new File("output.xml"));
			XMLStreamWriter xmlStreamWriter=outputFactory.createXMLStreamWriter(output,"iso-8859-1");

			xmlStreamWriter.writeStartDocument("iso-8859-1","1.0");
			xmlStreamWriter.writeComment("A OReilly Journal Catalog");
			xmlStreamWriter.writeProcessingInstruction("catalog","journal='OReilly'");
			xmlStreamWriter.writeStartElement("journal","catalog","http://OnJava.com/Journal");
			xmlStreamWriter.writeNamespace("journal","http://OnJava.com/Journal");
			xmlStreamWriter.writeNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance");
			xmlStreamWriter.writeAttribute("xsi:noNamespaceSchemaLocation","file://c:/Schemas/catalog.xsd");
			xmlStreamWriter.writeAttribute("publisher","OReilly");

			xmlStreamWriter.writeStartElement("journal","journal","http://OnJava.com/Journal");
			xmlStreamWriter.writeAttribute("date","January 2004");
			xmlStreamWriter.writeAttribute("title","ONJava");
			xmlStreamWriter.writeStartElement("article");
			xmlStreamWriter.writeStartElement("title");
			xmlStreamWriter.writeCharacters("Data Binding with XMLBeans");
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeStartElement("author");
			xmlStreamWriter.writeCharacters("Daniel Steinberg");
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeEndElement();

			xmlStreamWriter.writeStartElement("journal","journal","http://OnJava.com/Journal");
			xmlStreamWriter.writeAttribute("date","July 2004");
			xmlStreamWriter.writeAttribute("title","java.net");
			xmlStreamWriter.writeStartElement("article");
			xmlStreamWriter.writeStartElement("title");
			xmlStreamWriter.writeCharacters("Hibernate: A Developer's Notebook");
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeStartElement("author");
			xmlStreamWriter.writeCharacters("James Elliott");
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeEndElement();
			xmlStreamWriter.writeEndElement();

			xmlStreamWriter.flush();
			xmlStreamWriter.close();

		}catch(FactoryConfigurationError e){
			System.out.println("FactoryConfigurationError"+e.getMessage());
		}catch(XMLStreamException e){
			System.out.println("XMLStreamException"+e.getMessage());
		}catch(IOException e){
			System.out.println("IOException"+e.getMessage());
		}
	}



	public static void main(String[] argv)
	{
		//parseXMLDocument();
		generateXMLDocument();
	}
}