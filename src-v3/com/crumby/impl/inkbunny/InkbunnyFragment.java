package com.crumby.impl.inkbunny;

import android.view.View;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.SettingAttributes;
import java.util.regex.Pattern;

public class InkbunnyFragment extends GalleryGridFragment {
    public static final String API_ROOT = "https://inkbunny.net/api_";
    public static final String BASE_URL = "https://inkbunny.net";
    public static final int BREADCRUMB_ICON = 2130837659;
    public static final String BREADCRUMB_NAME = "inkbunny";
    public static final String DISPLAY_NAME = "Inkbunny";
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "inkbunny.net";
    public static final SettingAttributes SETTING_ATTRIBUTES;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?(" + Pattern.quote(ROOT_NAME) + ")";
        REGEX_URL = REGEX_BASE + CrumbyGalleryFragment.REGEX_URL;
        SETTING_ATTRIBUTES = new SettingAttributes(INCLUDE_IN_HOME_FALSE);
    }

    public String getSearchPrefix() {
        return BREADCRUMB_NAME;
    }

    public String getSearchArgumentName() {
        return "keywords";
    }

    public void applyGalleryImageChange(View view, GalleryImage image, int position) {
        super.applyGalleryImageChange(view, image, position);
    }

    protected GalleryProducer createProducer() {
        return new InkbunnyProducer();
    }
}
