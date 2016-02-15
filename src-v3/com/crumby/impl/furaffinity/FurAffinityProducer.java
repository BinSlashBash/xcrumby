package com.crumby.impl.furaffinity;

import com.crumby.lib.universal.UniversalProducer;

public class FurAffinityProducer extends UniversalProducer {
    public String getScriptName() {
        return FurAffinityFragment.class.getSimpleName();
    }

    protected String getBaseUrl() {
        return "http://www.furaffinity.net";
    }

    protected String getRegexForMatchingId() {
        return null;
    }
}
