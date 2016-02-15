/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.derpibooru;

import com.crumby.impl.derpibooru.DerpibooruProducer;
import com.google.gson.JsonArray;

public abstract class DerpibooruLoggedInUserProducer
extends DerpibooruProducer {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected JsonArray getDerpImages(int n2) throws Exception {
        String string2;
        if (n2 == 0) {
            string2 = "";
            do {
                return this.getDerpImages("https://derpibooru.org" + this.getSuffix() + ".json" + string2, "images");
                break;
            } while (true);
        }
        string2 = "?page=" + (n2 + 1);
        return this.getDerpImages("https://derpibooru.org" + this.getSuffix() + ".json" + string2, "images");
    }

    protected abstract String getSuffix();
}

