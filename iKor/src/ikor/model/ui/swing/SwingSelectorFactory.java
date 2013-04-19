package ikor.model.ui.swing;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ikor.collection.Dictionary;
import ikor.model.Observer;
import ikor.model.Subject;
import ikor.model.ui.Option;
import ikor.model.ui.Selector;
import ikor.model.ui.Separator;
import ikor.model.ui.UIFactory;


public class SwingSelectorFactory implements UIFactory<SwingUI,Selector>
{
	@Override
	public void build(SwingUI ui, Selector selector) 
	{	
		JList       jlist = new JList();
		ListHandler handler = new ListHandler(selector);
		
		jlist.addListSelectionListener( new ListHandler(selector) );
		

		updateList (ui,selector,jlist,handler);
		
		selector.addObserver( new ListObserver(ui,selector,jlist,handler) );
		
		JScrollPane scroll = new JScrollPane(jlist);
		
		scroll.setMinimumSize( new Dimension(125,25) );
		
		ui.addComponent ( scroll );	
	}
	
	public void updateList (SwingUI ui, Selector selector, JList jlist, ListHandler handler)
	{
		JListModel    model = new JListModel(ui, selector);
		JListRenderer renderer = new JListRenderer(model); 
		
		if (selector.isMultipleSelection())
			jlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		else
			jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//jlist.setBorder( BorderFactory.createEtchedBorder() );
		jlist.setModel(model); 
		jlist.setCellRenderer(renderer);
		
		handler.setSelector(selector);
	}

	// Observer design pattern
	
	public class ListObserver implements Observer
	{
		private SwingUI ui;
		private JList control;
		private Selector selector;
		private ListHandler handler;
		
		public ListObserver (SwingUI ui, Selector selector, JList control, ListHandler handler)
		{
			this.ui = ui;
			this.selector = selector;
			this.control = control;
			this.handler = handler;
		}

		@Override
		public void update(Subject o, Object arg) 
		{
		    SwingUtilities.invokeLater(new Runnable() 
		    {
		      public void run()
		      {
		    	  updateList(ui,selector,control,handler);
		    	  control.updateUI();
		      }
		    });			
		}
	}	
	
	// Event handler
	
	class ListHandler implements ListSelectionListener
	{
		private Selector selector;
		
		public ListHandler (Selector selector)
		{
			this.selector = selector;
		}
		
		public void setSelector (Selector selector)
		{
			this.selector = selector;
		}
		
		@Override
		public void valueChanged (ListSelectionEvent event) 
		{
			JList  list = (JList) event.getSource();			
			
			if (!selector.isMultipleSelection()) {
				
				// Single selection
				
				if (list.getSelectedValue() instanceof Option) {

					Option option = (Option)list.getSelectedValue();

					if (option!=selector.getSelectedOption()) {
						selector.setSelected(option);

						if (option.getAction()!=null)
							option.getAction().run();
					}
				}
				
			} else if (!list.getValueIsAdjusting()) {
				
				// Multiple selection

				selector.clearSelected();

				for (int i=0; i<list.getModel().getSize(); i++) {
					
					Option option = selector.getOptions().get(i);

					if (list.isSelectedIndex(i)) {
						selector.addSelectedOption(option);

						if (option.getAction()!=null)
							option.getAction().run();
						
					} else {
						selector.removeSelectedOption(option);
					}
				}
			}
		}			
	}

	// Custom list model
	
	class JListModel implements ListModel 
	{
		private Selector selector;
		private Dictionary<String,ImageIcon> icons;

		public JListModel (SwingUI ui, Selector selector) 
		{
			this.selector = selector;
			this.icons = new ikor.collection.DynamicDictionary<String,ImageIcon>();
			
			for (Option option: selector.getOptions()) {
				
				ImageIcon icon = getIcon(option);
				
				if ((icon==null) && (option.getLabel().getIcon()!=null)) {
					icon = ui.loadIcon(option.getLabel().getIcon());
					icons.set(option.getLabel().getIcon(), icon);
				}
			}
		}

		public Object getElementAt (int index) 
		{
			return selector.getOptions().get(index);
		}

		public int getSize() 
		{
			return selector.getOptions().size();
		}
		
		public ImageIcon getIcon (Option option)
		{
			return icons.get(option.getLabel().getIcon());
		}

		public void addListDataListener (ListDataListener l) {}

		public void removeListDataListener (ListDataListener l) {}
	}

	// Custom list renderer
	
	class JListRenderer extends DefaultListCellRenderer 
	{
		JListModel model;
		
		public JListRenderer (JListModel model)
		{
			this.model = model;
		}

		public java.awt.Component getListCellRendererComponent ( JList list, Object value, int index, boolean isSelected, boolean hasFocus) 
		{
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);

			if (value instanceof Separator) {
				label = new JLabel();
				label.setBorder( BorderFactory.createLineBorder(Color.darkGray) );
				label.setPreferredSize( new Dimension( 1, 1 ) );
			} else if (value instanceof Option) {
				Option option = (Option) value;
				label.setIcon(model.getIcon(option));				
			}
			
			return label;
		}
	}

}
