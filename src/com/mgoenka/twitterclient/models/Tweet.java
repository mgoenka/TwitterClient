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
	
	@Column(name = "UId", index = true, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	private long uid;

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

    public static Tweet fromJson(JSONObject jsonObject) {
    	User user;
    	
        Tweet tweet = new Tweet();
        try {
        	tweet.body = jsonObject.getString("text");
        	tweet.uid = jsonObject.getLong("id");
            user = User.fromJson(jsonObject.getJSONObject("user"));
            tweet.userName = user.getName();
            tweet.userScreenName = user.getScreenName();
            tweet.userProfileImageUrl = user.getProfileImageUrl();
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
