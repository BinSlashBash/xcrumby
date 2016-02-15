/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.tumblr;

import com.crumby.impl.tumblr.TumblrFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.universal.UniversalProducer;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;

class TumblrProducer
extends UniversalProducer {
    String timestamp = "null";

    TumblrProducer() {
    }

    @Override
    protected String getBaseUrl() {
        return "http://api.tumblr.com";
    }

    @Override
    protected String getExtraScript() {
        return "var tumblrTimeStamp = " + this.timestamp + ";";
    }

    @Override
    public String getHostUrl() {
        return TumblrFragment.getDisplayUrl(super.getHostUrl());
    }

    @Override
    protected ArrayList<GalleryImage> getImagesFromJson(JsonNode jsonNode) throws Exception {
        try {
            this.timestamp = jsonNode.get("tumblrTimeStamp").asText();
        }
        catch (NullPointerException var1_2) {
            return new ArrayList<GalleryImage>();
        }
        return super.getImagesFromJson(jsonNode);
    }

    @Override
    protected String getRegexForMatchingId() {
        return TumblrFragment.REGEX_URL;
    }

    @Override
    protected String getScriptName() {
        return TumblrFragment.class.getSimpleName();
    }
}

