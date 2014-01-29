package com.mgoenka.twitterclient;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mgoenka.twitterclient.models.Keys;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 */

public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = Keys.getConsumerKey(); //Integer.toString(R.string.consumer_key);       // Change this
    public static final String REST_CONSUMER_SECRET = Keys.getConsumerSecret(); //Integer.toString(R.string.consumer_Secret); // Change this
    public static final String REST_CALLBACK_URL = "oauth://twitterclient"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeline(AsyncHttpResponseHandler handler, boolean more, long maxId) {
    	String url = getApiUrl("1.1/statuses/home_timeline.json");
        RequestParams params = new RequestParams();
    	params.put("count", "25");
        if (more) {
        	params.put("max_id", String.valueOf(maxId - 1));
        }
    	
    	client.get(url, params, handler);
    }

    public void postStatusUpdates(AsyncHttpResponseHandler handler, String status) {
    	String url = getApiUrl("1.1/statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", status);
    	
    	client.post(url, params, handler);
    }
}
