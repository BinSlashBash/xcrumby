/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl.deviantart;

import android.net.Uri;
import com.crumby.CrDb;
import com.crumby.impl.deviantart.DeviantArtAttributes;
import com.crumby.lib.ExtraAttributes;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DeviantArtProducer
extends GalleryProducer {
    protected static final String API_URL = "http://backend.deviantart.com/";
    protected static final String RSS_API_URL = "http://backend.deviantart.com/rss.xml?";

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static GalleryImage convertLinkToImage(Element element, boolean bl2) {
        int n2;
        Object object;
        int n3;
        if (!element.hasClass("thumb") && !bl2 || (object = element.getElementsByTag("img").first()) == null) {
            return null;
        }
        String string2 = element.attr("data-super-full-img");
        try {
            n3 = Integer.parseInt(element.attr("data-super-full-width"));
            n2 = Integer.parseInt(element.attr("data-super-full-height"));
        }
        catch (NumberFormatException var4_4) {
            try {
                n3 = Integer.parseInt(element.attr("data-super-width"));
                n2 = Integer.parseInt(element.attr("data-super-height"));
                string2 = element.attr("data-super-img");
            }
            catch (NumberFormatException var4_5) {
                n3 = 0;
                n2 = 0;
                string2 = object.attr("src");
            }
        }
        object = new GalleryImage(object.attr("src"), element.attr("href"), element.attr("title"), n3, n2);
        CrDb.d("deviantart producer", "===================");
        CrDb.d("deviantart producer", element.attr("data-super-full-img"));
        CrDb.d("deviantart producer", element.attr("data-super-img") + " ");
        CrDb.d("deviantart producer", string2);
        CrDb.d("deviantart producer", "===================");
        object.setImageUrl(string2);
        object.setReload(true);
        return object;
    }

    public static String modifyDescription(String string2) {
        String string3 = string2;
        if (string2 != null) {
            string3 = string2;
            if (string2.contains("<img")) {
                string3 = string2.substring(0, string2.lastIndexOf("<img"));
            }
        }
        return string3;
    }

    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        if (n2 == 0 || DeviantArtProducer.getParameterInUrl(this.getHostUrl(), "q") != null) {
            this.getImagesFromReallyStupidSyndication(arrayList, n2);
            return arrayList;
        }
        this.getImagesFromWebPage(arrayList, n2);
        return arrayList;
    }

    protected String getDefaultUrl(int n2) {
        return "http://www.deviantart.com/?order=67108864&offset=" + n2 * 24;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void getImagesFromReallyStupidSyndication(ArrayList<GalleryImage> arrayList, int n2) throws Exception {
        Object object = XML.toJSONObject(DeviantArtProducer.legacyfetchUrl(this.getRssUrl(n2))).toString();
        if ((object = new ObjectMapper().readTree((String)object).findParent("item")) != null) {
            if (this.getHostImage().getTitle() == null && this.getHostImage().getDescription() == null) {
                this.setGalleryMetadata(object.get("title").asText(), object.get("description").asText());
            }
            if (object.get("item") != null) {
                for (JsonNode jsonNode : (ArrayNode)object.get("item")) {
                    int n3;
                    TreeNode treeNode = jsonNode.get("media:content");
                    Object object2 = (ArrayNode)jsonNode.get("media:thumbnail");
                    n2 = 0;
                    object = "";
                    if (object2 == null) continue;
                    object2 = object2.iterator();
                    while (object2.hasNext()) {
                        JsonNode jsonNode2 = (JsonNode)object2.next();
                        n3 = jsonNode2.get("height").asInt();
                        if (n3 <= n2) continue;
                        n2 = n3;
                        object = jsonNode2.get("url").asText();
                    }
                    n2 = 0;
                    n3 = 0;
                    if (treeNode.get("width") != null) {
                        n2 = treeNode.get("width").asInt();
                        n3 = treeNode.get("height").asInt();
                    }
                    object = new GalleryImage((String)object, jsonNode.get("link").asText(), jsonNode.get("title").asText(), n2, n3);
                    object.setImageUrl(treeNode.get("url").asText());
                    object.setReload(true);
                    object.setAttributes(new DeviantArtAttributes(DeviantArtProducer.modifyDescription(jsonNode.get("description").asText())));
                    arrayList.add((GalleryImage)object);
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void getImagesFromWebPage(ArrayList<GalleryImage> arrayList, int n2) throws Exception {
        Element element = Jsoup.parse(DeviantArtProducer.fetchUrl(this.getDefaultUrl(n2))).getElementsByClass("page-results").first();
        if (element != null) {
            for (Object object : element.children()) {
                try {
                    if ((object = DeviantArtProducer.convertLinkToImage(object.getElementsByTag("a").first(), false)) == null) continue;
                    arrayList.add((GalleryImage)object);
                }
                catch (Exception var4_5) {}
            }
        }
    }

    protected String getRssUrl(int n2) {
        String string2 = DeviantArtProducer.getParameterInUrl(this.getHostUrl(), "q");
        if (string2 != null) {
            return this.getRssUrlForSearch(n2, string2);
        }
        return "http://backend.deviantart.com/rss.xml?offset=" + n2 * 24 + "&order=67108864";
    }

    protected String getRssUrlForSearch(int n2, String string2) {
        return "http://backend.deviantart.com/rss.xml?offset=" + n2 * 60 + "&q=" + Uri.encode((String)string2);
    }
}

