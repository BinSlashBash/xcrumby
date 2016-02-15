package com.crumby.impl.tbib;

import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.impl.gelbooru.GelbooruProducer;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import java.util.regex.Pattern;

public class TbibFragment extends GalleryGridFragment {
    public static final String API_ROOT = "http://tbib.org/index.php?page=dapi&s=post&q=index";
    public static final String BASE_URL = "http://tbib.org";
    public static final int BREADCRUMB_ICON = 2130837692;
    public static final String BREADCRUMB_NAME = "tbib";
    public static final String DISPLAY_NAME = "tbib";
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "tbib.org";
    public static final String SAFE_API_ROOT = "http://tbib.org/index.php?page=dapi&s=post&q=index&tags=rating%3Asafe";
    public static final int SEARCH_FORM_ID = 2130903055;
    public static final SettingAttributes SETTING_ATTRIBUTES;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?(" + Pattern.quote(ROOT_NAME) + ")";
        REGEX_URL = REGEX_BASE + CrumbyGalleryFragment.REGEX_URL;
        SETTING_ATTRIBUTES = new SettingAttributes(INCLUDE_IN_HOME_FALSE, SHOW_IN_SEARCH, SAFE_IN_TOP_LEVEL);
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
        return new GelbooruProducer("http://tbib.org/", API_ROOT, SAFE_API_ROOT, TbibFragment.class);
    }
}
