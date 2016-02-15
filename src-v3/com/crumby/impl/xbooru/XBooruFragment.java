package com.crumby.impl.xbooru;

import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.impl.gelbooru.GelbooruProducer;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class XBooruFragment extends GalleryGridFragment {
    public static final String BASE_URL = "https://xbooru.com";
    public static final int BREADCRUMB_ICON = 2130837712;
    public static final String BREADCRUMB_NAME = "xbooru";
    public static final String DISPLAY_NAME = "xbooru";
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "xbooru.com";
    public static final int SEARCH_FORM_ID = 2130903055;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote(ROOT_NAME);
        REGEX_URL = REGEX_BASE + "/?" + "[^/]*";
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
        return "tags";
    }

    protected GalleryProducer createProducer() {
        return new GelbooruProducer("https://xbooru.com/", "https://xbooru.com/index.php?page=dapi&s=post&q=index", "https://xbooru.com/index.php?page=dapi&s=post&q=index", null);
    }
}
