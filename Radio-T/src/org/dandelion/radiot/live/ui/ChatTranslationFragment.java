package org.dandelion.radiot.live.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import org.dandelion.radiot.R;
import org.dandelion.radiot.live.chat.ChatTranslation;
import org.dandelion.radiot.live.chat.MessageConsumer;

public class ChatTranslationFragment extends ListFragment {
    public static ChatTranslation.Factory chatFactory;
    private static final int MESSAGE_LIMIT = 30;
    private ChatStreamAdapter adapter;
    private ChatTranslation chat;
    private View errorView;
    private View progressView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_translation, container, false);
        errorView = view.findViewById(R.id.chat_error_text);
        progressView = view.findViewById(R.id.progress_container);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ChatStreamAdapter(getActivity(), MESSAGE_LIMIT);
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        chat = chatFactory.create();
        ChatProgressController progressController = new ChatProgressController(adapter, this);
        MessageConsumer consumer = new ChatScroller(adapter, (ChatStreamView) getListView());
        chat.start(consumer, progressController);
    }

    @Override
    public void onPause() {
        super.onPause();
        chat.stop();
    }

    public void showError() {
        Animation animation = loadAnimation(R.anim.slide_in_down);
        errorView.setVisibility(View.VISIBLE);
        errorView.startAnimation(animation);
    }

    public void hideError() {
        errorView.setVisibility(View.GONE);
    }

    public void showProgress() {
        showViewAnimated(progressView);
        hideViewAnimated(getListView());
    }

    private Animation loadAnimation(int id) {
        return AnimationUtils.loadAnimation(getActivity(), id);
    }

    public void hideProgress() {
        hideViewAnimated(progressView);
        showViewAnimated(getListView());
    }

    private void hideViewAnimated(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.startAnimation(loadAnimation(android.R.anim.fade_out));
            view.setVisibility(View.GONE);
        }
    }

    private void showViewAnimated(View view) {
        if (view.getVisibility() != View.VISIBLE) {
            view.startAnimation(loadAnimation(android.R.anim.fade_in));
            view.setVisibility(View.VISIBLE);
        }
    }
}
