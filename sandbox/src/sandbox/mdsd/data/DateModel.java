package sandbox.mdsd.data;

import java.text.DateFormat;
import java.util.Date;

public class DateModel implements DataModel<Date> 
{

	@Override
	public String toString(Date object) 
	{
		return DateFormat.getDateInstance().format( object );
	}

	@Override
	public Date fromString (String string) 
	{
		Date date;
		
		try {
			date =  DateFormat.getDateInstance().parse(string);
		} catch (Exception error) {
			date = null;
		}
		
		return date;
	}

}
