/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl.derpibooru;

import android.net.Uri;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.impl.derpibooru.DerpibooruFragment;
import com.crumby.impl.derpibooru.DerpibooruImageFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.ImageComment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DerpibooruProducer
extends GalleryProducer {
    private static final String DEFAULT_FILTER = "f178bcd16361641586000000";
    private static final String EVERYTHING_FILTER = "6758f0d16361640e71480000";
    private static boolean HAS_SIGNED_IN;
    public static boolean nsfwMode;
    private static String sessionAuth;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static GalleryImage convertDerpJsonToDerpImage(JsonObject jsonObject) {
        ArrayList<ImageComment> arrayList;
        Object object;
        Iterator<JsonElement> iterator;
        Object object2;
        object2 = jsonObject.get("id_number");
        Object object3 = jsonObject.get("description");
        if (jsonObject.get("representations") == null) {
            return null;
        }
        arrayList = jsonObject.get("representations").getAsJsonObject();
        iterator = arrayList.get("full");
        object = arrayList.get("thumb");
        arrayList = arrayList.get("thumb_small");
        object2 = object2.toString();
        object3 = object3.toString();
        iterator = iterator.toString();
        object = object.toString();
        arrayList.toString();
        object = new GalleryImage("https:" + object.substring(1, object.length() - 1), "https://derpibooru.org/" + (String)object2, null, jsonObject.get("width").getAsInt(), jsonObject.get("height").getAsInt());
        object.setId(jsonObject.get("id").getAsString());
        object.setImageUrl("https:" + iterator.substring(1, iterator.length() - 1));
        object.setDescription(object3.substring(1, object3.length() - 1));
        object.setDescription(object.getDescription().replace("\\r\\n", "\n").replace("\\\"", "\""));
        try {
            arrayList = jsonObject.get("source_url").getAsString();
            object.setDescription(object.getDescription() + "\n" + arrayList);
        }
        catch (Exception var2_5) {}
        try {
            iterator = jsonObject.get("comments").getAsJsonArray();
            arrayList = new ArrayList<ImageComment>();
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                object2 = iterator.next().getAsJsonObject();
                arrayList.add(new ImageComment(object2.get("author").getAsString(), object2.get("body").getAsString(), null));
            }
            object.setComments(arrayList);
        }
        catch (NullPointerException var2_4) {
            object.setReload(true);
        }
        object.setTags(DerpibooruImageFragment.getTags(jsonObject));
        return object;
    }

    public static String getAuthKey(String object) throws Exception {
        if ((object = Jsoup.parse((String)object).getElementsByAttributeValue("name", "csrf-token")) != null && object.first().tagName().equals("meta")) {
            return object.first().attr("content");
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static void makeNSFW(boolean bl2) throws Exception {
        if (nsfwMode == bl2) {
            return;
        }
        CrDb.d("derpibooru producer", "make nsfw: " + bl2);
        nsfwMode = bl2;
        if (sessionAuth == null) {
            sessionAuth = DerpibooruProducer.getAuthKey(DerpibooruProducer.fetchUrl("https://derpibooru.org"));
        }
        Object object = bl2 ? "6758f0d16361640e71480000" : "f178bcd16361641586000000";
        object = (HttpURLConnection)new URL("https://derpibooru.org/filters/select?id=" + (String)object).openConnection();
        object.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        object.setRequestProperty("Accept", "*/*");
        object.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
        object.setDoInput(true);
        object.setDoOutput(true);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(object.getOutputStream());
        outputStreamWriter.write("_method=patch&authenticity_token=" + URLEncoder.encode("", "UTF-8"));
        outputStreamWriter.close();
        object.connect();
        if (object.getResponseCode() == 200) return;
        throw new Exception("Derpibooru server could not change the image filter!");
    }

    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        Iterator<JsonElement> iterator = this.getDerpImages(n2).iterator();
        while (iterator.hasNext()) {
            GalleryImage galleryImage = DerpibooruProducer.convertDerpJsonToDerpImage(iterator.next().getAsJsonObject());
            if (galleryImage == null) continue;
            arrayList.add(galleryImage);
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected JsonArray getDerpImages(int n2) throws Exception {
        String string2;
        String string3 = string2 = DerpibooruProducer.getQueryParameter(Uri.parse((String)this.getHostUrl()), this.getHostUrl(), "sbq");
        if (string2 == null) {
            string3 = string2;
            if (FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(DerpibooruFragment.class)) {
                string3 = "safe";
            }
        }
        if (string3 != null || !GalleryViewer.BLACK_LISTED_TAGS.isEmpty()) {
            string2 = string3;
            if (string3 == null) {
                string2 = "";
            }
            string3 = string2;
            if (!GalleryViewer.BLACK_LISTED_TAGS.isEmpty()) {
                string3 = string2;
                if (!string2.equals("")) {
                    string3 = string2 + ",";
                }
                string3 = string3 + GalleryViewer.getBlacklist().substring(1).replace(" ", ",").replace("_", " ");
            }
            string2 = "https://derpibooru.org" + "/search.json?q=" + Uri.encode((String)string3);
            string3 = "search";
        } else {
            string2 = "https://derpibooru.org" + "/images.json?";
            string3 = "images";
        }
        String string4 = string2;
        if (n2 != 0) {
            string4 = string2 + "&page=" + (n2 + 1);
        }
        return this.getDerpImages(string4, string3);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected JsonArray getDerpImages(String object, String object2) throws Exception {
        object = JSON_PARSER.parse(DerpibooruProducer.fetchUrl((String)object));
        try {
            object = object2 = object.getAsJsonObject().get((String)object2);
            do {
                return object.getAsJsonArray();
                break;
            } while (true);
        }
        catch (Exception var2_3) {
            return object.getAsJsonArray();
        }
    }
}

