package com.crumby.impl.boorus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.impl.device.DeviceFragment;
import com.crumby.impl.gelbooru.GelbooruProducer;
import com.crumby.lib.FormSearchHandler;
import com.crumby.lib.SearchForm;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.universal.UniversalProducer;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class BoorusFragment extends GalleryGridFragment {
    public static final String API_ROOT_SUFFIX = "/index.php?page=dapi&s=post&q=index";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837565;
    public static final String BREADCRUMB_NAME = "BOORU";
    public static final boolean DEFAULT_TO_HIDDEN = true;
    public static final FormSearchHandler FORM_SEARCH_HANDLER;
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "booru.org";
    public static final String SAFE_API_ROOT_SUFFIX = "&tags=rating%3Asafe";
    public static final int SEARCH_FORM_ID = 2130903055;
    public static final boolean SUGGESTABLE = true;
    private boolean mustUseUniversal;

    /* renamed from: com.crumby.impl.boorus.BoorusFragment.1 */
    static class C07311 implements FormSearchHandler {
        C07311() {
        }

        public String getUrlForSearch(String url, SearchForm searchForm) {
            return BoorusFragment.getBaseUrl(url) + "/index.php?page=post&s=list&" + searchForm.encodeFormData();
        }

        public String getTitle(FragmentIndex index, String query) {
            return "BOORUS " + BoorusFragment.getSubdomain(query).toUpperCase();
        }

        public int getIcon(FragmentIndex index, String query) {
            return 0;
        }
    }

    /* renamed from: com.crumby.impl.boorus.BoorusFragment.2 */
    class C12722 extends UniversalProducer {
        C12722() {
        }

        protected String getScriptName() {
            return BoorusFragment.class.getSimpleName();
        }

        protected String getBaseUrl() {
            return BoorusFragment.getBaseUrl(BoorusFragment.this.getUrl());
        }

        protected String getRegexForMatchingId() {
            return null;
        }
    }

    static {
        REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + GalleryViewerFragment.captureMinimumLength("[a-zA-Z0-9]", 4) + "\\." + Pattern.quote(ROOT_NAME);
        REGEX_URL = REGEX_BASE + CrumbyGalleryFragment.REGEX_URL;
        FORM_SEARCH_HANDLER = new C07311();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.createFragmentView(inflater, container, savedInstanceState);
    }

    public String getSearchPrefix() {
        return getSubdomain(getUrl());
    }

    public String getSearchArgumentName() {
        return "tags";
    }

    protected int getHeaderLayout() {
        return C0065R.layout.omniform_container;
    }

    protected void setupHeaderLayout(ViewGroup header) {
        ((OmniformContainer) header).showAsInGrid(getImage().getLinkUrl());
    }

    private static String getSubdomain(String url) {
        String id = GalleryViewerFragment.matchIdFromUrl(REGEX_URL, url);
        if (id == null) {
            return "invalid";
        }
        return id;
    }

    public static String getBaseUrl(String url) {
        return "http://" + getSubdomain(url) + ".booru.org";
    }

    public void showError(DisplayError error, String reason, String url) {
        if (!error.equals(DisplayError.EMPTY_GALLERY) || this.mustUseUniversal) {
            super.showError(error, reason, url);
            return;
        }
        this.mustUseUniversal = SUGGESTABLE;
        this.producer = createProducer();
        this.adapter.initialize(this);
        this.adapter.startFetching();
    }

    protected GalleryProducer createProducer() {
        String baseUrl = getBaseUrl(getUrl());
        if (!this.mustUseUniversal) {
            return new GelbooruProducer(baseUrl + DeviceFragment.REGEX_BASE, baseUrl + API_ROOT_SUFFIX, baseUrl + API_ROOT_SUFFIX + SAFE_API_ROOT_SUFFIX, null);
        }
        CrDb.m0d("boorus fragment", "spinning up universal producer");
        return new C12722();
    }
}
