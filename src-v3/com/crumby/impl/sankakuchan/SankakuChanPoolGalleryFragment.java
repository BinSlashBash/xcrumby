package com.crumby.impl.sankakuchan;

import com.crumby.impl.e621.e621PoolFragment;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;

public class SankakuChanPoolGalleryFragment extends GalleryGridFragment {
    public static final String BASE_URL = "https://chan.sankakucomplex.com/pool/show";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837613;
    public static final String BREADCRUMB_NAME = "pools";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;

    /* renamed from: com.crumby.impl.sankakuchan.SankakuChanPoolGalleryFragment.1 */
    class C12841 extends UniversalProducer {
        C12841() {
        }

        protected String getScriptName() {
            return SankakuChanPoolGalleryFragment.class.getSimpleName();
        }

        protected String getBaseUrl() {
            return SankakuChanFragment.BASE_URL;
        }

        protected String getRegexForMatchingId() {
            return SankakuChanPoolGalleryFragment.REGEX_URL;
        }
    }

    static {
        REGEX_BASE = SankakuChanPoolFragment.REGEX_BASE + "/show\\.xml\\?\\&id=";
        REGEX_URL = REGEX_BASE + "([0-9]+)*";
        BREADCRUMB_PARENT_CLASS = e621PoolFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new C12841();
    }
}
