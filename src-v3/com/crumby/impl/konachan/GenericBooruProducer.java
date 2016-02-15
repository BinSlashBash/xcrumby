package com.crumby.impl.konachan;

import android.net.Uri;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.nodes.Document;

public class GenericBooruProducer extends GalleryProducer {
    private String apiRoot;
    private String baseUrl;
    private Class topLevel;

    public GenericBooruProducer(String apiRoot, String baseUrl, Class topLevel) {
        this.apiRoot = apiRoot;
        this.baseUrl = baseUrl;
        this.topLevel = topLevel;
    }

    public String getGalleryUrl(int pageNumber) throws Exception {
        String url = this.apiRoot;
        Uri uri = Uri.parse(getHostUrl());
        if (pageNumber != 0) {
            url = url + "&page=" + (pageNumber + 1);
        }
        url = url + "&tags=" + Uri.encode(GalleryViewer.getBlacklist());
        String tags = GalleryProducer.getQueryParameter(uri, getHostUrl(), "tags");
        if (tags == null && FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(this.topLevel)) {
            tags = "rating:safe ";
        }
        if (tags == null || tags.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
            return url;
        }
        return url + Uri.encode(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + tags);
    }

    public static GalleryImage convertObjectToImage(String baseUrl, JsonObject obj) {
        String thumbnailUrl = obj.get("preview_url").toString();
        String fileUrl = obj.get("file_url").toString();
        GalleryImage image = new GalleryImage(thumbnailUrl.substring(1, thumbnailUrl.length() - 1), baseUrl + "/post/show/" + obj.get("id").toString(), null, obj.get("jpeg_width").getAsInt(), obj.get("jpeg_height").getAsInt());
        image.setImageUrl(fileUrl.substring(1, fileUrl.length() - 1));
        image.setTags(obj.get("tags").getAsString().split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR));
        return image;
    }

    public GalleryImage convertImagePageToGalleryImage(Document doc) {
        GalleryImage image = new GalleryImage();
        image.setImageUrl(doc.getElementById("image").attr("src"));
        image.setLinkUrl(getHostUrl());
        return null;
    }

    public static void convertArrayToGalleryImages(String baseUrl, String json, ArrayList<GalleryImage> images) {
        Iterator i$ = JSON_PARSER.parse(json).getAsJsonArray().iterator();
        while (i$.hasNext()) {
            GalleryImage image = convertObjectToImage(baseUrl, ((JsonElement) i$.next()).getAsJsonObject());
            if (image != null) {
                images.add(image);
            }
        }
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        ArrayList<GalleryImage> images = new ArrayList();
        convertArrayToGalleryImages(this.baseUrl, GalleryProducer.fetchUrl(getGalleryUrl(pageNumber)), images);
        return images;
    }
}
