package com.crumby.lib.events;

public class RemoveFragmentLinkFromDatabaseEvent {
    public String baseUrl;

    public RemoveFragmentLinkFromDatabaseEvent(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
