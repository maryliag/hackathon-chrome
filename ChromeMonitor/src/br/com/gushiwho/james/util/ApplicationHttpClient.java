package br.com.gushiwho.james.util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ApplicationHttpClient {
	
private static AsyncHttpClient mClient = new AsyncHttpClient();
	
	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		mClient.get(getAbsoluteUrl(url), params, responseHandler);
		System.out.println(getAbsoluteUrl(url) + " " + params);
	}
	
	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		mClient.post(getAbsoluteUrl(url), params, responseHandler);
		System.out.println(getAbsoluteUrl(url) + " " + params);
	}
	
	public static void getFromExternal(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		mClient.get(url, params, responseHandler);
		System.out.println(getAbsoluteUrl(url) + " " + params);
	}
	
	public static String getAbsoluteUrl(String relativeUrl) {
		return "http://23.22.100.228/" + relativeUrl;
	}

}
