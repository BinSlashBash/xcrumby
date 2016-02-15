package com.crumby.impl.derpibooru;

import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class DerpibooruWatchedFragment extends DerpibooruNeedsLoginFragment {
    public static final String BASE_URL = "https://derpibooru.org/images/watched";
    public static final int BREADCRUMB_ICON = 2130837628;
    public static final String BREADCRUMB_NAME = "my watched";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "My Derpibooru Watched Tags";
    public static final String REGEX_URL;
    public static final String SUFFIX = "/images/watched";
    public static final boolean SUGGESTABLE = true;

    /* renamed from: com.crumby.impl.derpibooru.DerpibooruWatchedFragment.1 */
    class C14641 extends DerpibooruLoggedInUserProducer {
        C14641() {
        }

        protected String getSuffix() {
            return DerpibooruWatchedFragment.SUFFIX;
        }
    }

    static {
        REGEX_URL = DerpibooruFragment.REGEX_BASE + SUFFIX + CrumbyGalleryFragment.REGEX_URL;
        BREADCRUMB_PARENT_CLASS = DerpibooruFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new C14641();
    }
}
