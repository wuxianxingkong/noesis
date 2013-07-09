package ikor.model.graphics.io;

import ikor.collection.Dictionary;
import ikor.collection.DynamicDictionary;

import ikor.model.graphics.*;
import ikor.model.graphics.styles.*;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

// TODO Shape rotations
// TODO Text / fonts

public class SVGDrawingWriter extends DrawingWriter 
{
	Dictionary<Class,SVGStyleWriter> styleWriters;
	Dictionary<Class,SVGElementWriter> elementWriters;
	
	public SVGDrawingWriter (Drawing drawing) 
	{
		super(drawing);
		
		styleWriters = new DynamicDictionary<Class,SVGStyleWriter>();
		
		styleWriters.set(FontStyle.class, null);
		styleWriters.set(LinearGradient.class, new SVGLinearGradientWriter() );
		styleWriters.set(RadialGradient.class, new SVGRadialGradientWriter() );
		styleWriters.set(Style.class, null);

		elementWriters = new DynamicDictionary<Class,SVGElementWriter>();
		
		elementWriters.set(Line.class, new SVGLineWriter() );
		elementWriters.set(Rectangle.class, new SVGRectangleWriter() );
		elementWriters.set(Polygon.class, new SVGPolygonWriter() );
		elementWriters.set(Circle.class, new SVGCircleWriter() );
		elementWriters.set(Ellipse.class, new SVGEllipseWriter() );
		elementWriters.set(Arc.class, new SVGArcWriter() );
		elementWriters.set(Bitmap.class, new SVGBitmapWriter() );
		elementWriters.set(Text.class, null);
	}

	@Override
	public void write(OutputStream writer) 
		throws IOException
	{
		BufferedWriter  output = new BufferedWriter( new OutputStreamWriter(writer) );
		XMLStreamWriter xmlWriter;
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		
		try {
			xmlWriter = factory.createXMLStreamWriter(output);
			
			xmlWriter.writeStartDocument("ISO-8859-1","1.0");
			
			xmlWriter.writeStartElement("svg");
			xmlWriter.writeAttribute("xmlns", "http://www.w3.org/2000/svg");
			xmlWriter.writeAttribute("version", "1.1");
			xmlWriter.writeAttribute("xmlns:xlink", "http://www.w3.org/1999/xlink");
			
			writeStyles(xmlWriter);
			
			writeElements(xmlWriter);
						
			xmlWriter.writeEndElement();
			
			xmlWriter.writeEndDocument();

			xmlWriter.flush();
			xmlWriter.close();			
			
		} catch (XMLStreamException error) {
			throw new IOException(error);
		}
		
	}
	
	private void writeStyles (XMLStreamWriter xmlWriter)
		throws XMLStreamException
	{		
		xmlWriter.writeStartElement("defs");
		
		for (Style style: drawing.getStyles() )
			writeStyle(style,xmlWriter);
		
		xmlWriter.writeEndElement();
	}
	
	private void writeStyle (Style style, XMLStreamWriter xmlWriter)
		throws XMLStreamException
	{
		SVGStyleWriter writer = getStyleWriter(style.getClass());
		
		if (writer!=null)
			writer.write(style, xmlWriter);
	}

	public SVGStyleWriter getStyleWriter (Class type)
	{
		SVGStyleWriter writer = styleWriters.get(type);
		
		if (writer!=null)
			return writer;
		else if (type.getSuperclass()!=null)
			return getStyleWriter(type.getSuperclass());
		else
			return null;	
	}
	
	
	private void writeElements (XMLStreamWriter xmlWriter)
		throws XMLStreamException
	{
		for (DrawingElement element: drawing.getElements() )
			writeElement(element,xmlWriter);
	}

	private void writeElement (DrawingElement element, XMLStreamWriter xmlWriter)
		throws XMLStreamException
	{
		SVGElementWriter writer = getElementWriter(element.getClass());
			
		if (writer!=null)
			writer.write(element, xmlWriter);
	}

