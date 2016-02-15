package com.crumby.impl.imgur;

import android.net.Uri;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.JNH;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.google.android.gms.plus.PlusShare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import uk.co.senab.photoview.IPhotoView;

public class ImgurProducer extends GalleryProducer {
    public static final String API_CREDENTIALS = "Client-ID ac562464e4b98f8";
    public static final String API_URL = "https://imgur-apiv3.p.mashape.com/3/";
    public static final String MAIN_GALLERY = "gallery/hot/viral/";
    public static final String MASHAPE_AUTH = "F7x7cTikVsmshJJ4wubKUnkmJkIyp19IUJKjsnJegrKYFdu0OP";
    public static final String THUMBNAIL_HOST = "http://i.imgur.com/";

    public void postInitialize() {
        getHostImage().setTitle("Imgur - ");
    }

    protected String getApiUrl(int pageNumber) {
        String query = GalleryProducer.getQueryParameter(Uri.parse(getHostUrl()), getHostUrl(), "q");
        if (query == null || query.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
            return "https://imgur-apiv3.p.mashape.com/3/gallery/hot/viral/" + pageNumber + ".json";
        }
        return "https://imgur-apiv3.p.mashape.com/3/gallery/search/" + pageNumber + "?q=" + Uri.encode(query);
    }

    protected String fetchImgurUrl(int pageNumber) throws Exception {
        return GalleryProducer.fetchUrl(getApiUrl(pageNumber), API_CREDENTIALS, MASHAPE_AUTH);
    }

    protected JsonArray sliceImages(JsonArray images, int pageNumber) throws IOException {
        JsonArray newImages = new JsonArray();
        int offset = pageNumber * IPhotoView.DEFAULT_ZOOM_DURATION;
        if (pageNumber <= 0) {
            offset = 0;
        }
        int size = Math.min(offset + IPhotoView.DEFAULT_ZOOM_DURATION, images.size());
        for (int i = offset; i < size; i++) {
            newImages.add(images.get(i));
        }
        return newImages;
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        JsonArray imgurImages = getImagesFromJsonElement(JSON_PARSER.parse(fetchImgurUrl(pageNumber)));
        if (imgurImages != null && !imgurImages.isJsonNull()) {
            return parseImgurImages(imgurImages);
        }
        throw new IOException("Imgur API not available to parse");
    }

    protected GalleryImage parseImgurImage(JsonObject image, boolean isAlbum) {
        String title = JNH.getAttributeString(image, PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE);
        String link = image.get("link").getAsString();
        int width = 0;
        int height = 0;
        String id = image.get("id").getAsString();
        String thumbnailId = UnsupportedUrlFragment.DISPLAY_NAME;
        boolean doNotUseThumbnail = false;
        if (isAlbum) {
            thumbnailId = JNH.getAttributeString(image, "cover");
            if (thumbnailId != null) {
                width = JNH.getAttributeInt(image, "cover_width");
                height = JNH.getAttributeInt(image, "cover_height");
                doNotUseThumbnail = true;
            }
        }
        if (!doNotUseThumbnail) {
            thumbnailId = id;
            width = JNH.getAttributeInt(image, "width");
            height = JNH.getAttributeInt(image, "height");
        }
        GalleryImage galleryImage = new GalleryImage(THUMBNAIL_HOST + thumbnailId + "m.jpg", getImgurLink(isAlbum, id, link), title, width, height);
        galleryImage.setLinksToGallery(isAlbum);
        galleryImage.setSmallThumbnailUrl(THUMBNAIL_HOST + thumbnailId + "t.jpg");
        String extension = "jpg";
        String type = JNH.getAttributeString(image, "type");
        if (type != null && type.equals("image/gif")) {
            extension = "gif";
        }
        galleryImage.setImageUrl(THUMBNAIL_HOST + thumbnailId + "." + extension);
        return galleryImage;
    }

    protected GalleryImage parseImgurImage(JsonElement element) {
        JsonObject image = element.getAsJsonObject();
        return parseImgurImage(image, image.get("is_album") != null ? image.get("is_album").getAsBoolean() : false);
    }

    protected String getImgurLink(boolean isAlbum, String id, String link) {
        return isAlbum ? link : ImgurImageFragment.BASE_URL + id;
    }

    protected final ArrayList<GalleryImage> parseImgurImages(JsonArray imgurImages) {
        ArrayList<GalleryImage> galleryImages = new ArrayList();
        Iterator i$ = imgurImages.iterator();
        while (i$.hasNext()) {
            galleryImages.add(parseImgurImage((JsonElement) i$.next()));
        }
        return galleryImages;
    }

    protected JsonArray getImagesFromJsonObject(JsonObject jsonObject) {
        return jsonObject.get("data").getAsJsonArray();
    }

    protected final JsonArray getImagesFromJsonElement(JsonElement jsonElement) {
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return null;
        }
        return getImagesFromJsonObject(jsonElement.getAsJsonObject());
    }
}
