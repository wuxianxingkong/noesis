package sandbox.mdsd.ui.swing;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.net.URL;

import ikor.collection.Dictionary;
import ikor.collection.DynamicDictionary;

import sandbox.mdsd.log.Log;
import sandbox.mdsd.ui.Component;
import sandbox.mdsd.ui.Menu;
import sandbox.mdsd.ui.Image;
import sandbox.mdsd.ui.Label;
import sandbox.mdsd.ui.Option;
import sandbox.mdsd.ui.Selector;
import sandbox.mdsd.ui.Separator;
import sandbox.mdsd.ui.Viewer;
import sandbox.mdsd.ui.Editor;
import sandbox.mdsd.ui.UIModel;
import sandbox.mdsd.ui.UI;
import sandbox.mdsd.ui.UIFactory;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import javax.swing.GroupLayout;

public class SwingUI extends JFrame implements UI 
{
	UIModel     context;
	
	Dictionary<Class,UIFactory>  builders;
	Dictionary<String,UIFactory> options;
	
	/**
	 * UI Constructor.
	 * 
	 * @param context UI model
	 */
	
	public SwingUI (UIModel context)
	{
		this.context = context;
		
		builders = new DynamicDictionary<Class,UIFactory>();
		
		builders.set ( Menu.class, new SwingMenuFactory() );
		builders.set ( Image.class, new SwingImageFactory() );
		builders.set ( Label.class, new SwingLabelFactory() );
		builders.set ( Option.class, new SwingOptionFactory() );
		builders.set ( Selector.class, new SwingSelectorFactory() );
		builders.set ( Separator.class, new SwingSeparatorFactory() );
		builders.set ( Viewer.class, new SwingViewerFactory() );
		builders.set ( Editor.class, new SwingEditorFactory() );
		
		options = new DynamicDictionary<String,UIFactory>();
		
		options.set ( "$icon", new SwingIconFactory() );
		options.set ( "$exit", new SwingExitFactory() );
		options.set ( "$background", new SwingBackgroundFactory() );
		
		buildUI();
	}
	
	private void buildUI ()
	{
		this.setTitle (context.getId());
				
		initLayout();

		// Components
		
		for (Component component: context.getComponents())
			build(component);
		
		
		initDisplay();
	}
	
	
	/**
	 * Build UI for a given UI component.
	 * 
	 * @param component UI component
	 */
	
	private void build (Component component)
	{
		UIFactory factory = null;
		
		if (component.getId().startsWith("$")) {
			factory = options.get(component.getId());
		} else {
			factory = getFactory(component.getClass());	
		}
		
		if (factory!=null)
			factory.build(this, component);
		else
			Log.warning("Unable to create UI widget for "+component);
	}
	
	
	/**
	 * Get UI factory for a given component class. 
	 * If no specific factory for this specific class is defined, 
	 * then the class hierarchy is explored to find a suitable factory.
	 * 
	 * @param type UI component class
	 * @return UI component factory
	 */
	
	public UIFactory getFactory (Class type)
	{
		UIFactory factory = builders.get(type);
		
		if (factory!=null)
			return factory;
		else if (type.getSuperclass()!=null)
			return getFactory(type.getSuperclass());
		else
			return null;	
	}
	
	/**
	 * Layout manager
	 */
	
	GroupLayout layout;
	GroupLayout.Group vertical;
	GroupLayout.Group horizontal;
	
	public GroupLayout initLayout ()
	{
		layout = new GroupLayout(getContentPane());
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		getContentPane().setLayout(layout);
		
		vertical = layout.createSequentialGroup();
		horizontal = layout.createParallelGroup(GroupLayout.Alignment.CENTER);

		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		
		return layout;
	}

	public GroupLayout getLayout ()
	{
		return layout;
	}
	
	public void addComponent (java.awt.Component component)
	{
		horizontal.addComponent(component);
		vertical.addComponent(component);
	}

	/**
	 * Load icon.
	 * 
	 * @param location Icon URL
	 * @return java.awt.ImageIcon
	 */
	
	public ImageIcon loadIcon (String location)
	{
		java.awt.Image image = loadImage(location);
		
		try {
			URL url = ClassLoader.getSystemClassLoader().getResource(location);
			image = new ImageIcon(url).getImage().getScaledInstance(32,32,java.awt.Image.SCALE_SMOOTH);
		} catch (Exception error) {
			Log.error("Loading icon "+location);
		}
		
		if (image!=null)
			return new ImageIcon(image);
		else 
			return null;
	}
	
	
	/**
	 * Load image
	 * 
	 * @param location Image URL
	 * @return java.awt.Image
	 */
	
	public java.awt.Image loadImage (String location)
	{
		java.awt.Image image = null;
		
		try {
			URL url = ClassLoader.getSystemClassLoader().getResource(location);
			image = new ImageIcon(url).getImage();
		} catch (Exception error) {
			Log.error("Loading image "+location);
		}
		
		return image;
	}
	
	
	/**
	 * Display initialization.
	 */
		
	private void initDisplay ()
	{		
		// Device properties
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
				
		// Pack UI elements...  
		  
		this.pack();  // vs. this.setSize(width, height);

		// Center on screen 
		   
		Dimension size = this.getPreferredSize();  
		this.setLocation((int) ( width/2 - size.getWidth()/2 ), (int) ( height/2 - size.getHeight()/2) );   		
	}
	
	
	/**
	 * UI execution
	 */

	@Override
	public void run() 
	{
		this.setVisible(true);		
	}

	@Override
	public void exit() 
	{
		this.setVisible(false);
		
		if (context==context.getApplication().getStartup())
			System.exit(0);
	}

}
