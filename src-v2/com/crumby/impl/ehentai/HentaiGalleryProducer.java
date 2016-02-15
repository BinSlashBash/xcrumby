/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.ehentai;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryScraper;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HentaiGalleryProducer
extends GalleryScraper {
    @Override
    protected ArrayList<GalleryImage> parseGalleryImagesHtml(Document object) throws NullPointerException {
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        object = object.getElementsByClass("it2");
        int n2 = 0;
        object = object.iterator();
        while (object.hasNext()) {
            Object object2 = (Element)object.next();
            Object object3 = object2.html().split("~", 4);
            object2 = object2.parent().getElementsByClass("it5").first().child(0).attr("href");
            if (object3.length != 4 || !object3[0].equals("init")) continue;
            object3 = new GalleryImage("http://" + object3[1] + "/" + object3[2], (String)object2, "" + n2 + " " + object3[3]);
            object3.setLinksToGallery(true);
            arrayList.add((GalleryImage)object3);
            ++n2;
        }
        return arrayList;
    }

    @Override
    protected void postInitialize() {
        this.pageArg = "page=";
    }
}

