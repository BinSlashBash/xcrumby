package com.crumby.impl.wildcritter;

import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;

public class WildCritterImageFragment extends GalleryImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;

    /* renamed from: com.crumby.impl.wildcritter.WildCritterImageFragment.1 */
    class C12871 extends UniversalProducer {
        C12871() {
        }

        protected String getScriptName() {
            return "DanbooruImageHtmlFragment";
        }

        protected String getBaseUrl() {
            return WildCritterFragment.BASE_URL;
        }

        protected String getRegexForMatchingId() {
            return WildCritterImageFragment.REGEX_URL;
        }
    }

    static {
        REGEX_URL = WildCritterFragment.REGEX_BASE + "/post/show/([0-9]+).*";
        BREADCRUMB_PARENT_CLASS = WildCritterFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new C12871();
    }
}
