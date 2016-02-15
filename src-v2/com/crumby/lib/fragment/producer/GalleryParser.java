/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.fragment.producer;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import java.net.URL;

public abstract class GalleryParser
extends GalleryProducer {
    protected URL apiUrl;

    public abstract URL convertUrlToApi(String var1);

    protected String getSearchMark() {
        if (this.apiUrl.toString().indexOf("?") == -1) {
            return "?";
        }
        return "";
    }

    @Override
    public boolean requestStartFetch() {
        if (this.apiUrl == null) {
            this.apiUrl = this.convertUrlToApi(this.getHostImage().getLinkUrl());
        }
        return super.requestStartFetch();
    }
}

