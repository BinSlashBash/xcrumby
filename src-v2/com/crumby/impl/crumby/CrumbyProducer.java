/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 *  org.apache.http.client.utils.URLEncodedUtils
 *  org.apache.http.message.BasicNameValuePair
 */
package com.crumby.impl.crumby;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import com.crumby.Analytics;
import com.crumby.CrDb;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.impl.imgur.ImgurImageFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryParser;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.router.IndexAttributeValue;
import com.crumby.lib.router.IndexSetting;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class CrumbyProducer
extends GalleryParser {
    public static final String ADULT_MODE = "&Adult=%27Off%27";
    private static final String BING_API_BASE = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/";
    private static final String BING_API_URL_IMAGE = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/Image?";
    private static final String BING_API_URL_WEB = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/Web?";
    public static final String SNAPITO = "http://api.snapito.com/web/sapuk-393f25c4-9be26dbc-df62be89-b79e-8414a8a5-e/sc/?fast=true&delay=0&freshness=-1&url=";
    private static HashMap<String, LayerDrawable> overlays;
    private static int searches;
    final String accountKey = "NH25Z74hLH07heZ4RnxhTIaO1eDwtWxdDzWakM4HWyU";
    private Context context;
    private boolean done;
    private String hostUrl;
    Set<String> processedResults = new HashSet<String>();
    private JsonArray results;
    ArrayList<String> sites;

    static {
        searches = 0;
    }

    private ArrayList<GalleryImage> bingSearch(int n2) throws Exception {
        if (this.done) {
            return new ArrayList<GalleryImage>();
        }
        this.done = true;
        if (++searches < 7) {
            return this.getBingImages(n2, true);
        }
        throw new Exception("Sorry; you have exceeded the maximum 'Search All Galleries' allowed for this session.");
    }

    private String getArbitraryThumbnailUrl(String string2) throws IOException {
        string2 = CrumbyProducer.fetchUrl("https://api.embed.ly/1/oembed?key=4e8e330da2f34353aa47727946038d8f&url=" + URLEncoder.encode(string2));
        return JSON_PARSER.parse(string2).getAsJsonObject().get("thumbnail_url").getAsString();
    }

    /*
     * Enabled aggressive block sorting
     */
    private ArrayList<GalleryImage> getBingImages(int n2, boolean bl2) throws IOException {
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        String string2 = this.getBingJson(n2, bl2);
        JsonArray jsonArray = new JsonParser().parse(string2).getAsJsonObject().get("d").getAsJsonObject().get("results").getAsJsonArray();
        string2 = bl2 ? "SourceUrl" : "Url";
        n2 *= 50;
        while (arrayList.size() < 50 && n2 < jsonArray.size()) {
            JsonObject jsonObject = jsonArray.get(n2).getAsJsonObject();
            String string3 = jsonObject.get(string2).getAsString();
            FragmentIndex fragmentIndex = FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(string3);
            if (fragmentIndex != null && !fragmentIndex.isIndexOf(UnsupportedUrlFragment.class)) {
                String string4;
                GalleryImage galleryImage = new GalleryImage(null, string3, jsonObject.get("Title").getAsString(), 150, 150);
                int n3 = fragmentIndex.getBreadcrumbIcon();
                int n4 = fragmentIndex.getRootBreadcrumbIcon();
                CrDb.d("crumby producer", "" + n4 + " " + fragmentIndex.getFragmentClassName() + " " + n3);
                if (n3 != n4 && n3 != -1) {
                    LayerDrawable layerDrawable;
                    String string5 = "" + n4 + " " + n3;
                    string4 = layerDrawable = overlays.get(string5);
                    if (layerDrawable == null) {
                        layerDrawable = this.context.getResources().getDrawable(n4);
                        Drawable drawable2 = this.context.getResources().getDrawable(n3);
                        string4 = (LayerDrawable)this.context.getResources().getDrawable(2130837688);
                        string4.setDrawableByLayerId(2131493169, (Drawable)layerDrawable);
                        string4.setDrawableByLayerId(2131493170, drawable2);
                        overlays.put(string5, (LayerDrawable)string4);
                    }
                    galleryImage.setIcon((LayerDrawable)string4);
                } else {
                    galleryImage.setIcon(n4);
                }
                if (bl2) {
                    galleryImage.setThumbnailUrl(jsonObject.get("Thumbnail").getAsJsonObject().get("MediaUrl").getAsString());
                    galleryImage.setImageUrl(jsonObject.get("MediaUrl").getAsString());
                } else {
                    galleryImage.setThumbnailUrl(CrumbyProducer.getSnapshot(string3));
                    if (fragmentIndex.isIndexOf(ImgurImageFragment.class)) {
                        string4 = GalleryViewerFragment.matchIdFromUrl(ImgurImageFragment.REGEX_URL, string3);
                        galleryImage.setImageUrl("http://i.imgur.com/" + string4 + ".jpg");
                        galleryImage.setThumbnailUrl("http://i.imgur.com/" + string4 + "m.jpg");
                        galleryImage.setSmallThumbnailUrl("http://i.imgur.com/" + string4 + "t.jpg");
                    }
                }
                galleryImage.setSubtitle(jsonObject.get("DisplayUrl").getAsString());
                galleryImage.setReload(true);
                arrayList.add(galleryImage);
            }
            ++n2;
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    private String getBingJson(int n2, boolean bl2) throws IOException {
        String string2 = bl2 ? "https://api.datamarket.azure.com/Data.ashx/Bing/Search/Image?" : "https://api.datamarket.azure.com/Data.ashx/Bing/Search/Web?";
        string2 = string2 + "Query=%27" + URLEncoder.encode(this.getHostImage().getLinkUrl(), "utf-8") + this.getWhitelist() + "%27&$format=JSON" + "&Adult=%27Off%27" + "&$skip=" + n2 * 50;
        byte[] arrby = Base64.encodeBase64("NH25Z74hLH07heZ4RnxhTIaO1eDwtWxdDzWakM4HWyU:NH25Z74hLH07heZ4RnxhTIaO1eDwtWxdDzWakM4HWyU".getBytes());
        return CrumbyProducer.fetchUrl(string2, "Basic " + new String(arrby));
    }

    public static String getSnapshot(String string2) {
        return "http://api.webthumbnail.org?width=400&height=400&screen=1024&url=" + string2;
    }

    private static ArrayList<GalleryImage> interleave(ArrayList arrayList, ArrayList arrayList2) {
        Object object = new ArrayList(arrayList.size() + arrayList2.size());
        int n2 = 0;
        for (int i2 = 0; n2 < arrayList.size() || i2 < arrayList2.size(); ++n2, ++i2) {
            if (n2 < arrayList.size()) {
                object.add(arrayList.get(n2));
            }
            if (i2 >= arrayList2.size()) continue;
            object.add(arrayList2.get(i2));
        }
        LinkedHashMap<String, GalleryImage> linkedHashMap = new LinkedHashMap<String, GalleryImage>();
        object = object.iterator();
        while (object.hasNext()) {
            GalleryImage galleryImage = (GalleryImage)object.next();
            if (linkedHashMap.get(galleryImage.getLinkUrl()) != null && ((GalleryImage)linkedHashMap.get(galleryImage.getLinkUrl())).getImageUrl() != null) continue;
            linkedHashMap.put(galleryImage.getLinkUrl(), galleryImage);
        }
        arrayList.clear();
        arrayList2.clear();
        return new ArrayList<GalleryImage>(linkedHashMap.values());
    }

    @Override
    public URL convertUrlToApi(String string2) {
        return null;
    }

    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        return this.bingSearch(n2);
    }

    @Override
    public String getHostUrl() {
        return this.hostUrl;
    }

    public String getWhitelist() {
        Object object;
        Object object2 = "(";
        if (this.sites == null) {
            this.sites = new ArrayList();
            object = FragmentRouter.INSTANCE.getAllIndexSettings().iterator();
            while (object.hasNext()) {
                IndexSetting indexSetting = object.next();
                IndexAttributeValue indexAttributeValue = indexSetting.getAttributeValue("show_in_search");
                if (indexAttributeValue == null || !((Boolean)indexAttributeValue.getObject()).booleanValue()) continue;
                this.sites.add(indexSetting.getBaseUrl().replace("http://", "").replace("https://", ""));
            }
        }
        for (int i2 = 0; i2 < this.sites.size(); ++i2) {
            object2 = object = (String)object2 + "site%3A" + this.sites.get(i2);
            if (i2 == this.sites.size() - 1) continue;
            object2 = (String)object + "+OR+";
        }
        return (String)object2 + ")";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void postInitialize() {
        String string2 = this.getHostUrl();
        try {
            new URL(string2);
        }
        catch (MalformedURLException var1_2) {
            ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
            arrayList.add(new BasicNameValuePair("q", this.getHostUrl()));
            try {
                this.hostUrl = new URI("http://bing.com/search?" + URLEncodedUtils.format(arrayList, (String)"UTF-8")).toString();
            }
            catch (URISyntaxException var1_4) {
                Analytics.INSTANCE.newException(var1_4);
            }
        }
        this.pageArg = "first=";
    }

    @Override
    protected void preInitialize() {
        if (overlays == null) {
            overlays = new HashMap();
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

