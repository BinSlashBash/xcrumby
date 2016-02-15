/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.imgur;

import com.crumby.impl.imgur.ImgurProducer;
import com.crumby.impl.imgur.ImgurSubredditFragment;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;

public class ImgurSubredditProducer
extends ImgurProducer {
    String subredditId;

    @Override
    protected boolean fetchMetadata() throws IOException {
        JsonObject jsonObject = JSON_PARSER.parse(ImgurSubredditProducer.fetchUrl("http://www.reddit.com/r/" + this.subredditId + "/about.json")).getAsJsonObject().get("data").getAsJsonObject();
        String string2 = jsonObject.get("description").getAsString();
        this.setGalleryMetadata(jsonObject.get("title").getAsString(), string2.substring(0, Math.min(200, string2.length())));
        return false;
    }

    @Override
    protected String getApiUrl(int n2) {
        return "https://imgur-apiv3.p.mashape.com/3/gallery/r/" + this.subredditId + "/time/" + n2;
    }

    @Override
    public void postInitialize() {
        this.subredditId = ImgurSubredditFragment.matchIdFromUrl(this.getHostUrl());
    }
}

