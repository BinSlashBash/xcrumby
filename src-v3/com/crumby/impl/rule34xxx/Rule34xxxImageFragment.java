package com.crumby.impl.rule34xxx;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.gelbooru.GelbooruImageProducer;
import com.crumby.lib.fragment.producer.GalleryProducer;

public class Rule34xxxImageFragment extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;

    static {
        REGEX_URL = Rule34xxxFragment.REGEX_BASE + "/index\\.php.*&id=([0-9]+)";
        BREADCRUMB_PARENT_CLASS = Rule34xxxFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new GelbooruImageProducer(Rule34xxxFragment.BASE_URL, REGEX_URL, "https://rule34.xxx//index.php?page=dapi&s=post&q=index");
    }

    protected String getTagUrl(String tag) {
        return "https://rule34.xxx/index.php?page=post&s=list&tags=" + Uri.encode(tag);
    }
}
