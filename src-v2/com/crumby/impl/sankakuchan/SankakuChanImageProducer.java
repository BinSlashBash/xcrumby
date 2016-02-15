/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.sankakuchan;

import com.crumby.impl.sankakuchan.SankakuChanImageFragment;
import com.crumby.lib.universal.UniversalImageProducer;

public class SankakuChanImageProducer
extends UniversalImageProducer {
    @Override
    protected String getBaseUrl() {
        return "https://chan.sankakucomplex.com";
    }

    @Override
    protected String getRegexForMatchingId() {
        return SankakuChanImageFragment.REGEX_URL;
    }

    @Override
    protected String getScriptName() {
        return SankakuChanImageFragment.class.getSimpleName();
    }
}

