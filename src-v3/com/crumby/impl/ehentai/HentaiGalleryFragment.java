package com.crumby.impl.ehentai;

import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class HentaiGalleryFragment extends GalleryGridFragment {
    public static final String BASE_URL = "http://g.e-hentai.org";
    public static final String BREADCRUMB_NAME = "e-hentai";
    public static final String DISPLAY_NAME = "E-hentai";
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "g.e-hentai.org";
    public static final int SEARCH_FORM_ID = 2130903065;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote(ROOT_NAME);
        REGEX_URL = REGEX_BASE + CrumbyGalleryFragment.REGEX_URL;
    }

    protected GalleryProducer createProducer() {
        return new HentaiGalleryProducer();
    }

    protected int getHeaderLayout() {
        return C0065R.layout.omniform_container;
    }

    protected void setupHeaderLayout(ViewGroup header) {
        ((OmniformContainer) header).showAsInGrid(getImage().getLinkUrl());
    }

    public String getSearchPrefix() {
        return BREADCRUMB_NAME;
    }

    public String getSearchArgumentName() {
        return "f_search";
    }
}
