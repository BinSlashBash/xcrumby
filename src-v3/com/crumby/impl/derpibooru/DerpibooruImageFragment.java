package com.crumby.impl.derpibooru;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import com.google.gson.JsonObject;
import java.util.ArrayList;

public class DerpibooruImageFragment extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "Derpibooru - MLP Imageboard";
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;

    /* renamed from: com.crumby.impl.derpibooru.DerpibooruImageFragment.1 */
    class C12731 extends SingleGalleryProducer {
        C12731() {
        }

        protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> galleryImages) throws Exception {
            String id = GalleryViewerFragment.matchIdFromUrl(DerpibooruImageFragment.REGEX_URL, getHostUrl());
            GalleryImage pony = new GalleryImage(UnsupportedUrlFragment.DISPLAY_NAME);
            String url_ID = "https://derpibooru.org/" + id;
            GalleryImage derpImage = DerpibooruProducer.convertDerpJsonToDerpImage(JSON_PARSER.parse(GalleryProducer.fetchUrl("https://derpibooru.org/" + id + ".json?comments=true")).getAsJsonObject());
            if (derpImage != null) {
                galleryImages.add(derpImage);
            }
        }
    }

    static {
        REGEX_URL = DerpibooruFragment.REGEX_BASE + "/([0-9]+)" + "/?" + "\\??" + CrumbyGalleryFragment.REGEX_URL;
        BREADCRUMB_PARENT_CLASS = DerpibooruFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new C12731();
    }

    protected void fillMetadataView() {
        super.fillMetadataView();
    }

    public static String[] getTags(JsonObject ponyObj) {
        String[] tags = ponyObj.get("tags").getAsString().split(",");
        for (int i = 0; i < tags.length; i++) {
            tags[i] = tags[i].trim();
        }
        return tags;
    }

    protected String getTagUrl(String tag) {
        return "https://derpibooru.org/search?sbq=" + Uri.encode(tag);
    }
}
