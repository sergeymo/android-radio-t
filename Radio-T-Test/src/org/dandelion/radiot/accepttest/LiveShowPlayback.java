package org.dandelion.radiot.accepttest;

import org.dandelion.radiot.live.LiveShowActivity;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public class LiveShowPlayback extends
		ActivityInstrumentationTestCase2<LiveShowActivity> {

	private LiveShowActivity activity;
	private Solo solo;

	public LiveShowPlayback() {
		super("org.dandelion.radiot", LiveShowActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
		solo = new Solo(getInstrumentation(), activity);
	}

	public void testGoingThroughPlaybackStateSequence() throws Exception {
		assertTrue(solo.waitForText("Idle"));
		solo.clickOnButton("Start");
		assertTrue(solo.waitForText("Waiting"));
		assertTrue(solo.waitForText("Playing"));
		solo.clickOnButton("Stop");
		assertTrue(solo.waitForText("Idle"));
	}
}