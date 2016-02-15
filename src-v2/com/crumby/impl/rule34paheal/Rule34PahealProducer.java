/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.rule34paheal;

import com.crumby.impl.rule34paheal.Rule34PahealFragment;
import com.crumby.lib.universal.UniversalProducer;

class Rule34PahealProducer
extends UniversalProducer {
    Rule34PahealProducer() {
    }

    @Override
    protected String getBaseUrl() {
        return "http://rule34.paheal.net";
    }

    @Override
    public String getHostUrl() {
        return Rule34PahealFragment.getDisplayUrl(super.getHostUrl());
    }

    @Override
    protected String getRegexForMatchingId() {
        return null;
    }

    @Override
    protected String getScriptName() {
        return Rule34PahealFragment.class.getSimpleName();
    }
}

