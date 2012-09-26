package org.dandelion.radiot.integration;

import android.graphics.Bitmap;
import android.test.InstrumentationTestCase;
import org.dandelion.radiot.podcasts.core.PodcastItem;
import org.dandelion.radiot.podcasts.ui.PodcastVisual;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PodcastVisualTest extends InstrumentationTestCase {
    private static final Bitmap DEFAULT_THUMBNAIL =
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);

    public void testReturnsDefaultThumbnailIfThumbnailIsMissing() throws Exception {
        PodcastVisual visual = new PodcastVisual(aPodcastItemWith(noThumbnail()));

        assertThat(visual.getThumbnail(DEFAULT_THUMBNAIL),
                equalTo(DEFAULT_THUMBNAIL));
    }

    public void testReturnsBitmapIfThumbnailDataIsValid() throws Exception {
        PodcastVisual visual = new PodcastVisual(aPodcastItemWith(validThumbnail()));
        assertThat(visual.getThumbnail(DEFAULT_THUMBNAIL),
                not(equalTo(DEFAULT_THUMBNAIL)));
    }

    public void testReturnsDefaultThumbnailIfThumbnailDataIsInvalid() throws Exception {
        PodcastVisual visual = new PodcastVisual(aPodcastItemWith(invalidThumbnail()));
        assertThat(visual.getThumbnail(DEFAULT_THUMBNAIL),
                (equalTo(DEFAULT_THUMBNAIL)));
    }

    public void testCachesThumbnail() throws Exception {
        PodcastVisual visual = new PodcastVisual(aPodcastItemWith(validThumbnail()));

        Bitmap first = visual.getThumbnail(DEFAULT_THUMBNAIL);
        Bitmap second = visual.getThumbnail(DEFAULT_THUMBNAIL);
        assertThat(first, sameInstance(second));
    }

    private byte[] invalidThumbnail() {
        return new byte[] {0, 0, 0, 0};
    }

    private byte[] validThumbnail() {
        Bitmap thumbnail = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, out);
        return out.toByteArray();
    }

    private PodcastItem aPodcastItemWith(byte[] thumbnailData) {
        PodcastItem p = new PodcastItem();
        p.setThumbnailData(thumbnailData);
        return p;
    }

    private byte[] noThumbnail() {
        return null;
    }
}
