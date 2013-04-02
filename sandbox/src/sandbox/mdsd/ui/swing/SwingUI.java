package sandbox.mdsd.ui.swing;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import sandbox.mdsd.ui.Action;
import sandbox.mdsd.ui.Component;
import sandbox.mdsd.ui.Log;
import sandbox.mdsd.ui.Menu;
import sandbox.mdsd.ui.Option;
import sandbox.mdsd.ui.UIModel;
import sandbox.mdsd.ui.UI;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
		}
		
	}
	
	private void initMenu (Menu menu)
	{
		JMenuBar menubar = new JMenuBar();
		
		for (Option item: menu.getItems()) {
			if (item instanceof Menu)
				menubar.add( createMenu((Menu)item) );
			else
				Log.warning( "Attempt to add a non-menu item to menu bar " + item );
		}
		/*
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem eMenuItem = new JMenuItem("Exit", icon);
		eMenuItem.setMnemonic(KeyEvent.VK_C);
		eMenuItem.setToolTipText("Exit application");
		eMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}

		});

		file.add(eMenuItem);
		menubar.add(file);
*/

		setJMenuBar(menubar);		
	}
	
	private JMenu createMenu (Menu menu)
	{
		JMenu jmenu = new JMenu( menu.getLabel().getText() );
		// jmenu.setMnemonic(KeyEvent.VK_C);
		// jmenu.setToolTipText("Exit application");
		// menuItem.setIcon(defaultIcon)
			
		for (Option item: menu.getItems()) {
			if (item instanceof Menu)
				jmenu.add( createMenu((Menu)item) );
			else
				jmenu.add( createMenuItem(item) );
		}

		return jmenu;
	}
	
	private JMenuItem createMenuItem (Option option)
	{
		JMenuItem menuItem = new JMenuItem( option.getLabel().getText() );
		// menuItem.setMnemonic(KeyEvent.VK_C);
		// menuItem.setToolTipText("Exit application");
		// menuItem.setIcon(defaultIcon)
		menuItem.addActionListener(new ActionHandler(option.getAction()));
		
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
			action.run();
		}
	}
	
}
