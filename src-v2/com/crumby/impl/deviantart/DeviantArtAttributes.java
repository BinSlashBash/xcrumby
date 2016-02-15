/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.deviantart;

import com.crumby.impl.deviantart.DeviantArtProducer;
import com.crumby.lib.ExtraAttributes;
import com.crumby.lib.GalleryImage;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DeviantArtAttributes
extends ExtraAttributes {
    private String htmlDescription;
    private ArrayList<GalleryImage> similarImages;

    public DeviantArtAttributes(String string2) {
        this.htmlDescription = string2;
    }

    public DeviantArtAttributes(ArrayList<GalleryImage> arrayList) {
        this.similarImages = arrayList;
    }

    /*
     * Enabled aggressive block sorting
     */
    public DeviantArtAttributes(Document iterator, String object) {
        this((String)object);
        iterator = iterator.getElementsByClass("deviation-mlt-preview-body");
        if (!iterator.isEmpty()) {
            iterator = iterator.get(iterator.size() - 1);
            this.similarImages = new ArrayList();
            iterator = iterator.getElementsByTag("a").iterator();
            while (iterator.hasNext()) {
                object = DeviantArtProducer.convertLinkToImage(iterator.next(), true);
                if (object == null || object.getThumbnailUrl() == null || object.getThumbnailUrl().equals("")) continue;
                this.similarImages.add((GalleryImage)object);
            }
        }
    }

    public String getHtmlDescription() {
        return this.htmlDescription;
    }

    public ArrayList<GalleryImage> getSimilarImages() {
        return this.similarImages;
    }
}

