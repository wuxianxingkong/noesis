package noesis.io;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;

import java.util.zip.GZIPInputStream;

public class SNAPGZNetworkReader extends SNAPNetworkReader 
{

	public SNAPGZNetworkReader (InputStream input)
		throws IOException
	{
		InputStream gzip = new GZIPInputStream( input );
		Reader reader = new InputStreamReader(gzip);
	
		setReader(reader);		
	}
}
