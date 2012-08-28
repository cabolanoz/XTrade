package com.xtrade.android.http;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.xtrade.android.util.Debug;

public class HttpCallerApacheImpl extends AbstractHttpCaller{

	public boolean call(URL urlResource) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet;
		
		try {
			httpGet = new HttpGet(urlResource.toURI());
			
			ResponseHandler<String> responseHandler=new BasicResponseHandler();
	        String responseBody = httpClient.execute(httpGet, responseHandler);
	       
			Debug.info(this, "Response body: " + responseBody);
		} catch (URISyntaxException urise) {
			urise.printStackTrace();
		} catch (ClientProtocolException cpe) {
			cpe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		return false;
	}

	public boolean call(URL urlResource, Object... params) {
		return false;
	}

}
