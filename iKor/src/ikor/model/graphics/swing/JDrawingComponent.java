package ikor.model.graphics.swing;

import ikor.collection.Dictionary;
import ikor.collection.DynamicDictionary;
import ikor.model.graphics.Arc;
import ikor.model.graphics.Bitmap;
import ikor.model.graphics.Circle;
import ikor.model.graphics.Drawing;
import ikor.model.graphics.DrawingElement;
import ikor.model.graphics.DrawingSelectionListener;
import ikor.model.graphics.DrawingTooltipProvider;
import ikor.model.graphics.DrawingUpdateListener;
import ikor.model.graphics.Ellipse;
import ikor.model.graphics.Line;
import ikor.model.graphics.Polygon;
import ikor.model.graphics.Rectangle;
import ikor.model.graphics.Style;
import ikor.model.graphics.Text;
import ikor.model.graphics.styles.FontStyle;
import ikor.model.graphics.styles.LinearGradient;
import ikor.model.graphics.styles.RadialGradient;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;


public class JDrawingComponent extends JComponent 
{
	private Drawing drawing;
	
	// Event handling
	
	private DrawingTooltipProvider tooltipProvider;
	private DrawingSelectionListener selectionListener;
	private DrawingUpdateListener actionListener;
	private DrawingUpdateListener draggingListener;
	
	// Cache for images
	
	private Dictionary<String,Image> images;
	
	/**
	 * Constructor
	 * 
	 * @param drawing Drawing element
	 */
	public JDrawingComponent (Drawing drawing)
	{
		this.drawing = drawing;
		this.images = new DynamicDictionary<String,Image>();
		
		this.setPreferredSize( new Dimension(drawing.getWidth(), drawing.getHeight()));
		
		JDrawingMouseListener mouseListener = new JDrawingMouseListener(this);
		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseListener);
	}
	
	/**
	 * Get current drawing.
	 * 
	 * @return the drawing
	 */
	public Drawing getDrawing() 
	{
		return drawing;
	}

	/**
	 * Set drawing.
	 * 
	 * @param drawing the drawing to set
	 */
	public void setDrawing(Drawing drawing) 
	{
		this.drawing = drawing;
	}

	// Save

	public void save (String fileName, String format)
	{
        java.awt.Rectangle r = getBounds();

        try {
            BufferedImage img = new BufferedImage(r.width, r.height, BufferedImage.TYPE_INT_RGB);
            Graphics g = img.getGraphics();
            paint(g);
            ImageIO.write(img,format, new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }		
	}

	public void save (OutputStream writer, String format)
	{
        java.awt.Rectangle r = getBounds();

        try {
            BufferedImage img = new BufferedImage(r.width, r.height, BufferedImage.TYPE_INT_RGB);
            Graphics g = img.getGraphics();
            paint(g);
            ImageIO.write(img,format,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }		
	}
	
	// Display
	
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
		} else if (element instanceof Arc) {
			drawArc(g, (Arc)element);
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

	
	public void drawShape (Graphics2D g, DrawingElement element, Shape shape)
	{
		if (element.getStyle()!=null) {
			setStyle(g, element, element.getStyle());
			g.fill(shape);
		}

		if (element.getBorder()!=null) {
			setStyle(g, element, element.getBorder());
			g.draw(shape);
		}
	}
	
	public void drawRectangle (Graphics2D g, Rectangle rectangle)
	{
		AffineTransform at;
		Shape shape = new java.awt.Rectangle(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());

		if (rectangle.getRotation()!=0) {
			at = new AffineTransform();
			at.rotate(rectangle.getRotation(), rectangle.getX(), rectangle.getY());
			shape = at.createTransformedShape(shape);
		}
		
		drawShape(g,rectangle,shape);
	}

	public void drawPolygon (Graphics2D g, Polygon polygon)
	{
		AffineTransform at;
		Shape shape = new java.awt.Polygon(polygon.getXCoords(), polygon.getYCoords(), polygon.getSides());
				
		if (polygon.getRotation()!=0) {
			at = new AffineTransform();
			at.rotate(polygon.getRotation(), polygon.getX(), polygon.getY());
			shape = at.createTransformedShape(shape);
		}
		
		drawShape(g,polygon,shape);
	}
	
	public void drawCircle (Graphics2D g, Circle circle)
	{
		int x = circle.getX();
		int y = circle.getY();
		int radius = circle.getRadius();
	
		if (circle.getStyle()!=null) {
			setStyle(g, circle, circle.getStyle());
			g.fillOval(x, y, 2*radius, 2*radius);
		}
		
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
	
		AffineTransform at;
		Shape shape = new java.awt.geom.Ellipse2D.Double(x,y,2*radiusX,2*radiusY);
				
		if (ellipse.getRotation()!=0) {
			at = new AffineTransform();
			at.rotate(ellipse.getRotation(), ellipse.getCenterX(), ellipse.getCenterY());
			shape = at.createTransformedShape(shape);
		}
		
		drawShape(g,ellipse,shape);
	}

	public void drawArc (Graphics2D g, Arc arc)
	{
		int x = arc.getX();
		int y = arc.getY();
		int radiusX = arc.getRadiusX();
		int radiusY = arc.getRadiusY();
		double startAngle = 180*arc.getStartAngle()/Math.PI;
		double extent = 180*arc.getExtent()/Math.PI;
	
		AffineTransform at;
		Shape shape = new java.awt.geom.Arc2D.Double(x,y,2*radiusX,2*radiusY, startAngle, extent, Arc2D.PIE);
				
		if (arc.getRotation()!=0) {
			at = new AffineTransform();
			at.rotate(arc.getRotation(), arc.getCenterX(), arc.getCenterY());
			shape = at.createTransformedShape(shape);
		}

		drawShape(g,arc,shape);
	}	

	public void drawBitmap (Graphics2D g, Bitmap bitmap)
	{
		Image image = images.get( bitmap.getUrl() );
		
		if (image==null) 
			try {
				image = ImageIO.read( new URL(bitmap.getUrl()) );
			} catch (Exception error) {
			}

		if (image!=null) {
			
			if (bitmap.getRotation()!=0) {
				AffineTransform at = new AffineTransform();
				at.translate(bitmap.getX(), bitmap.getY());
				at.rotate(bitmap.getRotation());
				g.drawImage(image, at, this);
			} else {
				g.drawImage (image, bitmap.getX(), bitmap.getY(), bitmap.getX()+bitmap.getWidth(), bitmap.getY()+bitmap.getHeight(), null );
			}
		}
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
		    fontAT.rotate(style.getAngle());
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

	
	// Event handling
	// --------------
	
	public DrawingTooltipProvider getTooltipProvider() 
	{
		return tooltipProvider;
	}

	public void setTooltipProvider(DrawingTooltipProvider tooltipProvider) 
	{
		this.tooltipProvider = tooltipProvider;
	}
	
	
	public DrawingSelectionListener getSelectionListener() 
	{
		return selectionListener;
	}

	public void setSelectionListener(DrawingSelectionListener selectionListener) 
	{
		this.selectionListener = selectionListener;
	}


	public DrawingUpdateListener getActionListener() 
	{
		return actionListener;
	}

	public void setActionListener(DrawingUpdateListener actionListener) 
	{
		this.actionListener = actionListener;
	}


	public DrawingUpdateListener getDraggingListener() 
	{
		return draggingListener;
	}

	public void setDraggingListener(DrawingUpdateListener draggingListener) 
	{
		this.draggingListener = draggingListener;
	}	
}
