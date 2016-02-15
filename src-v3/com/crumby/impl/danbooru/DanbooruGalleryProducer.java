package com.crumby.impl.danbooru;

import android.net.Uri;
import android.os.Bundle;
import com.crumby.GalleryViewer;
import com.crumby.impl.boorus.BoorusFragment;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.JNH;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.fragment.producer.GalleryParser;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DanbooruGalleryProducer extends GalleryParser {
    public static final String API_URL = "http://danbooru.donmai.us/posts.json?";

    public void initialize(GalleryConsumer adapter, GalleryImage image, Bundle bundle) {
        super.initialize(adapter, image, bundle);
        setStartPage(1);
    }

    public URL convertUrlToApi(String url) {
        String apiUrlString = API_URL;
        if (url != null) {
            Uri uri = Uri.parse(url.toString());
            if (GalleryProducer.getQueryParameter(uri, url, "tags") != null && !GalleryProducer.getQueryParameter(uri, url, "tags").equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                apiUrlString = apiUrlString + "&tags=" + Uri.encode(GalleryProducer.getQueryParameter(uri, url, "tags"));
            } else if (FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(DanbooruGalleryFragment.class)) {
                apiUrlString = apiUrlString + BoorusFragment.SAFE_API_ROOT_SUFFIX;
            }
        }
        try {
            return new URL(apiUrlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected final String deriveUrl(JsonElement url, String path) {
        String href = UnsupportedUrlFragment.DISPLAY_NAME;
        if (!(url == null || url.toString() == null)) {
            href = url.toString().replace("\"", UnsupportedUrlFragment.DISPLAY_NAME);
        }
        return DanbooruGalleryFragment.BASE_URL + path + href;
    }

    public static String buildLink(String link, String title) {
        return "[" + title.replace("_", "\\_") + "]" + "(" + link.replace("_", "\\_") + ")";
    }

    String buildTagLinks(String tagString) {
        String tagLinks = "## Tag Links\n";
        for (String tag : tagString.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)) {
            tagLinks = tagLinks + buildLink("http://danbooru.donmai.us/posts?tags=" + tag, tag) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
        }
        return tagLinks;
    }

    private boolean checkForTags(String[] tags, String blacklistedTag) {
        if (tags != null && Arrays.binarySearch(tags, blacklistedTag) >= 0) {
            return true;
        }
        return false;
    }

    protected void filterOutUndesiredDanbooruImages(ArrayList<GalleryImage> galleryImages) {
        List<GalleryImage> remove = new ArrayList();
        if (GalleryViewer.BLACK_LISTED_TAGS != null) {
            Iterator it = galleryImages.iterator();
            while (it.hasNext()) {
                GalleryImage image = (GalleryImage) it.next();
                if (image.getTags() != null) {
                    DanbooruAttributes attributes = (DanbooruAttributes) image.attr();
                    Iterator i$ = GalleryViewer.BLACK_LISTED_TAGS.iterator();
                    while (i$.hasNext()) {
                        String blacklistedTag = (String) i$.next();
                        if (!checkForTags(image.getTags(), blacklistedTag) && !checkForTags(attributes.getArtistTags(), blacklistedTag) && !checkForTags(attributes.getCharacterTags(), blacklistedTag)) {
                            if (checkForTags(attributes.getCopyrightTags(), blacklistedTag)) {
                                remove.add(image);
                                break;
                            }
                        }
                        remove.add(image);
                        break;
                    }
                }
            }
            galleryImages.removeAll(remove);
        }
    }

    protected GalleryImage getGalleryImage(JsonObject galleryImageObj) {
        GalleryImage image = new GalleryImage(deriveUrl(galleryImageObj.get("preview_file_url"), UnsupportedUrlFragment.DISPLAY_NAME), deriveUrl(galleryImageObj.get("id"), "/posts/"), UnsupportedUrlFragment.DISPLAY_NAME, JNH.getAttributeInt(galleryImageObj, "image_width"), JNH.getAttributeInt(galleryImageObj, "image_height"));
        image.setImageUrl(deriveUrl(galleryImageObj.get("file_url"), UnsupportedUrlFragment.DISPLAY_NAME));
        image.setTags(galleryImageObj.get("tag_string").getAsString().split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR));
        image.setAttributes(new DanbooruAttributes(galleryImageObj.get("tag_string_character").getAsString().split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR), galleryImageObj.get("tag_string_artist").getAsString().split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR), galleryImageObj.get("tag_string_copyright").getAsString().split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)));
        return image;
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        ArrayList<GalleryImage> galleryImages = new ArrayList();
        try {
            JsonArray arr = JSON_PARSER.parse(GalleryProducer.fetchUrl(this.apiUrl.toString() + getSearchMark() + "&page=" + pageNumber)).getAsJsonArray();
            for (int i = 0; i < arr.size(); i++) {
                GalleryImage galleryImage = getGalleryImage(arr.get(i).getAsJsonObject());
                galleryImage.setPage(pageNumber);
                galleryImages.add(galleryImage);
            }
            filterOutUndesiredDanbooruImages(galleryImages);
            return galleryImages;
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    protected boolean fetchMetadata() throws IOException {
        setGalleryMetadata(null, GalleryProducer.getQueryParameter(Uri.parse(getHostUrl()), getHostUrl(), "tags"));
        return false;
    }
}
