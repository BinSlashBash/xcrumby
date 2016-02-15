/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.Bundle
 */
package com.crumby.impl.danbooru;

import android.net.Uri;
import android.os.Bundle;
import com.crumby.GalleryViewer;
import com.crumby.impl.danbooru.DanbooruAttributes;
import com.crumby.impl.danbooru.DanbooruGalleryFragment;
import com.crumby.lib.ExtraAttributes;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.JNH;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryParser;
import com.crumby.lib.router.FragmentRouter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

public class DanbooruGalleryProducer
extends GalleryParser {
    public static final String API_URL = "http://danbooru.donmai.us/posts.json?";

    public static String buildLink(String string2, String string3) {
        string2 = string2.replace("_", "\\_");
        string3 = string3.replace("_", "\\_");
        return "[" + string3 + "]" + "(" + string2 + ")";
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean checkForTags(String[] arrstring, String string2) {
        if (arrstring == null || Arrays.binarySearch(arrstring, string2) < 0) {
            return false;
        }
        return true;
    }

    String buildTagLinks(String string2) {
        String[] arrstring = string2.split(" ");
        string2 = "## Tag Links\n";
        for (String string3 : arrstring) {
            string2 = string2 + DanbooruGalleryProducer.buildLink(new StringBuilder().append("http://danbooru.donmai.us/posts?tags=").append(string3).toString(), string3) + " ";
        }
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public URL convertUrlToApi(String object) {
        String string2;
        String string3 = string2 = "http://danbooru.donmai.us/posts.json?";
        if (object != null) {
            string3 = Uri.parse((String)object.toString());
            if (DanbooruGalleryProducer.getQueryParameter((Uri)string3, (String)object, "tags") != null && !DanbooruGalleryProducer.getQueryParameter((Uri)string3, (String)object, "tags").equals("")) {
                string3 = "http://danbooru.donmai.us/posts.json?" + "&tags=" + Uri.encode((String)DanbooruGalleryProducer.getQueryParameter((Uri)string3, (String)object, "tags"));
            } else {
                string3 = string2;
                if (FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(DanbooruGalleryFragment.class)) {
                    string3 = "http://danbooru.donmai.us/posts.json?" + "&tags=rating%3Asafe";
                }
            }
        }
        try {
            return new URL(string3);
        }
        catch (MalformedURLException var1_2) {
            var1_2.printStackTrace();
            return null;
        }
    }

    protected final String deriveUrl(JsonElement jsonElement, String string2) {
        String string3;
        String string4 = string3 = "";
        if (jsonElement != null) {
            string4 = string3;
            if (jsonElement.toString() != null) {
                string4 = jsonElement.toString().replace("\"", "");
            }
        }
        return "http://danbooru.donmai.us" + string2 + string4;
    }

    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        Object object = DanbooruGalleryProducer.fetchUrl(this.apiUrl.toString() + this.getSearchMark() + "&page=" + n2);
        object = JSON_PARSER.parse((String)object).getAsJsonArray();
        int n3 = 0;
        do {
            if (n3 >= object.size()) break;
            GalleryImage galleryImage = this.getGalleryImage(object.get(n3).getAsJsonObject());
            galleryImage.setPage(n2);
            arrayList.add(galleryImage);
            ++n3;
            continue;
            break;
        } while (true);
        try {
            this.filterOutUndesiredDanbooruImages(arrayList);
            return arrayList;
        }
        catch (JsonSyntaxException var3_3) {
            return null;
        }
    }

    @Override
    protected boolean fetchMetadata() throws IOException {
        this.setGalleryMetadata(null, DanbooruGalleryProducer.getQueryParameter(Uri.parse((String)this.getHostUrl()), this.getHostUrl(), "tags"));
        return false;
    }

    protected void filterOutUndesiredDanbooruImages(ArrayList<GalleryImage> arrayList) {
        ArrayList<GalleryImage> arrayList2 = new ArrayList<GalleryImage>();
        if (GalleryViewer.BLACK_LISTED_TAGS == null) {
            return;
        }
        block0 : for (GalleryImage galleryImage : arrayList) {
            if (galleryImage.getTags() == null) continue;
            DanbooruAttributes danbooruAttributes = (DanbooruAttributes)galleryImage.attr();
            for (String string2 : GalleryViewer.BLACK_LISTED_TAGS) {
                if (!this.checkForTags(galleryImage.getTags(), string2) && !this.checkForTags(danbooruAttributes.getArtistTags(), string2) && !this.checkForTags(danbooruAttributes.getCharacterTags(), string2) && !this.checkForTags(danbooruAttributes.getCopyrightTags(), string2)) continue;
                arrayList2.add(galleryImage);
                continue block0;
            }
        }
        arrayList.removeAll(arrayList2);
    }

    protected GalleryImage getGalleryImage(JsonObject jsonObject) {
        GalleryImage galleryImage = new GalleryImage(this.deriveUrl(jsonObject.get("preview_file_url"), ""), this.deriveUrl(jsonObject.get("id"), "/posts/"), "", JNH.getAttributeInt(jsonObject, "image_width"), JNH.getAttributeInt(jsonObject, "image_height"));
        galleryImage.setImageUrl(this.deriveUrl(jsonObject.get("file_url"), ""));
        galleryImage.setTags(jsonObject.get("tag_string").getAsString().split(" "));
        galleryImage.setAttributes(new DanbooruAttributes(jsonObject.get("tag_string_character").getAsString().split(" "), jsonObject.get("tag_string_artist").getAsString().split(" "), jsonObject.get("tag_string_copyright").getAsString().split(" ")));
        return galleryImage;
    }

    @Override
    public void initialize(GalleryConsumer galleryConsumer, GalleryImage galleryImage, Bundle bundle) {
        super.initialize(galleryConsumer, galleryImage, bundle);
        this.setStartPage(1);
    }
}

