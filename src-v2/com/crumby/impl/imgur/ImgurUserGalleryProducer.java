/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.imgur;

import com.crumby.impl.imgur.ImgurProducer;
import com.crumby.impl.imgur.ImgurUserGalleryFragment;
import com.crumby.lib.GalleryImage;
import com.google.gson.JsonObject;

public class ImgurUserGalleryProducer
extends ImgurProducer {
    private String userId;

    @Override
    protected String getApiUrl(int n2) {
        return "https://imgur-apiv3.p.mashape.com/3/account/" + this.userId + "/albums/" + n2;
    }

    @Override
    protected String getImgurLink(boolean bl2, String string2, String string3) {
        return string3;
    }

    @Override
    protected GalleryImage parseImgurImage(JsonObject jsonObject, boolean bl2) {
        return super.parseImgurImage(jsonObject, true);
    }

    @Override
    public void postInitialize() {
        this.userId = ImgurUserGalleryFragment.matchIdFromUrl(this.getHostUrl());
    }
}

