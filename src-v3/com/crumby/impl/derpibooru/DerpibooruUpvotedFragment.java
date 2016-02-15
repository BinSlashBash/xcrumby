package com.crumby.impl.derpibooru;

import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class DerpibooruUpvotedFragment extends DerpibooruNeedsLoginFragment {
    public static final String BASE_URL = "https://derpibooru.org/images/upvoted";
    public static final int BREADCRUMB_ICON = 2130837623;
    public static final String BREADCRUMB_NAME = "my upvoted";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "My Derpibooru Upvoted posts";
    public static final String REGEX_URL;
    public static final String SUFFIX = "/images/upvoted";
    public static final boolean SUGGESTABLE = true;

    /* renamed from: com.crumby.impl.derpibooru.DerpibooruUpvotedFragment.1 */
    class C14631 extends DerpibooruLoggedInUserProducer {
        C14631() {
        }

        protected String getSuffix() {
            return DerpibooruUpvotedFragment.SUFFIX;
        }
    }

    static {
        REGEX_URL = DerpibooruFragment.REGEX_BASE + SUFFIX + CrumbyGalleryFragment.REGEX_URL;
        BREADCRUMB_PARENT_CLASS = DerpibooruFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new C14631();
    }
}
