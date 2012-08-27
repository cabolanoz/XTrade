package com.xtrade.android.http;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.xtrade.android.util.Debug;

public class HttpCallerApacheImpl extends AbstractHttpCaller{

	public boolean call(URL urlResource) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost;
		HttpResponse httpResponse;
		try {
			httpPost = new HttpPost(urlResource.toURI());
			httpResponse = httpClient.execute(httpPost);
			Debug.info(this, "Http request: " + httpResponse.toString());
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
