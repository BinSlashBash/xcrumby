package com.crumby.impl.idolchan;

import com.crumby.lib.universal.UniversalImageProducer;

public class SankakuChanImageProducer extends UniversalImageProducer {
    protected String getScriptName() {
        return SankakuChanImageFragment.class.getSimpleName();
    }

    protected String getBaseUrl() {
        return SankakuChanFragment.BASE_URL;
    }

    protected String getRegexForMatchingId() {
        return SankakuChanImageFragment.REGEX_URL;
    }
}
