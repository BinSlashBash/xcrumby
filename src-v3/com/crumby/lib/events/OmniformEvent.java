package com.crumby.lib.events;

public class OmniformEvent {
    public static final String LOGIN = "Login";
    public static final String SEARCH = "Search";
    public String eventName;

    public OmniformEvent(String eventName) {
        this.eventName = eventName;
    }
}
