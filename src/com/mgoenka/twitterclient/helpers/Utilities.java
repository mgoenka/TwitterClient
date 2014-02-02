package com.mgoenka.twitterclient.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utilities {
	public static boolean isNetworkAvailable() {
        //ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return true;
        //return activeNetworkInfo != null;
    }

}
