package noesis.ui.model;

import noesis.ui.model.actions.ExitAction;
import ikor.model.ui.Application;
import ikor.model.ui.Image;
import ikor.model.ui.Label;
import ikor.model.ui.Option;
import ikor.model.ui.UIModel;
import ikor.util.xml.rss.AtomFeedReader;
import ikor.util.xml.rss.Feed;
import ikor.util.xml.rss.FeedMessage;
import ikor.util.xml.rss.FeedReader;


public class TipUIModel extends UIModel
{
	private Feed feed;
	
	// Singleton
	
	private static TipUIModel tips = null;
	
	
	public static TipUIModel create (Application app)
	{
		if (tips==null)
			tips = new TipUIModel (app);
		
		return tips;
	}
	
	
	// Constructor
	
	
	private TipUIModel (Application app)
	{
		super(app, "NOESIS updates");
		
		setIcon( app.url("icon.gif") );
		
		add( new Image("logo", app.url("logo.gif") ) );

		FeedMessage message = message();

		if (message!=null) {
			add( new Label("<html><body style='width:300px;text-align:left;font-size:120%;font-weight:bold'>"+message.getTitle()+"</body></html>") );
			add( new Label("<html><body style='width:300px;text-align:justify'>"+message.getContent()+"</body></html>") );
			add( new Label("<html><body style='width:300px;text-align:left'><a href=\"http://noesis.ikor.org/updates\">More...</a></body></html>"));
		} 

		Option ok = new Option("OK");
		ok.setIcon( app.url("icon.gif") );
		ok.setAction( new ExitAction(this) );
		add( ok );			
	}
	
	@Override
	public void start ()
	{
		if (feed==null)
			exit();
	}
	
	
	public FeedMessage message ()
	{
		FeedReader reader = new AtomFeedReader("http://goo.gl/uTpTb1"); 
		// http://goo.gl/uTpTb1 -> http://noesis.ikor.org/updates/posts.xml
		
		feed = reader.read();
		
		if ((feed!=null) && (feed.getMessageCount()>0))
			return feed.getMessage(0);
		else
			return null;
	}

}
