package com.crumby.impl.derpibooru;

import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class DerpibooruUploadedFragment extends DerpibooruNeedsLoginFragment {
    public static final String BASE_URL = "https://derpibooru.org/images/uploaded";
    public static final int BREADCRUMB_ICON = 2130837607;
    public static final String BREADCRUMB_NAME = "my uploaded";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "My Derpibooru Uploaded Pictures";
    public static final String REGEX_URL;
    public static final String SUFFIX = "/images/uploaded";
    public static final boolean SUGGESTABLE = true;

    /* renamed from: com.crumby.impl.derpibooru.DerpibooruUploadedFragment.1 */
    class C14621 extends DerpibooruLoggedInUserProducer {
        C14621() {
        }

        protected String getSuffix() {
            return DerpibooruUploadedFragment.SUFFIX;
        }
    }

    static {
        REGEX_URL = DerpibooruFragment.REGEX_BASE + SUFFIX + CrumbyGalleryFragment.REGEX_URL;
        BREADCRUMB_PARENT_CLASS = DerpibooruFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new C14621();
    }
}
