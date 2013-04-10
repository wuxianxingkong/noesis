package ikor.model.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateModel implements DataModel<Date> 
{ 
	@Override
	public String toString(Date object) 
	{
		return DateFormat.getDateInstance(DateFormat.SHORT).format( object );
	}

	@Override
	public Date fromString (String string) 
	{
		Date date;
		DateFormat df;
		
		try {
			df = DateFormat.getDateInstance(DateFormat.SHORT);
			df.setLenient(false);
			date = df.parse(string);
		} catch (ParseException error) {
			date = null;
		}
		
		return date;
	}

}
