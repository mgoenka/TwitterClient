package com.mgoenka.twitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;

public class User extends Model {
	private String name;
	private String screenName;
	private String profileImageUrl;
	private String profileBgImageUrl;
	
    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getProfileBackgroundImageUrl() {
        return profileBgImageUrl;
    }

    public static User fromJson(JSONObject json) {
        User u = new User();
        try {
        	u.name = json.getString("name");
        	u.screenName = json.getString("screen_name");
        	u.profileImageUrl = json.getString("profile_image_url");
        	u.profileBgImageUrl = json.getString("profile_background_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }
}
