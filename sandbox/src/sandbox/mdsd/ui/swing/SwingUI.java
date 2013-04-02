package sandbox.mdsd.ui.swing;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import sandbox.mdsd.ui.Action;
import sandbox.mdsd.ui.Component;
import sandbox.mdsd.ui.Log;
import sandbox.mdsd.ui.Menu;
import sandbox.mdsd.ui.Option;
import sandbox.mdsd.ui.Image;
import sandbox.mdsd.ui.Separator;
import sandbox.mdsd.ui.UIModel;
import sandbox.mdsd.ui.UI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class SwingUI extends JFrame implements UI 
{
	UIModel context;
	
	public SwingUI (UIModel context)
	{
		this.context = context;
		
		initUI();
	}
	
	private void initUI ()
	{
		this.setTitle (context.getId());
		
		for (Component component: context.getItems())
			init(component);
		
		initDisplay();
	}
	
	private void init (Component component)
	{
		if (component instanceof Menu) {
			initMenu( (Menu) component);
		} else if (component instanceof Option) {
			initOption ( (Option) component );
		} else if (component instanceof Image) {
			
			if (component.getId()=="$icon") {
				this.setIconImage( loadIcon(((Image)component).getUrl()).getImage() );
			}
		}
		
	}
	
	private void initMenu (Menu menu)
	{
		JMenuBar menubar = new JMenuBar();
		
		for (Option item: menu.getItems()) {
			if (item instanceof Menu)
				menubar.add( createMenu((Menu)item) );
			else 
				menubar.add ( createMenuItem(item) ); 
		}

		setJMenuBar(menubar);		
	}
	
	private ImageIcon loadIcon (String location)
	{
		java.awt.Image image = null;
		
		try {
			URL url = ClassLoader.getSystemClassLoader().getResource(location);
			image = new ImageIcon(url).getImage().getScaledInstance(32,32,java.awt.Image.SCALE_SMOOTH);
		} catch (Exception error) {
			Log.error("Loading icon "+location);
		}
		
		return new ImageIcon(image);
	}
	
	private JMenu createMenu (Menu menu)
	{
		JMenu jmenu = new JMenu( menu.getLabel().getText() );

		jmenu.setToolTipText( menu.getLabel().getDescription() );
	    jmenu.setEnabled( menu.isEnabled() );

		if (menu.getIcon()!=null)
			jmenu.setIcon( loadIcon(menu.getIcon()) );

		if (menu.getShortcut()!=0)
			jmenu.setMnemonic( menu.getShortcut() );
			
		for (Option item: menu.getItems()) {
			if (item instanceof Menu)
				jmenu.add( createMenu((Menu)item) );
			else if (item instanceof Separator)
				jmenu.addSeparator();
			else
				jmenu.add( createMenuItem(item) );
		}

		return jmenu;
	}
	
	private JMenuItem createMenuItem (Option option)
	{
		JMenuItem menuItem = new JMenuItem( option.getLabel().getText() );
		
		menuItem.setToolTipText( option.getLabel().getDescription() );
	    menuItem.setEnabled( option.isEnabled() );
		menuItem.addActionListener(new ActionHandler(option.getAction()));
		
		if (option.getIcon()!=null)
			menuItem.setIcon( loadIcon(option.getIcon()) );

		if (option.getShortcut()!=0)
			menuItem.setAccelerator( KeyStroke.getKeyStroke( option.getShortcut(), 0 ) );
		
		return menuItem;
	}
	
	
	
	private void initOption (Option option)
	{
		switch (option.getId()) {

		case "$exit":
			addWindowListener(new WindowHandler(option.getAction()));

		}
	}
	
	private void initDisplay ()
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
				
		// Pack UI elements...  
		  
		this.pack();  // vs. this.setSize(width, height);

		// Center on screen 
		   
		Dimension size = this.getPreferredSize();  
		this.setLocation((int) ( width/2 - size.getWidth()/2 ), (int) ( height/2 - size.getHeight()/2) );   		
	}
	

	@Override
	public void run() 
	{
		this.setVisible(true);		
	}

	
	// Event handling
	
	class WindowHandler extends WindowAdapter
	{
		Action action;
		
		public WindowHandler (Action action)
		{
			this.action = action;
		}

		public void windowClosing (WindowEvent e)
		{ 
			action.run();
		}
	}

	class ActionHandler implements ActionListener
	{
		Action action;
		
		public ActionHandler (Action action)
		{
			this.action = action;
		}

		@Override
		public void actionPerformed (ActionEvent e)
		{ 
			if (action!=null)
				action.run();
			else
				Log.warning( "Attempt to execute null action - " + e );
		}
	}
	
}
