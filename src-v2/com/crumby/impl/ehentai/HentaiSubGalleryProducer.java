/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.ehentai;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryScraper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HentaiSubGalleryProducer
extends GalleryScraper {
    private Map<String, GalleryImage> generatedImages;

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected ArrayList<GalleryImage> parseGalleryImagesHtml(Document arrayList) {
        if (this.getCurrentPage() == 1) {
            this.setGalleryMetadata(arrayList.getElementsByTag("title").get(0).html(), "");
        }
        ArrayList<GalleryImage> arrayList2 = new ArrayList<GalleryImage>();
        arrayList = arrayList.getElementById("gdt").getElementsByClass("gdtm");
        int n2 = 0;
        arrayList = arrayList.iterator();
        while (arrayList.hasNext()) {
            Object object = (Element)arrayList.next();
            Object object2 = object.child(0).attr("style");
            String string2 = object2.substring(object2.indexOf("background"));
            String string3 = this.parseStyle((String)object2, "url(", ")");
            object = object.child(0).child(0).attr("href");
            int n3 = Integer.parseInt(this.parseStyle((String)object2, "height:", "px"));
            object2 = new GalleryImage(string3, (String)object, null, Integer.parseInt(this.parseStyle((String)object2, "width:", "px")), n3, Integer.parseInt(this.parseStyle(string2.substring(string2.indexOf(")")), "-", "px")));
            object2.setReload(true);
            if (this.generatedImages.get(string3) != null) {
                ++n2;
            } else {
                this.generatedImages.put(string3, (GalleryImage)object2);
            }
            arrayList2.add((GalleryImage)object2);
        }
        arrayList = arrayList2;
        if (n2 != arrayList2.size()) return arrayList;
        return null;
    }

    String parseStyle(String string2, String string3, String string4) {
        string2 = string2.substring(string2.indexOf(string3));
        return string2.substring(string3.length(), string2.indexOf(string4));
    }

    @Override
    public void postInitialize() {
        this.pageArg = "p=";
        this.generatedImages = new HashMap<String, GalleryImage>();
    }
}

