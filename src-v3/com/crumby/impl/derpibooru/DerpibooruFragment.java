package com.crumby.impl.derpibooru;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.SettingAttributes;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.List;
import java.util.regex.Pattern;

public class DerpibooruFragment extends GalleryGridFragment {
    public static final int ACCOUNT_LAYOUT = 2130903057;
    public static final String ACCOUNT_TYPE = "derpibooru";
    public static final String API_ROOT = "https://derpibooru.org/images";
    public static final String BASE_URL = "https://derpibooru.org";
    public static final int BREADCRUMB_ICON = 2130837567;
    public static final String BREADCRUMB_NAME = "derpibooru";
    public static final String DISPLAY_NAME = "Derpibooru";
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "derpibooru.org";
    public static final int SEARCH_FORM_ID = 2130903058;
    public static final SettingAttributes SETTING_ATTRIBUTES;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_BASE = "(?:http://www.|https://www.|https://|http://|www.)?" + "(" + Pattern.quote(ROOT_NAME) + "|" + Pattern.quote("derpiboo.ru") + ")";
        REGEX_URL = REGEX_BASE + CrumbyGalleryFragment.REGEX_URL;
        SETTING_ATTRIBUTES = new SettingAttributes(INCLUDE_IN_HOME, SHOW_IN_SEARCH, SAFE_IN_TOP_LEVEL);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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
        return "sbq";
    }

    public static void addSearchQuery(String url, List<Breadcrumb> breadcrumbs) {
        String searchQuery = GalleryProducer.getQueryParameter(Uri.parse(url), url, "sbq");
        if (searchQuery != null) {
            ((Breadcrumb) breadcrumbs.get(0)).setBreadcrumbText("derpibooru: " + searchQuery.replace(":", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR));
        }
    }

    public void setImage(GalleryImage image) {
        super.setImage(image);
        if (image.getLinkUrl().contains("derpibooru.org?sbq=")) {
            image.setLinkUrl(image.getLinkUrl().replace("derpibooru.org?sbq=", "derpibooru.org/search?sbq="));
        }
    }

    public void setBreadcrumbs(List<Breadcrumb> breadcrumbs) {
        addSearchQuery(getUrl(), breadcrumbs);
        super.setBreadcrumbs(breadcrumbs);
    }

    public void applyGalleryImageChange(View view, GalleryImage image, int position) {
        super.applyGalleryImageChange(view, image, position);
    }

    protected GalleryProducer createProducer() {
        return new DerpibooruProducer();
    }
}
