/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl;

import com.crumby.impl.BooruImageProducer;

public class RestrictedBooruImageProducer
extends BooruImageProducer {
    public RestrictedBooruImageProducer(String string2, String string3) {
        super(string2, string3);
    }

    @Override
    protected String getApiUrlForImage(String string2) {
        return this.baseUrl + "/post.xml?tags=id:" + string2;
    }
}

