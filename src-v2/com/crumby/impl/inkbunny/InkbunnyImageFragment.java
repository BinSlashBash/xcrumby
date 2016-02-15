/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl.inkbunny;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.inkbunny.InkbunnyFragment;
import com.crumby.impl.inkbunny.InkbunnyProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;

public class InkbunnyImageFragment
extends BooruImageFragment {
    public static final String API_ROOT = "https://inkbunny.net/api_submissions.php?";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "inkbunny";
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_URL = InkbunnyFragment.REGEX_BASE + "/submissionview\\.php\\?id=([0-9]+)*";
        BREADCRUMB_PARENT_CLASS = InkbunnyFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new SingleGalleryProducer(){

            @Override
            protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> object) throws Exception {
                object = GalleryViewerFragment.matchIdFromUrl(InkbunnyImageFragment.REGEX_URL, this.getHostUrl());
                object = "https://inkbunny.net/api_submissions.php?" + "&sid=" + InkbunnyProducer.SESSION_ID + "&submission_ids=" + (String)object;
                InkbunnyProducer.jsonObjectToImage(JSON_PARSER.parse(.fetchUrl((String)object)).getAsJsonObject().get("submissions").getAsJsonArray().get(0).getAsJsonObject());
            }
        };
    }

    @Override
    protected String getTagUrl(String string2) {
        return "https://inkbunny.net/api_search.php?sid=" + InkbunnyProducer.SESSION_ID + "&text=" + Uri.encode((String)string2);
    }

}

