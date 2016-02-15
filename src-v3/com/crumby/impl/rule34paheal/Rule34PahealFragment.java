package com.crumby.impl.rule34paheal;

import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.impl.device.DeviceFragment;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class Rule34PahealFragment extends GalleryGridFragment {
    public static final String BASE_URL = "http://rule34.paheal.net";
    public static final int BREADCRUMB_ICON = 2130837682;
    public static final String BREADCRUMB_NAME = "rule34 paheal";
    public static final String DISPLAY_NAME = "rule34 paheal";
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "rule34.paheal.net";
    public static final int SEARCH_FORM_ID = 2130903103;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + Pattern.quote(ROOT_NAME);
        REGEX_URL = REGEX_BASE + "/?" + CrumbyGalleryFragment.REGEX_URL;
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

    public String getDisplayUrl() {
        return getDisplayUrl(getUrl());
    }

    public static String getDisplayUrl(String url) {
        String suffix = "/post/list";
        if (url.contains("?tags=")) {
            return url.replace("?tags=", suffix + DeviceFragment.REGEX_BASE);
        }
        if (url.contains(suffix)) {
            return url;
        }
        return url + suffix;
    }

    protected GalleryProducer createProducer() {
        return new Rule34PahealProducer();
    }
}
