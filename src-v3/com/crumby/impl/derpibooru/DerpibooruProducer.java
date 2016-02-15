package com.crumby.impl.derpibooru;

import android.net.Uri;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.ImageComment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.plus.PlusShare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import oauth.signpost.OAuth;
import org.apache.commons.codec.binary.Hex;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import uk.co.senab.photoview.IPhotoView;

public class DerpibooruProducer extends GalleryProducer {
    private static final String DEFAULT_FILTER = "f178bcd16361641586000000";
    private static final String EVERYTHING_FILTER = "6758f0d16361640e71480000";
    private static boolean HAS_SIGNED_IN;
    public static boolean nsfwMode;
    private static String sessionAuth;

    protected JsonArray getDerpImages(int pageNumber) throws Exception {
        String key;
        String apiUrlString = DerpibooruFragment.BASE_URL;
        String search = GalleryProducer.getQueryParameter(Uri.parse(getHostUrl()), getHostUrl(), "sbq");
        if (search == null && FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(DerpibooruFragment.class)) {
            search = "safe";
        }
        if (search == null && GalleryViewer.BLACK_LISTED_TAGS.isEmpty()) {
            apiUrlString = apiUrlString + "/images.json?";
            key = "images";
        } else {
            if (search == null) {
                search = UnsupportedUrlFragment.DISPLAY_NAME;
            }
            if (!GalleryViewer.BLACK_LISTED_TAGS.isEmpty()) {
                if (!search.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                    search = search + ",";
                }
                search = search + GalleryViewer.getBlacklist().substring(1).replace(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, ",").replace("_", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            }
            apiUrlString = apiUrlString + "/search.json?q=" + Uri.encode(search);
            key = "search";
        }
        if (pageNumber != 0) {
            apiUrlString = apiUrlString + "&page=" + (pageNumber + 1);
        }
        return getDerpImages(apiUrlString, key);
    }

    protected JsonArray getDerpImages(String apiUrl, String key) throws Exception {
        JsonElement derpElement;
        JsonElement parsedDerp = JSON_PARSER.parse(GalleryProducer.fetchUrl(apiUrl));
        try {
            derpElement = parsedDerp.getAsJsonObject().get(key);
        } catch (Exception e) {
            derpElement = parsedDerp;
        }
        return derpElement.getAsJsonArray();
    }

    public static GalleryImage convertDerpJsonToDerpImage(JsonObject derpSubObject) {
        JsonElement derpLinkURL = derpSubObject.get("id_number");
        JsonElement derpDescrip = derpSubObject.get(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
        if (derpSubObject.get("representations") == null) {
            return null;
        }
        JsonObject derpSubObject2 = derpSubObject.get("representations").getAsJsonObject();
        JsonElement derpImageURL = derpSubObject2.get("full");
        JsonElement derpThumbURL = derpSubObject2.get("thumb");
        JsonElement derpSmallThumb = derpSubObject2.get("thumb_small");
        String tempLink = derpLinkURL.toString();
        String tempDescrip = derpDescrip.toString();
        String tempImageURL = derpImageURL.toString();
        String tempThumbURL = derpThumbURL.toString();
        String tempSmallThumb = derpSmallThumb.toString();
        GalleryImage derpImage = new GalleryImage("https:" + tempThumbURL.substring(1, tempThumbURL.length() - 1), "https://derpibooru.org/" + tempLink, null, derpSubObject.get("width").getAsInt(), derpSubObject.get("height").getAsInt());
        derpImage.setId(derpSubObject.get("id").getAsString());
        derpImage.setImageUrl("https:" + tempImageURL.substring(1, tempImageURL.length() - 1));
        derpImage.setDescription(tempDescrip.substring(1, tempDescrip.length() - 1));
        derpImage.setDescription(derpImage.getDescription().replace("\\r\\n", "\n").replace("\\\"", "\""));
        try {
            derpImage.setDescription(derpImage.getDescription() + "\n" + derpSubObject.get("source_url").getAsString());
        } catch (Exception e) {
        }
        try {
            JsonArray commentJson = derpSubObject.get("comments").getAsJsonArray();
            ArrayList<ImageComment> imageComments = new ArrayList();
            Iterator i$ = commentJson.iterator();
            while (i$.hasNext()) {
                JsonObject commentObject = ((JsonElement) i$.next()).getAsJsonObject();
                imageComments.add(new ImageComment(commentObject.get("author").getAsString(), commentObject.get("body").getAsString(), null));
            }
            derpImage.setComments(imageComments);
        } catch (NullPointerException e2) {
            derpImage.setReload(true);
        }
        derpImage.setTags(DerpibooruImageFragment.getTags(derpSubObject));
        return derpImage;
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        ArrayList<GalleryImage> images = new ArrayList();
        Iterator i$ = getDerpImages(pageNumber).iterator();
        while (i$.hasNext()) {
            GalleryImage image = convertDerpJsonToDerpImage(((JsonElement) i$.next()).getAsJsonObject());
            if (image != null) {
                images.add(image);
            }
        }
        return images;
    }

    public static String getAuthKey(String derp) throws Exception {
        Elements el = Jsoup.parse(derp).getElementsByAttributeValue("name", "csrf-token");
        if (el == null || !el.first().tagName().equals("meta")) {
            return null;
        }
        return el.first().attr("content");
    }

    public static void makeNSFW(boolean wantsNsfw) throws Exception {
        if (nsfwMode != wantsNsfw) {
            CrDb.m0d("derpibooru producer", "make nsfw: " + wantsNsfw);
            nsfwMode = wantsNsfw;
            String authkey = UnsupportedUrlFragment.DISPLAY_NAME;
            if (sessionAuth == null) {
                sessionAuth = getAuthKey(GalleryProducer.fetchUrl(DerpibooruFragment.BASE_URL));
            }
            HttpURLConnection urlConnection = (HttpURLConnection) new URL("https://derpibooru.org/filters/select?id=" + (wantsNsfw ? EVERYTHING_FILTER : DEFAULT_FILTER)).openConnection();
            urlConnection.setRequestProperty("Content-Type", OAuth.FORM_ENCODED);
            urlConnection.setRequestProperty("Accept", "*/*");
            urlConnection.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write("_method=patch&authenticity_token=" + URLEncoder.encode(authkey, Hex.DEFAULT_CHARSET_NAME));
            out.close();
            urlConnection.connect();
            if (urlConnection.getResponseCode() != IPhotoView.DEFAULT_ZOOM_DURATION) {
                throw new Exception("Derpibooru server could not change the image filter!");
            }
        }
    }
}
