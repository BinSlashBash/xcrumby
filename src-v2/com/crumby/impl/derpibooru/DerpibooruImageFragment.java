/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl.derpibooru;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.derpibooru.DerpibooruFragment;
import com.crumby.impl.derpibooru.DerpibooruProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;

public class DerpibooruImageFragment
extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "Derpibooru - MLP Imageboard";
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;

    static {
        REGEX_URL = DerpibooruFragment.REGEX_BASE + "/([0-9]+)" + "/?" + "\\??" + ".*";
        BREADCRUMB_PARENT_CLASS = DerpibooruFragment.class;
    }

    public static String[] getTags(JsonObject arrstring) {
        arrstring = arrstring.get("tags").getAsString().split(",");
        for (int i2 = 0; i2 < arrstring.length; ++i2) {
            arrstring[i2] = arrstring[i2].trim();
        }
        return arrstring;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new SingleGalleryProducer(){

            @Override
            protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> arrayList) throws Exception {
                Object object = GalleryViewerFragment.matchIdFromUrl(DerpibooruImageFragment.REGEX_URL, this.getHostUrl());
                new GalleryImage("");
                "https://derpibooru.org/" + (String)object;
                object = .fetchUrl("https://derpibooru.org/" + (String)object + ".json?comments=true");
                object = DerpibooruProducer.convertDerpJsonToDerpImage(JSON_PARSER.parse((String)object).getAsJsonObject());
                if (object == null) {
                    return;
                }
                arrayList.add((GalleryImage)object);
            }
        };
    }

    @Override
    protected void fillMetadataView() {
        super.fillMetadataView();
    }

    @Override
    protected String getTagUrl(String string2) {
        return "https://derpibooru.org/search?sbq=" + Uri.encode((String)string2);
    }

}

