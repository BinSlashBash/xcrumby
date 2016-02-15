package com.crumby.impl.rule34paheal;

import com.crumby.lib.universal.UniversalProducer;

class Rule34PahealProducer extends UniversalProducer {
    Rule34PahealProducer() {
    }

    public String getHostUrl() {
        return Rule34PahealFragment.getDisplayUrl(super.getHostUrl());
    }

    protected String getScriptName() {
        return Rule34PahealFragment.class.getSimpleName();
    }

    protected String getBaseUrl() {
        return Rule34PahealFragment.BASE_URL;
    }

    protected String getRegexForMatchingId() {
        return null;
    }
}
