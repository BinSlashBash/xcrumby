package com.crumby.impl.crumby;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import com.crumby.Analytics;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.impl.imgur.ImgurImageFragment;
import com.crumby.impl.imgur.ImgurProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryParser;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentIndex;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.router.IndexAttributeValue;
import com.crumby.lib.router.IndexSetting;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class CrumbyProducer extends GalleryParser {
    public static final String ADULT_MODE = "&Adult=%27Off%27";
    private static final String BING_API_BASE = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/";
    private static final String BING_API_URL_IMAGE = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/Image?";
    private static final String BING_API_URL_WEB = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/Web?";
    public static final String SNAPITO = "http://api.snapito.com/web/sapuk-393f25c4-9be26dbc-df62be89-b79e-8414a8a5-e/sc/?fast=true&delay=0&freshness=-1&url=";
    private static HashMap<String, LayerDrawable> overlays;
    private static int searches;
    final String accountKey;
    private Context context;
    private boolean done;
    private String hostUrl;
    Set<String> processedResults;
    private JsonArray results;
    ArrayList<String> sites;

    public CrumbyProducer() {
        this.accountKey = "NH25Z74hLH07heZ4RnxhTIaO1eDwtWxdDzWakM4HWyU";
        this.processedResults = new HashSet();
    }

    public String getHostUrl() {
        return this.hostUrl;
    }

    protected void preInitialize() {
        if (overlays == null) {
            overlays = new HashMap();
        }
    }

    public void postInitialize() {
        try {
            URL url = new URL(getHostUrl());
        } catch (MalformedURLException e) {
            List<NameValuePair> list = new ArrayList();
            list.add(new BasicNameValuePair("q", getHostUrl()));
            try {
                this.hostUrl = new URI(CrumbyGalleryFragment.URL_HOST + URLEncodedUtils.format(list, Hex.DEFAULT_CHARSET_NAME)).toString();
            } catch (URISyntaxException e1) {
                Analytics.INSTANCE.newException(e1);
            }
        }
        this.pageArg = "first=";
    }

    public URL convertUrlToApi(String url) {
        return null;
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        return bingSearch(pageNumber);
    }

    public String getWhitelist() {
        String whitelist = "(";
        if (this.sites == null) {
            this.sites = new ArrayList();
            for (IndexSetting indexSetting : FragmentRouter.INSTANCE.getAllIndexSettings()) {
                IndexAttributeValue value = indexSetting.getAttributeValue(GalleryViewerFragment.SHOW_IN_SEARCH_KEY);
                if (value != null && ((Boolean) value.getObject()).booleanValue()) {
                    this.sites.add(indexSetting.getBaseUrl().replace("http://", UnsupportedUrlFragment.DISPLAY_NAME).replace("https://", UnsupportedUrlFragment.DISPLAY_NAME));
                }
            }
        }
        for (int i = 0; i < this.sites.size(); i++) {
            whitelist = whitelist + "site%3A" + ((String) this.sites.get(i));
            if (i != this.sites.size() - 1) {
                whitelist = whitelist + "+OR+";
            }
        }
        return whitelist + ")";
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private String getBingJson(int pageNumber, boolean useImage) throws IOException {
        return GalleryProducer.fetchUrl((useImage ? BING_API_URL_IMAGE : BING_API_URL_WEB) + "Query=%27" + URLEncoder.encode(getHostImage().getLinkUrl(), "utf-8") + getWhitelist() + "%27&$format=JSON" + ADULT_MODE + "&$skip=" + (pageNumber * 50), "Basic " + new String(Base64.encodeBase64("NH25Z74hLH07heZ4RnxhTIaO1eDwtWxdDzWakM4HWyU:NH25Z74hLH07heZ4RnxhTIaO1eDwtWxdDzWakM4HWyU".getBytes())));
    }

    private String getArbitraryThumbnailUrl(String url) throws IOException {
        return JSON_PARSER.parse(GalleryProducer.fetchUrl("https://api.embed.ly/1/oembed?key=4e8e330da2f34353aa47727946038d8f&url=" + URLEncoder.encode(url))).getAsJsonObject().get("thumbnail_url").getAsString();
    }

    private ArrayList<GalleryImage> getBingImages(int pageNumber, boolean useImage) throws IOException {
        ArrayList<GalleryImage> galleryImages = new ArrayList();
        JsonArray results = new JsonParser().parse(getBingJson(pageNumber, useImage)).getAsJsonObject().get("d").getAsJsonObject().get("results").getAsJsonArray();
        Class crumbyClass = UnsupportedUrlFragment.class;
        Class crumbySearchClass = CrumbyGalleryFragment.class;
        String urlKey = useImage ? "SourceUrl" : "Url";
        int i = pageNumber * 50;
        while (galleryImages.size() < 50 && i < results.size()) {
            JsonObject galleryImageJson = results.get(i).getAsJsonObject();
            String url = galleryImageJson.get(urlKey).getAsString();
            FragmentIndex index = FragmentRouter.INSTANCE.getGalleryFragmentIndexByUrl(url);
            if (!(index == null || index.isIndexOf(crumbyClass))) {
                GalleryImage galleryImage = new GalleryImage(null, url, galleryImageJson.get("Title").getAsString(), 150, 150);
                int secondId = index.getBreadcrumbIcon();
                int firstId = index.getRootBreadcrumbIcon();
                CrDb.m0d("crumby producer", firstId + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + index.getFragmentClassName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + secondId);
                if (secondId == firstId || secondId == -1) {
                    galleryImage.setIcon(firstId);
                } else {
                    String key = firstId + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + secondId;
                    LayerDrawable icon = (LayerDrawable) overlays.get(key);
                    if (icon == null) {
                        Drawable first = this.context.getResources().getDrawable(firstId);
                        Drawable second = this.context.getResources().getDrawable(secondId);
                        icon = (LayerDrawable) this.context.getResources().getDrawable(C0065R.drawable.search_icon);
                        icon.setDrawableByLayerId(C0065R.id.first_layer_search, first);
                        icon.setDrawableByLayerId(C0065R.id.second_layer_search, second);
                        overlays.put(key, icon);
                    }
                    galleryImage.setIcon(icon);
                }
                if (useImage) {
                    galleryImage.setThumbnailUrl(galleryImageJson.get("Thumbnail").getAsJsonObject().get("MediaUrl").getAsString());
                    galleryImage.setImageUrl(galleryImageJson.get("MediaUrl").getAsString());
                } else {
                    galleryImage.setThumbnailUrl(getSnapshot(url));
                    if (index.isIndexOf(ImgurImageFragment.class)) {
                        String id = GalleryViewerFragment.matchIdFromUrl(ImgurImageFragment.REGEX_URL, url);
                        galleryImage.setImageUrl(ImgurProducer.THUMBNAIL_HOST + id + ".jpg");
                        galleryImage.setThumbnailUrl(ImgurProducer.THUMBNAIL_HOST + id + "m.jpg");
                        galleryImage.setSmallThumbnailUrl(ImgurProducer.THUMBNAIL_HOST + id + "t.jpg");
                    }
                }
                galleryImage.setSubtitle(galleryImageJson.get("DisplayUrl").getAsString());
                galleryImage.setReload(true);
                galleryImages.add(galleryImage);
            }
            i++;
        }
        return galleryImages;
    }

    public static String getSnapshot(String url) {
        return "http://api.webthumbnail.org?width=400&height=400&screen=1024&url=" + url;
    }

    private static ArrayList<GalleryImage> interleave(ArrayList a1, ArrayList a2) {
        ArrayList r = new ArrayList(a1.size() + a2.size());
        int i = 0;
        int j = 0;
        while (true) {
            if (i >= a1.size() && j >= a2.size()) {
                break;
            }
            if (i < a1.size()) {
                r.add(a1.get(i));
            }
            if (j < a2.size()) {
                r.add(a2.get(j));
            }
            i++;
            j++;
        }
        LinkedHashMap<String, GalleryImage> map = new LinkedHashMap();
        Iterator i$ = r.iterator();
        while (i$.hasNext()) {
            GalleryImage image = (GalleryImage) i$.next();
            if (map.get(image.getLinkUrl()) == null || ((GalleryImage) map.get(image.getLinkUrl())).getImageUrl() == null) {
                map.put(image.getLinkUrl(), image);
            }
        }
        a1.clear();
        a2.clear();
        return new ArrayList(map.values());
    }

    static {
        searches = 0;
    }

    private ArrayList<GalleryImage> bingSearch(int pageNumber) throws Exception {
        if (this.done) {
            return new ArrayList();
        }
        this.done = true;
        searches++;
        if (searches < 7) {
            return getBingImages(pageNumber, true);
        }
        throw new Exception("Sorry; you have exceeded the maximum 'Search All Galleries' allowed for this session.");
    }
}