	public SVGElementWriter getElementWriter (Class type)
	{
		SVGElementWriter writer = elementWriters.get(type);
		
		if (writer!=null)
			return writer;
		else if (type.getSuperclass()!=null)
			return getElementWriter(type.getSuperclass());
		else
			return null;	
	}

	
	public String rgbColor (Color color)
	{
		return String.format("#%06x",color.getRGB() & 0x00ffffff);
	}
	
	public String opacity (Color color)
	{
		return String.format(Locale.ENGLISH, "%.3f", (color.getRGB() >>> 24) / 255.0);
	}
	
	// Styles
	
	private abstract class SVGStyleWriter 
	{
		public abstract void write (Style style, XMLStreamWriter writer) throws XMLStreamException;		

	}

	private class SVGRadialGradientWriter extends SVGStyleWriter
	{
		@Override
		public void write(Style style, XMLStreamWriter writer)
			throws XMLStreamException 
		{
			RadialGradient gradient = (RadialGradient) style;
			
			writer.writeStartElement("radialGradient");
			writer.writeAttribute("id", "s"+gradient.hashCode() );
			writer.writeAttribute("cx", ""+Math.round(100*gradient.getCenterX())+"%" );
			writer.writeAttribute("cy", ""+Math.round(100*gradient.getCenterY())+"%" );
			writer.writeAttribute("r", ""+Math.round(100*gradient.getRadius())+"%" );
			writer.writeAttribute("fx", ""+Math.round(100*gradient.getCenterX())+"%" );
			writer.writeAttribute("fy", ""+Math.round(100*gradient.getCenterY())+"%" );
			
			for (GradientKeyframe keyframe: gradient.getKeyframes()) {
				writer.writeStartElement("stop");
				writer.writeAttribute("offset", ""+Math.round(100*keyframe.getValue())+"%");
				writer.writeAttribute("style", "stop-color:"+rgbColor(keyframe.getColor())+";"
						                     + "stop-opacity:"+opacity(keyframe.getColor()));
				writer.writeEndElement();							
			}
			
			writer.writeEndElement();			
		}
	}
	
	private class SVGLinearGradientWriter extends SVGStyleWriter
	{
		@Override
		public void write(Style style, XMLStreamWriter writer)
			throws XMLStreamException 
		{
			LinearGradient gradient = (LinearGradient) style;
			
			writer.writeStartElement("linearGradient");
			writer.writeAttribute("id", "s"+gradient.hashCode() );
			writer.writeAttribute("x1", ""+Math.round(100*gradient.getStartX())+"%" );
			writer.writeAttribute("y1", ""+Math.round(100*gradient.getStartY())+"%" );
			writer.writeAttribute("x2", ""+Math.round(100*gradient.getEndX())+"%" );
			writer.writeAttribute("y2", ""+Math.round(100*gradient.getEndY())+"%" );
			
			for (GradientKeyframe keyframe: gradient.getKeyframes()) {
				writer.writeStartElement("stop");
				writer.writeAttribute("offset", ""+Math.round(100*keyframe.getValue())+"%");
				writer.writeAttribute("style", "stop-color:"+rgbColor(keyframe.getColor())+";"
						                     + "stop-opacity:"+opacity(keyframe.getColor()));
				writer.writeEndElement();							
			}
			
			writer.writeEndElement();			
		}
	}
		
	// Elements
	
	private abstract class SVGElementWriter
	{
		public abstract void write (DrawingElement element, XMLStreamWriter writer) throws XMLStreamException;

		public String styleReference (Style style)
		{
			if ((style!=null) && (style instanceof Gradient))
				return "url(#s"+style.hashCode()+")";
			else
				return null;
		}
		
		public String fillString (Style style)
		{
			return "fill:"+rgbColor(style.getColor())+";"
				 + "fill-opacity:"+opacity(style.getColor())+";";
		}

		public String borderString (Style style)
		{
			return "stroke:"+rgbColor(style.getColor())+";"
				 + "stroke-opacity:"+opacity(style.getColor())+";"
				 + "stroke-width:"+style.getWidth()+";";
		}
		
