package ikor.model.ui.swing;

import ikor.model.Observer;
import ikor.model.Subject;
import ikor.model.ui.Menu;
import ikor.model.ui.Option;
import ikor.model.ui.Separator;
import ikor.model.ui.UIFactory;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;


public class SwingMenuFactory implements UIFactory<SwingUI,Menu>
{
	@Override
	public void build(SwingUI ui, Menu menu) 
	{	
		buildMenuBar(ui,menu);
		
		menu.addObserver( new MenuObserver(ui,menu) );
	}
	
	private JMenuBar buildMenuBar (SwingUI ui, Menu menu)
	{
		JMenuBar menubar = new JMenuBar();
		
		for (Option item: menu.getItems()) {
			if (item instanceof Menu)
				menubar.add( buildMenu(ui, (Menu)item) );
			else 
				menubar.add ( buildMenuItem(ui, item) ); 
		}

		ui.setJMenuBar(menubar);
		
		menubar.updateUI();
		
		return menubar;
	}

	private JMenu buildMenu (SwingUI ui, Menu menu)
	{
		JMenu jmenu = new JMenu( menu.getLabel().getText() );

		jmenu.setToolTipText( menu.getLabel().getDescription() );
	    jmenu.setEnabled( menu.isEnabled() );
	    jmenu.setVisible( menu.isVisible() );

		if (menu.getLabel().getIcon()!=null)
			jmenu.setIcon( ui.loadIcon(menu.getLabel().getIcon()) );

		if (menu.getShortcut()!=0)
			jmenu.setMnemonic( menu.getShortcut() );
			
		for (Option item: menu.getItems()) {
			if (item instanceof Menu)
				jmenu.add( buildMenu(ui,(Menu)item) );
			else if (item instanceof Separator)
				jmenu.addSeparator();
			else
				jmenu.add( buildMenuItem(ui,item) );
		}

		return jmenu;
	}
	
	private JMenuItem buildMenuItem (SwingUI ui, Option option)
	{
		JMenuItem menuItem = new JMenuItem( option.getLabel().getText() );
		
		menuItem.setToolTipText( option.getLabel().getDescription() );
	    menuItem.setEnabled( option.isEnabled() );
	    menuItem.setVisible( option.isVisible() );
		menuItem.addActionListener(new SwingActionHandler(option.getAction()));
		
		if (option.getLabel().getIcon()!=null)
			menuItem.setIcon( ui.loadIcon(option.getLabel().getIcon()) );

		if (option.getShortcut()!=0)
			menuItem.setAccelerator( KeyStroke.getKeyStroke( option.getShortcut(), 0 ) );
		
		return menuItem;
	}


	// Menu observer
	
	public class MenuObserver implements Observer
	{
		private SwingUI ui;
		private Menu menu;
		
		public MenuObserver (SwingUI ui, Menu menu)
		{
			this.ui = ui;
			this.menu = menu;
		}

		@Override
		public void update(Subject o, Object arg) 
		{
			buildMenuBar(ui,menu);
		}
	}
	
}
