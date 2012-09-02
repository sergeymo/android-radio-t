package org.dandelion.radiot.util;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class CustomTitleActivity extends Activity {
    private ActivityHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = ActivityHelper.create(this);
        helper.onCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        helper.onPostCreate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return helper.onOptionsItemSelected(item) ||
                super.onOptionsItemSelected(item);
    }
}

