package com.crumby.impl.sankakuchan;

import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;

public class SankakuChanPoolFragment extends GalleryGridFragment {
    public static final String BASE_URL = "https://chan.sankakucomplex.com/pool";
    public static final String BREADCRUMB_NAME = "pools";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "pools";
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final int SEARCH_FORM_ID = 2130903064;

    /* renamed from: com.crumby.impl.sankakuchan.SankakuChanPoolFragment.1 */
    class C12831 extends UniversalProducer {
        C12831() {
        }

        protected String getScriptName() {
            return SankakuChanPoolFragment.class.getSimpleName();
        }

        protected String getBaseUrl() {
            return SankakuChanFragment.BASE_URL;
        }

        protected String getRegexForMatchingId() {
            return null;
        }
    }

    static {
        REGEX_BASE = SankakuChanFragment.REGEX_BASE + "/pool";
        REGEX_URL = REGEX_BASE + CrumbyGalleryFragment.REGEX_URL;
        BREADCRUMB_PARENT_CLASS = SankakuChanFragment.class;
    }

    protected int getHeaderLayout() {
        return C0065R.layout.omniform_container;
    }

    protected void setupHeaderLayout(ViewGroup header) {
        ((OmniformContainer) header).showAsInGrid(getImage().getLinkUrl());
    }

    public String getSearchPrefix() {
        return DISPLAY_NAME;
    }

    public String getSearchArgumentName() {
        return "query";
    }

    protected GalleryProducer createProducer() {
        return new C12831();
    }
}
