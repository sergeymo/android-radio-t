package org.dandelion.radiot.live.core;

import org.dandelion.radiot.live.core.states.*;

public class LiveShowPlayer implements AudioStream.StateListener {
    public static final String SHOW_URL = "http://radio10.promodeejay.net:8181/stream";
    // private static final String SHOW_URL = "http://icecast.bigrradio.com/80s90s";

    public static int waitTimeoutMilliseconds = 60 * 1000;

    private StateChangeListener listener;
    private LiveShowState state;
    private AudioStream audioStream;
    private Timeout waitTimeout;
    private Runnable onWaitTimeout = new Runnable() {
        @Override
        public void run() {
            beConnecting();
        }
    };


    public interface StateChangeListener {
        void onChangedState(LiveShowState newState);
    }

    public static interface StateVisitor {
        void onWaiting(long timestamp);
        void onIdle();
        void onConnecting(long timestamp);
        void onPlaying(long timestamp);
        void onStopping(long timestamp);
    }

    public static void setWaitTimeoutSeconds(int value) {
		waitTimeoutMilliseconds = value * 1000;
	}

    public LiveShowPlayer(AudioStream audioStream, Timeout waitTimeout) {
        this.audioStream = audioStream;
        this.waitTimeout = waitTimeout;
        this.state = new Idle();
        this.audioStream.setStateListener(this);
    }

    public void setListener(StateChangeListener listener) {
        this.listener = listener;
    }

    public void queryState(StateVisitor visitor) {
        state.acceptVisitor(visitor);
    }

    public void togglePlayback() {
        state.togglePlayback(this);
    }

    public boolean isIdle() {
        return (state instanceof Idle);
    }

    public void beIdle() {
        waitTimeout.reset();
        setState(new Idle());
    }

    public void beConnecting() {
        try {
            audioStream.play(SHOW_URL);
            setState(new Connecting());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void beStopping() {
        audioStream.stop();
        setState(new Stopping());
    }

    public void beWaiting() {
        waitTimeout.set(waitTimeoutMilliseconds, onWaitTimeout);
        setState(new Waiting());
    }

    @Override
    public void onStarted() {
        setState(new Playing());
    }

    @Override
    public void onError() {
        state.handleError(this);
    }

    @Override
    public void onStopped() {
        beIdle();
    }

    private void setState(LiveShowState state) {
        this.state = state;
        if (listener != null) {
            listener.onChangedState(state);
        }
    }
}
