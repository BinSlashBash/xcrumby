package com.crumby.lib.events;

public class ReloadFragmentEvent {
    public boolean hardRefresh;

    public ReloadFragmentEvent(boolean hardRefresh) {
        this.hardRefresh = hardRefresh;
    }
}