		public String styleString (Style fill, Style border)
		{
			String result = "";
			
			if ( (fill!=null) && (styleReference(fill)==null) )
				result += fillString(fill);
			
			if ( (border!=null) && (styleReference(border)==null) )
			   result += borderString(border);
			   
			return result;
		}
	}
	
	private class SVGLineWriter extends SVGElementWriter
	{
		@Override
		public void write(DrawingElement element, XMLStreamWriter writer)
			throws XMLStreamException 
		{
			Line line = (Line) element;
			
			writer.writeStartElement("line");
			
			if (line.getId()!=null)
				writer.writeAttribute("id", line.getId());
			
			writer.writeAttribute("x1", ""+line.getStartX());
			writer.writeAttribute("y1", ""+line.getStartY());
			writer.writeAttribute("x2", ""+line.getEndX());
			writer.writeAttribute("y2", ""+line.getEndY());
			writer.writeAttribute("style", borderString(line.getStyle()) );
			
			writer.writeEndElement();			
		}
	}

	private class SVGBitmapWriter extends SVGElementWriter
	{
		@Override
		public void write(DrawingElement element, XMLStreamWriter writer)
			throws XMLStreamException 
		{
			Bitmap image = (Bitmap) element;
			
			writer.writeStartElement("image");
			
			if (image.getId()!=null)
				writer.writeAttribute("id", image.getId());
			
			writer.writeAttribute("x", ""+image.getX() );
			writer.writeAttribute("y", ""+image.getY() );
			writer.writeAttribute("height", ""+image.getHeight() );
			writer.writeAttribute("width", ""+image.getWidth() );
			writer.writeAttribute("href", image.getUrl() );
			
			writer.writeEndElement();			
		}
	}

	private class SVGRectangleWriter extends SVGElementWriter
	{
		@Override
		public void write(DrawingElement element, XMLStreamWriter writer)
			throws XMLStreamException 
		{
			Rectangle rectangle = (Rectangle) element;
			
			writer.writeStartElement("rect");
			
			if (rectangle.getId()!=null)
				writer.writeAttribute("id", rectangle.getId());
			
			writer.writeAttribute("x", ""+rectangle.getX() );
			writer.writeAttribute("y", ""+rectangle.getY() );
			writer.writeAttribute("width", ""+rectangle.getWidth() );
			writer.writeAttribute("height", ""+rectangle.getHeight() );
			
			if (styleReference(rectangle.getStyle())!=null)
				writer.writeAttribute("fill", styleReference(rectangle.getStyle()) );

			if (styleReference(rectangle.getBorder())!=null)
				writer.writeAttribute("stroke", styleReference(rectangle.getBorder()) );

			writer.writeAttribute("style", styleString(rectangle.getStyle(),rectangle.getBorder()) );

			writer.writeEndElement();			
		}
	}

	private class SVGPolygonWriter extends SVGElementWriter
	{
		@Override
		public void write(DrawingElement element, XMLStreamWriter writer)
			throws XMLStreamException 
		{
			Polygon polygon = (Polygon) element;
			
			writer.writeStartElement("polygon");
			
			if (polygon.getId()!=null)
				writer.writeAttribute("id", polygon.getId());
			
			// points
			
			String coordinates = "";
			
			for (int i=0; i<polygon.getSides(); i++)
				coordinates += polygon.getX(i)+","+polygon.getY(i) + " ";
			
			writer.writeAttribute("points", coordinates );
			
			// style
			
			if (styleReference(polygon.getStyle())!=null)
				writer.writeAttribute("fill", styleReference(polygon.getStyle()) );

			if (styleReference(polygon.getBorder())!=null)
				writer.writeAttribute("stroke", styleReference(polygon.getBorder()) );

			writer.writeAttribute("style", styleString(polygon.getStyle(),polygon.getBorder()) );

			writer.writeEndElement();			
		}
	}
	
