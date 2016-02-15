package com.crumby.impl.crumby;

import android.os.Bundle;
import android.view.View;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import java.util.List;

public class CrumbyGalleryFragment extends GalleryGridFragment {
    public static final int BREADCRUMB_ICON = 2130837640;
    public static final String BREADCRUMB_NAME = "search results";
    public static final String REGEX_URL = ".*";
    public static final String URL_HOST = "http://bing.com/search?";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected GalleryProducer createProducer() {
        return new CrumbyProducer();
    }

    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbList) {
        String url = !getUrl().equals(UnsupportedUrlFragment.DISPLAY_NAME) ? "\"" + getUrl() + "\"" : "search";
        List<Breadcrumb> breadcrumbs = breadcrumbList.getChildren();
        ((Breadcrumb) breadcrumbs.get(0)).setText(url);
        ((Breadcrumb) breadcrumbs.get(0)).invalidate();
        super.setBreadcrumbs(breadcrumbList);
    }

    protected void initializeAdapter() {
        ((CrumbyProducer) getProducer()).setContext(getActivity().getApplicationContext());
        super.initializeAdapter();
    }

    public void applyGalleryImageChange(View view, GalleryImage image, int position) {
        postUrlChangeToBusWithProducer(image);
    }

    protected void cleanLinkUrl() {
    }
}
