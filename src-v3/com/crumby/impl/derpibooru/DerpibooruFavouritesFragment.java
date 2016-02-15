package com.crumby.impl.derpibooru;

import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class DerpibooruFavouritesFragment extends DerpibooruNeedsLoginFragment {
    public static final String BASE_URL = "https://derpibooru.org/images/favourites";
    public static final int BREADCRUMB_ICON = 2130837620;
    public static final String BREADCRUMB_NAME = "my favorites";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "My Derpibooru Favourites";
    public static final String REGEX_URL;
    public static final String SUFFIX = "/images/favourites";
    public static final boolean SUGGESTABLE = true;

    /* renamed from: com.crumby.impl.derpibooru.DerpibooruFavouritesFragment.1 */
    class C14611 extends DerpibooruLoggedInUserProducer {
        C14611() {
        }

        protected String getSuffix() {
            return DerpibooruFavouritesFragment.SUFFIX;
        }
    }

    static {
        REGEX_URL = DerpibooruFragment.REGEX_BASE + SUFFIX + CrumbyGalleryFragment.REGEX_URL;
        BREADCRUMB_PARENT_CLASS = DerpibooruFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new C14611();
    }
}
