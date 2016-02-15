package com.crumby.impl.furaffinity;

import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;

public class FurAffinityUserFragment extends GalleryGridFragment {
    public static final String BASE_URL = "http://furaffinity.net/user/";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837632;
    public static final String BREADCRUMB_NAME = "furAffinity";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "furAffinity";
    public static final String REGEX_URL;
    public static final String SUFFIX = "/user/";

    /* renamed from: com.crumby.impl.furaffinity.FurAffinityUserFragment.1 */
    class C12761 extends UniversalProducer {
        C12761() {
        }

        public String getScriptName() {
            return FurAffinityUserFragment.class.getSimpleName();
        }

        protected String getBaseUrl() {
            return "http://www.furaffinity.net";
        }

        protected String getRegexForMatchingId() {
            return FurAffinityUserFragment.REGEX_URL;
        }
    }

    static {
        REGEX_URL = FurAffinityFragment.REGEX_BASE + SUFFIX + "(.*)";
        BREADCRUMB_PARENT_CLASS = FurAffinityFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new C12761();
    }
}