	private class SVGCircleWriter extends SVGElementWriter
	{
		@Override
		public void write(DrawingElement element, XMLStreamWriter writer)
			throws XMLStreamException 
		{
			Circle circle = (Circle) element;
			
			writer.writeStartElement("circle");
			
			if (circle.getId()!=null)
				writer.writeAttribute("id", circle.getId());
			
			writer.writeAttribute("cx", ""+circle.getCenterX() );
			writer.writeAttribute("cy", ""+circle.getCenterY() );
			writer.writeAttribute("r", ""+circle.getRadius() );
			
			if (styleReference(circle.getStyle())!=null)
				writer.writeAttribute("fill", styleReference(circle.getStyle()) );

			if (styleReference(circle.getBorder())!=null)
				writer.writeAttribute("stroke", styleReference(circle.getBorder()) );

			writer.writeAttribute("style", styleString(circle.getStyle(),circle.getBorder()) );

			writer.writeEndElement();			
		}
	}

	private class SVGEllipseWriter extends SVGElementWriter
	{
		@Override
		public void write(DrawingElement element, XMLStreamWriter writer)
			throws XMLStreamException 
		{
			Ellipse ellipse = (Ellipse) element;
			
			writer.writeStartElement("ellipse");
			
			if (ellipse.getId()!=null)
				writer.writeAttribute("id", ellipse.getId());
			
			writer.writeAttribute("cx", ""+ellipse.getCenterX() );
			writer.writeAttribute("cy", ""+ellipse.getCenterY() );
			writer.writeAttribute("rx", ""+ellipse.getRadiusX() );
			writer.writeAttribute("ry", ""+ellipse.getRadiusY() );
			
			if (styleReference(ellipse.getStyle())!=null)
				writer.writeAttribute("fill", styleReference(ellipse.getStyle()) );

			if (styleReference(ellipse.getBorder())!=null)
				writer.writeAttribute("stroke", styleReference(ellipse.getBorder()) );

			writer.writeAttribute("style", styleString(ellipse.getStyle(),ellipse.getBorder()) );

			writer.writeEndElement();			
		}
	}

	private class SVGArcWriter extends SVGElementWriter
	{
		@Override
		public void write(DrawingElement element, XMLStreamWriter writer)
			throws XMLStreamException 
		{
			Arc arc = (Arc) element;
			int sx = (int) ( arc.getCenterX() + Math.cos(arc.getStartAngle())*arc.getRadiusX() );
			int sy = (int) ( arc.getCenterY() - Math.sin(arc.getStartAngle())*arc.getRadiusY() );
			int dx = (int) ( arc.getCenterX() + Math.cos(arc.getEndAngle())*arc.getRadiusX() );
			int dy = (int) ( arc.getCenterY() - Math.sin(arc.getEndAngle())*arc.getRadiusY() );
			
			writer.writeStartElement("path");
			
			if (arc.getId()!=null)
				writer.writeAttribute("id", arc.getId());
			
			// ref. http://www.w3.org/TR/SVG/paths.html#PathDataEllipticalArcCommands
			
			writer.writeAttribute("d", "M " + arc.getCenterX() + " " + arc.getCenterY() + " "
					                 + "L " + sx + " " + sy + " "
					                 + "A " + arc.getRadiusX() + " " + arc.getRadiusY() + " "
					                        + "0 "  // x-axis rotation
					                        + (arc.getExtent()>=Math.PI?"1 ":"0 ")  // large arc flag 
					                        + (arc.getStartAngle()<arc.getEndAngle()?"0 ":"1 ")  // sweep flag (positive angle direction)
					                        + dx + " " + dy);
					          
			
			if (styleReference(arc.getStyle())!=null)
				writer.writeAttribute("fill", styleReference(arc.getStyle()) );

			if (styleReference(arc.getBorder())!=null)
				writer.writeAttribute("stroke", styleReference(arc.getBorder()) );

			writer.writeAttribute("style", styleString(arc.getStyle(),arc.getBorder()) );

			writer.writeEndElement();			
		}
	}
	
}
