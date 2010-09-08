package org.dandelion.radiot.home_screen;

import org.dandelion.radiot.PodcastListActivity;
import org.dandelion.radiot.R;
import org.dandelion.radiot.live.LiveShowActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class HomeScreenActivity extends Activity {

	public static void start(Context context) {
		Intent intent = new Intent(context, HomeScreenActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		initHomeScreenItems();
		Log.i("RadioT", "Create new home");
	}

	@Override
	protected void onDestroy() {
		Log.i("RadioT", "Destroyed home");
		super.onDestroy();
	}

	private void initHomeScreenItems() {
		HomeScreenAdapter adapter = initAdapter();
		ListView listView = (ListView) findViewById(R.id.podcasts_menu);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(adapter);
	}

	protected HomeScreenAdapter initAdapter() {
		HomeScreenAdapter a = new HomeScreenAdapter(this);
		a.addItem(R.string.live_show_title, R.drawable.ic_list_liveshow,
				new HomeScreenItem.OnClickListener() {
					public void onClick(HomeScreenItem item) {
						startActivity(new Intent(HomeScreenActivity.this,
								LiveShowActivity.class));
					}
				});
		a.addSeparator();
		a.addItem(R.string.main_show_title, R.drawable.ic_list_podcasts,
				new HomeScreenItem.OnClickListener() {
					public void onClick(HomeScreenItem item) {
						PodcastListActivity.start(HomeScreenActivity.this,
								item.title, "main-show");
					}
				});
		a.addItem(R.string.after_show_title, R.drawable.ic_list_aftershow,
				new HomeScreenItem.OnClickListener() {
					public void onClick(HomeScreenItem item) {
						PodcastListActivity.start(HomeScreenActivity.this,
								item.title, "after-show");
					}
				});
		a.addSeparator();
		a.addItem(R.string.about_app_title, R.drawable.ic_list_about,
				new HomeScreenItem.OnClickListener() {
					public void onClick(HomeScreenItem item) {
						startActivity(new Intent(HomeScreenActivity.this,
								AboutAppActivity.class));
					}
				});
		return a;
	}
}
