package com.crumby.lib.events;

public class InsertFragmentLinkIntoDatabaseEvent {
    public String thumbnail;
    public String url;

    public InsertFragmentLinkIntoDatabaseEvent(String url, String thumbnail) {
        this.url = url;
        this.thumbnail = thumbnail;
    }
}
