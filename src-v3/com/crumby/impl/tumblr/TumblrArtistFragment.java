package com.crumby.impl.tumblr;

import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;
import java.util.regex.Pattern;

public class TumblrArtistFragment extends TumblrFragment {
    public static final String ALT_SEARCH_BASE;
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837632;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_BASE;
    public static final String REGEX_URL;

    /* renamed from: com.crumby.impl.tumblr.TumblrArtistFragment.1 */
    class C12851 extends UniversalProducer {
        C12851() {
        }

        protected String getScriptName() {
            return TumblrArtistFragment.class.getSimpleName();
        }

        protected String getBaseUrl() {
            return "http://api.tumblr.com";
        }

        protected String getRegexForMatchingId() {
            return TumblrArtistFragment.REGEX_URL;
        }
    }

    static {
        REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + GalleryViewerFragment.captureMinimumLength("[a-zA-Z0-9_\\+\\-]", 4) + "\\." + Pattern.quote(TumblrFragment.ROOT_NAME);
        REGEX_URL = REGEX_BASE + CrumbyGalleryFragment.REGEX_URL;
        BREADCRUMB_PARENT_CLASS = TumblrFragment.class;
        ALT_SEARCH_BASE = REGEX_BASE;
    }

    protected GalleryProducer createProducer() {
        return new C12851();
    }
}
