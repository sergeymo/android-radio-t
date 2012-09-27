package org.dandelion.radiot.helpers;

import org.dandelion.radiot.podcasts.core.PodcastList;
import org.dandelion.radiot.podcasts.core.PodcastsCache;

public class MemoryCache implements PodcastsCache {
    public PodcastList data;

    @Override
    public void reset() {
        data = null;
    }

    @Override
    public PodcastList getData() {
        return data;
    }

    @Override
    public void updateWith(PodcastList data) {
        this.data = data;
    }

    @Override
    public boolean isValid() {
        return data != null;
    }
}