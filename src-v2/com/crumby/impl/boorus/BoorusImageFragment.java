/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 */
package com.crumby.impl.boorus;

import android.net.Uri;
import android.os.Bundle;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.boorus.BoorusFragment;
import com.crumby.impl.gelbooru.GelbooruImageProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalImageProducer;

public class BoorusImageFragment
extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "Gelbooru";
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;
    private boolean mustUseUniversal;

    static {
        REGEX_URL = BoorusFragment.REGEX_BASE + "/index\\.php.*&id=([0-9]+)";
        BREADCRUMB_PARENT_CLASS = BoorusFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        final String string2 = BoorusFragment.getBaseUrl(this.getUrl());
        if (!this.mustUseUniversal) {
            return new GelbooruImageProducer(string2 + "/", REGEX_URL, string2 + "/index.php?page=dapi&s=post&q=index");
        }
        return new UniversalImageProducer(){

            @Override
            protected String getBaseUrl() {
                return string2;
            }

            @Override
            protected String getRegexForMatchingId() {
                return GalleryViewerFragment.matchIdFromUrl(BoorusImageFragment.REGEX_URL, BoorusImageFragment.this.getUrl());
            }

            @Override
            protected String getScriptName() {
                return BoorusImageFragment.class.getSimpleName();
            }
        };
    }

    @Override
    protected String getTagUrl(String string2) {
        return BoorusFragment.getBaseUrl(this.getUrl()) + "/index.php?page=post&s=list&tags=" + Uri.encode((String)string2);
    }

    @Override
    public void showError(Exception exception) {
        if (!this.mustUseUniversal) {
            this.mustUseUniversal = true;
            this.reloading = true;
            this.producer = this.createProducer();
            this.producer.initialize(this, this.getImage(), null, true);
            return;
        }
        super.showError(exception);
    }

}

