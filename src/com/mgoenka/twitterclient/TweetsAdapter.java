package com.mgoenka.twitterclient;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mgoenka.twitterclient.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet> {
	Context activityContext;
	
	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
		activityContext = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater. inflate(R.layout.tweet_item, null);
		}
		
		final Tweet tweet = getItem(position);
		
		final ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
		ImageLoader.getInstance().displayImage(tweet.getUserProfileImageUrl(), imageView);
		
		TextView nameView = (TextView) view.findViewById(R.id.tvName);
		String formattedName = "<b>" + tweet.getUserName() + "</b> <small><font color='#777777'>@" +
				tweet.getUserScreenName() + "</font></small>";
		nameView.setText(Html.fromHtml(formattedName));
		
		TextView bodyView = (TextView) view.findViewById(R.id.tvBody);
		bodyView.setText(Html.fromHtml(tweet.getBody()));
		
		imageView.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
                if (v.equals(imageView)) {
                    // Write your awesome code here
                	Intent i = new Intent(activityContext, ProfileActivity.class);
            		i.putExtra("user_id", tweet.getProfileId());
            		i.putExtra("screen_name", tweet.getUserScreenName());
                	activityContext.startActivity(i);
                }
            }
		});
		
		return view;
	}
}
