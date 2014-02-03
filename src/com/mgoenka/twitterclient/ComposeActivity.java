package com.mgoenka.twitterclient;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mgoenka.twitterclient.controllers.TwitterClientApp;

public class ComposeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		EditText etComposedTweet = (EditText) findViewById(R.id.etComposedTweet);
		Button btnTweet = (Button) findViewById(R.id.btnTweet);
		watcher(etComposedTweet, btnTweet);
	}
	
	public void onCancel(View v) {
		setResult(RESULT_CANCELED);
		finish();
	}
	
	public void onTweet(View v) {
		String composedTweet = ((EditText) findViewById(R.id.etComposedTweet)).getText().toString();
		
		TwitterClientApp.getRestClient().postStatusUpdates(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray postTweets) {
				// Update timeline
				// TimelineActivity.updateTweets();
			}
		}, composedTweet);
		setResult(RESULT_OK);
		finish();
	}
	
	protected void watcher(final EditText etComposedTweet,final Button btnTweet) {
	    etComposedTweet.addTextChangedListener(new TextWatcher() {
	        public void afterTextChanged(Editable s) { 
	            if (etComposedTweet.length() == 0) {
	            	btnTweet.setEnabled(false);
	            } else if (etComposedTweet.length() > 140) {
	            	btnTweet.setEnabled(false);
	            	Toast.makeText(ComposeActivity.this, getString(R.string.length_exceeded), Toast.LENGTH_SHORT).show();
	            } else {
	            	btnTweet.setEnabled(true);
	            }
	        }

	        public void beforeTextChanged(CharSequence s, int start, int count, int after){
	        }
	        
	        public void onTextChanged(CharSequence s, int start, int before, int count){
	        }
	    }); 
	    
	    if (etComposedTweet.length() == 0) {
	    	btnTweet.setEnabled(false);
	    }
	}

}
