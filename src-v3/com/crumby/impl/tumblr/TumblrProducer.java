package com.crumby.impl.tumblr;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.universal.UniversalProducer;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;

class TumblrProducer extends UniversalProducer {
    String timestamp;

    TumblrProducer() {
        this.timestamp = "null";
    }

    public String getHostUrl() {
        return TumblrFragment.getDisplayUrl(super.getHostUrl());
    }

    protected String getExtraScript() {
        return "var tumblrTimeStamp = " + this.timestamp + ";";
    }

    protected ArrayList<GalleryImage> getImagesFromJson(JsonNode node) throws Exception {
        try {
            this.timestamp = node.get("tumblrTimeStamp").asText();
            return super.getImagesFromJson(node);
        } catch (NullPointerException e) {
            return new ArrayList();
        }
    }

    protected String getScriptName() {
        return TumblrFragment.class.getSimpleName();
    }

    protected String getBaseUrl() {
        return "http://api.tumblr.com";
    }

    protected String getRegexForMatchingId() {
        return TumblrFragment.REGEX_URL;
    }
}
