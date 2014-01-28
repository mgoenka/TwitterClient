package com.mgoenka.twitterclient.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Tweet")
public class Tweet extends Model {
	@Column(name = "Body")
	private String body;

	@Column(name = "UserName")
	private String userName;

	@Column(name = "UserScreenName")
	private String userScreenName;
	
	@Column(name = "UserProfileImageUrl")
	private String userProfileImageUrl;
	
	private long uid;
	private boolean favorited;
	private boolean retweeted;
	private User user;

    public String getBody() {
        return body;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserScreenName() {
        return userScreenName;
    }

    public String getUserProfileImageUrl() {
        return userProfileImageUrl;
    }

    public long getUserId() {
        return uid;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
        	tweet.body = jsonObject.getString("text");
        	tweet.uid = jsonObject.getLong("id");
        	tweet.favorited = jsonObject.getBoolean("favorited");
        	tweet.retweeted = jsonObject.getBoolean("retweeted");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
            tweet.userName = tweet.user.getName();
            tweet.userScreenName = tweet.user.getScreenName();
            tweet.userProfileImageUrl = tweet.user.getProfileImageUrl();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        tweet.save();
        return tweet;
    }

    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = Tweet.fromJson(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }

        return tweets;
    }
}
