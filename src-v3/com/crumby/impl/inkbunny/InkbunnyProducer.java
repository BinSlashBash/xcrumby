package com.crumby.impl.inkbunny;

import android.net.Uri;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.impl.device.DeviceFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.google.android.gms.plus.PlusShare;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;

public class InkbunnyProducer extends GalleryProducer {
    public static String API_ROOT;
    public static String GET_SID;
    public static String SESSION_ID;

    static {
        API_ROOT = InkbunnyFragment.API_ROOT;
        GET_SID = API_ROOT + "login.php?username=guest";
    }

    public static void getSessionID() throws Exception {
        String temp = JSON_PARSER.parse(GalleryProducer.fetchUrl(GET_SID)).getAsJsonObject().get("sid").toString();
        SESSION_ID = temp.substring(1, temp.length() - 1);
    }

    public static GalleryImage jsonObjectToImage(JsonObject obj) {
        String thumbnailUrl = obj.get("thumbnail_url_medium").toString();
        String submissionID = obj.get("submission_id").toString();
        String title = obj.get(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE).toString();
        GalleryImage image = new GalleryImage(thumbnailUrl.substring(1, thumbnailUrl.length() - 1), "https://inkbunny.net/submissionview.php?id=" + submissionID.substring(1, submissionID.length() - 1), title.substring(1, title.length() - 1), 100, 100);
        String fileName = obj.get("file_name").toString();
        fileName = fileName.substring(1, fileName.length() - 1);
        if (obj.get("keywords") != null) {
            JsonArray array = obj.get("keywords").getAsJsonArray();
            String keywords = UnsupportedUrlFragment.DISPLAY_NAME;
            Iterator i$ = array.iterator();
            while (i$.hasNext()) {
                String temp2 = ((JsonElement) i$.next()).getAsJsonObject().get("keyword_name").toString();
                keywords = keywords + temp2.substring(1, temp2.length() - 1) + ",";
            }
            image.setTags(keywords.split(","));
        }
        image.setImageUrl("https://inkbunny.net/files/screen/" + fileName.substring(0, 3) + DeviceFragment.REGEX_BASE + fileName);
        return image;
    }

    public JsonObject getGalleryJson(int pageNumber) throws Exception {
        String keywords = GalleryProducer.getQueryParameter(Uri.parse(getHostUrl()), getHostUrl(), "text");
        String url = API_ROOT + "search.php?sid=" + SESSION_ID;
        if (SESSION_ID == UnsupportedUrlFragment.DISPLAY_NAME || JSON_PARSER.parse(GalleryProducer.fetchUrl(url)).getAsJsonObject().get("sid") == null) {
            getSessionID();
            url = API_ROOT + "search.php?sid=" + SESSION_ID;
        }
        if (pageNumber > 0) {
            url = url + "&page=" + (pageNumber + 1);
        }
        if (!(keywords == null || keywords.equals(UnsupportedUrlFragment.DISPLAY_NAME))) {
            url = url + "&text=" + Uri.encode(keywords);
        }
        return JSON_PARSER.parse(GalleryProducer.fetchUrl(url)).getAsJsonObject();
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        ArrayList<GalleryImage> images = new ArrayList();
        Iterator i$ = getGalleryJson(pageNumber).get("submissions").getAsJsonArray().iterator();
        while (i$.hasNext()) {
            images.add(jsonObjectToImage(((JsonElement) i$.next()).getAsJsonObject()));
        }
        return images;
    }
}
