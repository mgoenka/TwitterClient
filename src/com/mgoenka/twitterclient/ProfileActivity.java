package com.mgoenka.twitterclient;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mgoenka.twitterclient.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	private static String screenName = "@me@";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		screenName = getIntent().getStringExtra("screen_name");

		if (screenName.equals("@me@")) {
			loadMyProfileInfo();
		} else {
			loadUserProfileInfo();
		}
	}
	
	private void loadMyProfileInfo() {
		TwitterClientApp.getRestClient().getMyInfo(new JsonHttpResponseHandler () {
			@Override
			public void onSuccess(JSONObject json) {
				User u = User.fromJson(json);
				getActionBar().setTitle("@" + u.getScreenName());
				populateProfileHeader(u);
			}
		});
	}

	private void loadUserProfileInfo() {
		TwitterClientApp.getRestClient().getUserInfo(new JsonHttpResponseHandler () {
			@Override
			public void onSuccess(JSONObject json) {
				User u = User.fromJson(json);
				getActionBar().setTitle("@" + u.getScreenName());
				populateProfileHeader(u);
			}
		}, screenName);
	}

	protected void populateProfileHeader(User user) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(Integer.toString(user.getFollowersCount()) + " " + getString(R.string.followers));
		tvFollowing.setText(Integer.toString(user.getFriendsCount()) + " " + getString(R.string.following));
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
	}
	
	public static String getScreenName() {
		return screenName;
	}
}
