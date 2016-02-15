/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.furaffinity;

import com.crumby.impl.furaffinity.FurAffinityFragment;
import com.crumby.lib.universal.UniversalProducer;

public class FurAffinityProducer
extends UniversalProducer {
    @Override
    protected String getBaseUrl() {
        return "http://www.furaffinity.net";
    }

    @Override
    protected String getRegexForMatchingId() {
        return null;
    }

    @Override
    public String getScriptName() {
        return FurAffinityFragment.class.getSimpleName();
    }
}

