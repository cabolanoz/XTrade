package com.xtrade.android.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * This implementation will be useful to dumb data,
 * so it can emulate http calls and retribution of data
 * through the control of sleeping thread and 
 * retribution through Mock objects or data
 * 
 * 
 * 
 * 
 * */

public class HttpCallerMockImpl extends AbstractHttpCaller{
	
	@Override
	public boolean call(URL urlResource) {
		return call(urlResource,null);
	}

	@Override
	public boolean call(URL urlResource, Object... params) {
		try {
			InputStream inputStream=urlResource.openStream();
			final 	char[] buffer =new char[BUFFER_LIMIT];
			StringBuilder out = new StringBuilder();
			
			Reader in = new InputStreamReader(inputStream, "UTF-8");
			try {
			  int read;
			  do {
			    read = in.read(buffer, 0, buffer.length);
			    if (read>0) {
			      out.append(buffer, 0, read);
			    }
			  } while (read>=0);
			} finally {
			  in.close();
			}
			
			result=out.toString();
			
			return true;
		} catch (IOException e) {

			e.printStackTrace();
		}

		
		return false;
	}
	

}
