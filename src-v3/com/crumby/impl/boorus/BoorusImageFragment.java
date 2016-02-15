package com.crumby.impl.boorus;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.device.DeviceFragment;
import com.crumby.impl.gelbooru.GelbooruImageProducer;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalImageProducer;

public class BoorusImageFragment extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "Gelbooru";
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;
    private boolean mustUseUniversal;

    /* renamed from: com.crumby.impl.boorus.BoorusImageFragment.1 */
    class C14601 extends UniversalImageProducer {
        final /* synthetic */ String val$baseUrl;

        C14601(String str) {
            this.val$baseUrl = str;
        }

        protected String getScriptName() {
            return BoorusImageFragment.class.getSimpleName();
        }

        protected String getBaseUrl() {
            return this.val$baseUrl;
        }

        protected String getRegexForMatchingId() {
            return GalleryViewerFragment.matchIdFromUrl(BoorusImageFragment.REGEX_URL, BoorusImageFragment.this.getUrl());
        }
    }

    static {
        REGEX_URL = BoorusFragment.REGEX_BASE + "/index\\.php.*&id=([0-9]+)";
        BREADCRUMB_PARENT_CLASS = BoorusFragment.class;
    }

    public void showError(Exception e) {
        if (this.mustUseUniversal) {
            super.showError(e);
            return;
        }
        this.mustUseUniversal = SUGGESTABLE;
        this.reloading = SUGGESTABLE;
        this.producer = createProducer();
        this.producer.initialize(this, getImage(), null, SUGGESTABLE);
    }

    protected GalleryProducer createProducer() {
        String baseUrl = BoorusFragment.getBaseUrl(getUrl());
        if (this.mustUseUniversal) {
            return new C14601(baseUrl);
        }
        return new GelbooruImageProducer(baseUrl + DeviceFragment.REGEX_BASE, REGEX_URL, baseUrl + BoorusFragment.API_ROOT_SUFFIX);
    }

    protected String getTagUrl(String tag) {
        return BoorusFragment.getBaseUrl(getUrl()) + "/index.php?page=post&s=list&tags=" + Uri.encode(tag);
    }
}
