package com.mgoenka.twitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;

public class User extends Model {
	private String name;
	private String screenName;
	private String tagline;
	private String profileImageUrl;
	private String profileBgImageUrl;
	private int followersCount;
	private int friendsCount;
	private long profileId;
	
    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getTagline() {
        return tagline;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getProfileBackgroundImageUrl() {
        return profileBgImageUrl;
    }
    
    public int getFollowersCount() {
    	return followersCount;
    }

    public int getFriendsCount() {
    	return friendsCount;
    }

    public long getProfileId() {
        return profileId;
    }

    public static User fromJson(JSONObject json) {
        User u = new User();
        try {
        	u.name = json.getString("name");
        	u.screenName = json.getString("screen_name");
        	u.tagline = json.getString("description");
        	u.profileImageUrl = json.getString("profile_image_url");
        	u.profileBgImageUrl = json.getString("profile_background_image_url");
        	u.followersCount = json.getInt("followers_count");
        	u.friendsCount = json.getInt("friends_count");
        	u.profileId = json.getLong("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }
}
