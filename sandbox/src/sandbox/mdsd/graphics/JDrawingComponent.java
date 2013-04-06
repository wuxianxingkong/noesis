package sandbox.mdsd.graphics;

import ikor.collection.Dictionary;
import ikor.collection.DynamicDictionary;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import sandbox.mdsd.graphics.styles.FontStyle;
import sandbox.mdsd.graphics.styles.LinearGradient;
import sandbox.mdsd.graphics.styles.RadialGradient;

public class JDrawingComponent extends JComponent 
{
	Drawing drawing;
	
	Dictionary<String,Image> images;
	
	public JDrawingComponent (Drawing drawing)
	{
		this.drawing = drawing;
		this.images = new DynamicDictionary<String,Image>();
	}
	
	@Override
	public void paintComponent (Graphics g)
    { 
		super.paintComponent(g);
	
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (DrawingElement element: drawing.getElements())
			draw(g2, element);
    }	
	
	public void draw (Graphics2D g, DrawingElement element)
	{
		if (element instanceof Line) {
			drawLine(g, (Line)element);
		} else if (element instanceof Rectangle) {
			drawRectangle(g, (Rectangle)element);
		} else if (element instanceof Polygon) {
			drawPolygon(g, (Polygon)element);
		} else if (element instanceof Circle) {
			drawCircle(g, (Circle)element);
		} else if (element instanceof Ellipse) {
			drawEllipse(g, (Ellipse)element);
		} else if (element instanceof Bitmap) {
			drawBitmap(g, (Bitmap)element);
		} else if (element instanceof Text) {
			drawText(g, (Text)element);
		}
	}

	public void drawLine (Graphics2D g, Line line)
	{
		setStyle(g, line, line.getStyle());
		g.drawLine( line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY() ) ;
	}

	public void drawRectangle (Graphics2D g, Rectangle rectangle)
	{
		setStyle(g, rectangle, rectangle.getStyle());
		g.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
		
		if (rectangle.getBorder()!=null) {
			setStyle(g, rectangle, rectangle.getBorder());
			g.drawRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
		}
	}

	public void drawPolygon (Graphics2D g, Polygon polygon)
	{
		setStyle(g, polygon, polygon.getStyle());
		g.fillPolygon(polygon.getXCoords(), polygon.getYCoords(), polygon.getSides());
		
		if (polygon.getBorder()!=null) {
			setStyle(g, polygon, polygon.getBorder());
			g.drawPolygon(polygon.getXCoords(), polygon.getYCoords(), polygon.getSides());
		}
	}
	
	public void drawCircle (Graphics2D g, Circle circle)
	{
		int x = circle.getX();
		int y = circle.getY();
		int radius = circle.getRadius();
		
		setStyle(g, circle, circle.getStyle());
		g.fillOval(x, y, 2*radius, 2*radius);
		
		if (circle.getBorder()!=null) {
			setStyle(g, circle, circle.getBorder());
			g.drawOval(x, y, 2*radius, 2*radius);
		}
	}
	
	public void drawEllipse (Graphics2D g, Ellipse ellipse)
	{
		int x = ellipse.getX();
		int y = ellipse.getY();
		int radiusX = ellipse.getRadiusX();
		int radiusY = ellipse.getRadiusY();
		
		setStyle(g, ellipse, ellipse.getStyle());
		g.fillOval(x, y, 2*radiusX, 2*radiusY);
		
		if (ellipse.getBorder()!=null) {
			setStyle(g, ellipse, ellipse.getBorder());
			g.drawOval(x, y, 2*radiusX, 2*radiusY);
		}
	}
	
	public void drawBitmap (Graphics2D g, Bitmap bitmap)
	{
		Image image = images.get( bitmap.getUrl() );
		
		if (image==null) 
			try {
				image = ImageIO.read( new URL(bitmap.getUrl()) );
			} catch (Exception error) {
			}

		if (image!=null)
			g.drawImage (image, bitmap.getX(), bitmap.getY(), bitmap.getX()+bitmap.getWidth(), bitmap.getY()+bitmap.getHeight(), null );		
	}
	
	public void drawText (Graphics2D g, Text text)
	{
		setStyle(g, text, text.getStyle());
		g.drawString(text.getText(), text.getX(), text.getY());
	}
	
	public void setStyle (Graphics2D g, DrawingElement element, Style style)
	{
		if (style!=null) {
			
			setColor(g,style);
			
			if (style instanceof FontStyle) {
				setFont(g, (FontStyle)style);
			} else if (style instanceof LinearGradient) {
				setLinearGradient (g, element, (LinearGradient)style );
			} else if (style instanceof RadialGradient) {
				setRadialGradient (g, element, (RadialGradient)style );
			}
		}
	}
	
	public void setColor (Graphics2D g, Style style)
	{
		g.setColor(style.getColor());
		g.setStroke(new BasicStroke(style.getWidth()));
	}

	public void setFont (Graphics2D g, FontStyle style)
	{
		if (style.getAngle()==0) {
			g.setFont(style.getFont());
		} else {
		    AffineTransform fontAT = new AffineTransform();
		    fontAT.rotate(style.getAngle()*Math.PI/180);
		    g.setFont( style.getFont().deriveFont(fontAT) );
		}
	}
	
	public void setLinearGradient (Graphics2D g, DrawingElement element, LinearGradient style)
	{
		Point2D start = new Point2D.Float( element.getX() + element.getWidth()*style.getStartX(), 
				                           element.getY() + element.getHeight()*style.getStartY() );
		Point2D end = new Point2D.Float( element.getX() + element.getWidth()*style.getEndX(), 
                                         element.getY() + element.getHeight()*style.getEndY() );

		float[] distribution = new float[style.getKeyframes().size()];
		Color[] colors = new Color[style.getKeyframes().size()];

		for (int i=0; i<distribution.length; i++) {
			distribution[i] = style.getKeyframe(i).getValue();
			colors[i] = style.getKeyframe(i).getColor();
		}

		LinearGradientPaint gradient = new LinearGradientPaint(start, end, distribution, colors);

		g.setPaint(gradient);
	}
	
	public void setRadialGradient (Graphics2D g, DrawingElement element, RadialGradient style)
	{
		Point2D center = new Point2D.Float( element.getX() + element.getWidth()*style.getCenterX(), 
				                            element.getY() + element.getHeight()*style.getCenterY() );
		float   radius = Math.max(element.getWidth(),element.getHeight())*style.getRadius();

		float[] distribution = new float[style.getKeyframes().size()];
		Color[] colors = new Color[style.getKeyframes().size()];

		for (int i=0; i<distribution.length; i++) {
			distribution[i] = style.getKeyframe(i).getValue();
			colors[i] = style.getKeyframe(i).getColor();
		}

		RadialGradientPaint gradient = new RadialGradientPaint(center, radius, distribution, colors);

		g.setPaint(gradient);
	}
	
	
}
