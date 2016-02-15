package com.crumby.impl;

public class RestrictedBooruImageProducer extends BooruImageProducer {
    public RestrictedBooruImageProducer(String baseUrl, String regexUrl) {
        super(baseUrl, regexUrl);
    }

    protected String getApiUrlForImage(String id) {
        return this.baseUrl + "/post.xml?tags=id:" + id;
    }
}
