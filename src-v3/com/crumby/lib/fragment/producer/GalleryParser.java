package com.crumby.lib.fragment.producer;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import java.net.URL;

public abstract class GalleryParser extends GalleryProducer {
    protected URL apiUrl;

    public abstract URL convertUrlToApi(String str);

    protected String getSearchMark() {
        return this.apiUrl.toString().indexOf("?") == -1 ? "?" : UnsupportedUrlFragment.DISPLAY_NAME;
    }

    public boolean requestStartFetch() {
        if (this.apiUrl == null) {
            this.apiUrl = convertUrlToApi(getHostImage().getLinkUrl());
        }
        return super.requestStartFetch();
    }
}
