package com.crumby.impl.wildcritter;

import android.net.Uri;
import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.impl.sankakuchan.SankakuChanFragment;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.universal.UniversalProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class WildCritterFragment extends GalleryGridFragment {
    public static final String BASE_URL = "http://wildcritters.ws";
    public static final int BREADCRUMB_ICON = 2130837565;
    public static final String BREADCRUMB_NAME = "ws";
    public static final String DISPLAY_NAME = "ws";
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "wildcritters.ws";
    public static final int SEARCH_FORM_ID = 2130903055;

    /* renamed from: com.crumby.impl.wildcritter.WildCritterFragment.1 */
    class C12861 extends UniversalProducer {
        C12861() {
        }

        public String getHostUrl() {
            String tags = GalleryProducer.getQueryParameter(Uri.parse(super.getHostUrl()), super.getHostUrl(), "tags");
            String url = getBaseUrl() + "?tags=";
            if (tags == null && FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(WildCritterFragment.class)) {
                tags = "rating:safe";
            }
            if (!(tags == null || tags.equals(UnsupportedUrlFragment.DISPLAY_NAME))) {
                url = url + Uri.encode(tags);
            }
            return url + Uri.encode(GalleryViewer.getBlacklist());
        }

        protected String getScriptName() {
            return SankakuChanFragment.class.getSimpleName();
        }

        protected String getBaseUrl() {
            return WildCritterFragment.BASE_URL;
        }

        protected String getRegexForMatchingId() {
            return null;
        }
    }

    static {
        REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?(" + Pattern.quote(ROOT_NAME) + ")";
        REGEX_URL = REGEX_BASE + CrumbyGalleryFragment.REGEX_URL;
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
        return "q";
    }

    protected GalleryProducer createProducer() {
        return new C12861();
    }
}
