package org.dandelion.radiot.util;

import android.content.Intent;
import android.view.MenuItem;
import org.dandelion.radiot.R;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.widget.TextView;
import org.dandelion.radiot.home_screen.HomeScreenActivity;

abstract class ActivityHelper {
    protected Activity activity;

    public static ActivityHelper create(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return new GingerbreadHelper(activity);
        } else {
            return new HoneycombHelper(activity);
        }
    }

    private ActivityHelper(Activity activity) {
        this.activity = activity;
    }

    public abstract void onCreate();

    public abstract void onPostCreate();

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(activity, HomeScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

    private static class GingerbreadHelper extends ActivityHelper {
        public GingerbreadHelper(Activity activity) {
            super(activity);
        }

        @Override
        public void onCreate() {
            activity.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        }

        @Override
        public void onPostCreate() {
            activity.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
            TextView titleText = (TextView) activity.findViewById(R.id.titlebar_title);
            titleText.setText(activity.getTitle());
        }
    }

    private static class HoneycombHelper extends ActivityHelper {
        public HoneycombHelper(Activity activity) {
            super(activity);
        }

        @Override
        public void onCreate() {
            ActionBar actionBar = activity.getActionBar();
            if (actionBar != null)  {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }

        @Override
        public void onPostCreate() {

        }
    }
}