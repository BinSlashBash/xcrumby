/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl;

import android.net.Uri;
import com.crumby.impl.BooruProducer;
import com.crumby.impl.crumby.CrumbyProducer;
import com.crumby.lib.GalleryImage;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class BooruPoolProducer
extends BooruProducer {
    public BooruPoolProducer(String string2, Class class_) {
        super(string2, class_);
    }

    public static GalleryImage getPoolInfoFromElement(String object, Element element) throws Exception {
        String string2 = element.attr("id");
        object = new GalleryImage(CrumbyProducer.getSnapshot((String)object + "/show/" + string2), (String)object + "/show/" + string2, element.attr("name").replace("_", " "));
        try {
            object.setDescription(element.getElementsByTag("description").text());
            return object;
        }
        catch (NullPointerException var1_2) {
            return object;
        }
    }

    private Document getPoolXml(int n2) throws Exception {
        String string2 = this.baseUrl + "/index.xml?";
        Uri.parse((String)string2.toString());
        String string3 = BooruPoolProducer.getParameterInUrl(this.getHostUrl(), "query");
        String string4 = string2;
        if (string3 != null) {
            string4 = string2 + "&query=" + string3;
        }
        string2 = string4;
        if (n2 > 0) {
            string2 = string4 + "&page=" + (n2 + 1);
        }
        return Jsoup.parse(BooruPoolProducer.fetchUrl(string2), "", Parser.xmlParser());
    }

    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        Document document = this.getPoolXml(n2);
        int n3 = document.getElementsByTag("pool").size();
        for (int i2 = 0; i2 < n3; ++i2) {
            GalleryImage galleryImage = BooruPoolProducer.getPoolInfoFromElement(this.baseUrl, (Element)document.getElementsByTag("pool").get(i2));
            galleryImage.setPage(n2);
            arrayList.add(galleryImage);
        }
        return arrayList;
    }
}

