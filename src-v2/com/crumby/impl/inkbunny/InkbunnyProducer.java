/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl.inkbunny;

import android.net.Uri;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Iterator;

public class InkbunnyProducer
extends GalleryProducer {
    public static String API_ROOT = "https://inkbunny.net/api_";
    public static String GET_SID = API_ROOT + "login.php?username=guest";
    public static String SESSION_ID;

    public static void getSessionID() throws Exception {
        String string2 = JSON_PARSER.parse(InkbunnyProducer.fetchUrl(GET_SID)).getAsJsonObject().get("sid").toString();
        SESSION_ID = string2.substring(1, string2.length() - 1);
    }

    public static GalleryImage jsonObjectToImage(JsonObject object) {
        Object object2 = object.get("thumbnail_url_medium").toString();
        String string2 = object.get("submission_id").toString();
        Iterator<JsonElement> iterator = object.get("title").toString();
        object2 = new GalleryImage(object2.substring(1, object2.length() - 1), "https://inkbunny.net/submissionview.php?id=" + string2.substring(1, string2.length() - 1), iterator.substring(1, iterator.length() - 1), 100, 100);
        string2 = object.get("file_name").toString();
        string2 = string2.substring(1, string2.length() - 1);
        if (object.get("keywords") != null) {
            iterator = object.get("keywords").getAsJsonArray();
            object = "";
            iterator = iterator.iterator();
            while (iterator.hasNext()) {
                String string3 = iterator.next().getAsJsonObject().get("keyword_name").toString();
                object = (String)object + string3.substring(1, string3.length() - 1) + ",";
            }
            object2.setTags(object.split(","));
        }
        object2.setImageUrl("https://inkbunny.net/files/screen/" + string2.substring(0, 3) + "/" + string2);
        return object2;
    }

    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        Iterator<JsonElement> iterator = this.getGalleryJson(n2).get("submissions").getAsJsonArray().iterator();
        while (iterator.hasNext()) {
            arrayList.add(InkbunnyProducer.jsonObjectToImage(iterator.next().getAsJsonObject()));
        }
        return arrayList;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public JsonObject getGalleryJson(int var1_1) throws Exception {
        var4_2 = InkbunnyProducer.getQueryParameter(Uri.parse((String)this.getHostUrl()), this.getHostUrl(), "text");
        var3_3 = InkbunnyProducer.API_ROOT + "search.php?sid=" + InkbunnyProducer.SESSION_ID;
        if (InkbunnyProducer.SESSION_ID == "") ** GOTO lbl-1000
        var2_4 = var3_3;
        if (InkbunnyProducer.JSON_PARSER.parse(InkbunnyProducer.fetchUrl(var3_3)).getAsJsonObject().get("sid") == null) lbl-1000: // 2 sources:
        {
            InkbunnyProducer.getSessionID();
            var2_4 = InkbunnyProducer.API_ROOT + "search.php?sid=" + InkbunnyProducer.SESSION_ID;
        }
        var3_3 = var2_4;
        if (var1_1 > 0) {
            var3_3 = var2_4 + "&page=" + (var1_1 + 1);
        }
        var2_4 = var3_3;
        if (var4_2 == null) return InkbunnyProducer.JSON_PARSER.parse(InkbunnyProducer.fetchUrl(var2_4)).getAsJsonObject();
        var2_4 = var3_3;
        if (var4_2.equals("") != false) return InkbunnyProducer.JSON_PARSER.parse(InkbunnyProducer.fetchUrl(var2_4)).getAsJsonObject();
        var2_4 = var3_3 + "&text=" + Uri.encode((String)var4_2);
        return InkbunnyProducer.JSON_PARSER.parse(InkbunnyProducer.fetchUrl(var2_4)).getAsJsonObject();
    }
}

