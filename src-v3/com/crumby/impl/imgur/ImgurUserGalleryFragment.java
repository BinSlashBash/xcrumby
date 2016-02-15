package com.crumby.impl.imgur;

import android.view.View;
import com.crumby.impl.furaffinity.FurAffinityUserFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import java.util.regex.Pattern;

public class ImgurUserGalleryFragment extends GalleryGridFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837632;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;
    public static final String REGEX_URL_1;
    public static final String REGEX_URL_2;

    static {
        REGEX_URL_1 = GalleryViewerFragment.captureMinimumLength("[a-zA-Z0-9]", 4) + "\\." + Pattern.quote(ImgurFragment.ROOT_NAME) + "(?:/.*)?";
        REGEX_URL_2 = Pattern.quote(ImgurFragment.ROOT_NAME) + FurAffinityUserFragment.SUFFIX + CAPTURE_ALPHANUMERIC_REPEATING + "/?";
        REGEX_URL = "(?:http://www.|https://www.|https://|http://|www.)?" + GalleryViewerFragment.matchOne(REGEX_URL_1, REGEX_URL_2);
        BREADCRUMB_PARENT_CLASS = ImgurFragment.class;
    }

    public void applyGalleryImageChange(View view, GalleryImage image, int position) {
        postUrlChangeToBusWithProducer(image);
    }

    protected GalleryProducer createProducer() {
        return new ImgurUserGalleryProducer();
    }

    public static String matchIdFromUrl(String url) {
        return GalleryViewerFragment.matchIdFromUrl(REGEX_URL, url);
    }
}
