/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.ehentai;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HentaiSequenceProducer
extends GalleryProducer {
    private boolean halt;
    private String imageUrl;

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        void var2_3;
        if (this.halt) {
            return var2_3;
        }
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        Iterator<Element> iterator = Jsoup.connect(this.imageUrl).get().getElementsByTag("img").iterator();
        do {
            ArrayList<GalleryImage> arrayList2 = arrayList;
            if (!iterator.hasNext()) {
                return var2_3;
            }
            Element element = iterator.next();
            if (element.attr("style") == null || element.attr("style").equals("")) continue;
            String string2 = element.parent().attr("href");
            GalleryImage galleryImage = new GalleryImage(null, null, null);
            galleryImage.setImageUrl(element.attr("src"));
            if (this.imageUrl.equals(string2)) {
                this.halt = true;
            }
            this.imageUrl = string2;
            arrayList.add(galleryImage);
        } while (true);
    }

    @Override
    public void postInitialize() {
        this.imageUrl = this.getHostImage().getLinkUrl();
    }
}

