/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl;

import com.crumby.impl.BooruPoolProducer;
import com.crumby.impl.BooruProducer;
import com.crumby.impl.e621.e621PoolGalleryFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class BooruPoolGalleryProducer
extends BooruProducer {
    private String poolID;
    private String postUrl;
    private String regexUrl;

    public BooruPoolGalleryProducer(String string2, Class class_, String string3, String string4, boolean bl2) {
        super(string2, class_, bl2);
        this.regexUrl = string3;
        this.postUrl = string4;
    }

    private Document getPoolXml(int n2) throws Exception {
        String string2;
        String string3 = string2 = this.baseUrl + ".xml?&id=" + this.poolID;
        if (n2 > 0) {
            string3 = string2 + "&page=" + (n2 + 1);
        }
        return Jsoup.parse(BooruPoolGalleryProducer.fetchUrl(string3), "", Parser.xmlParser());
    }

    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        this.poolID = GalleryViewerFragment.matchIdFromUrl(this.regexUrl, this.getHostUrl());
        Document document = this.getPoolXml(n2);
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        for (Element element : document.getElementsByTag("post")) {
            arrayList.add(BooruProducer.convertElementToImage(this.postUrl, element, this.prepend));
        }
        return arrayList;
    }

    @Override
    protected boolean fetchMetadata() throws Exception {
        if (this.getHostImage().getTitle() == null || this.getHostImage().getDescription() == null) {
            Object object = Jsoup.parse(BooruPoolGalleryProducer.fetchUrl(this.baseUrl + "/" + e621PoolGalleryFragment.matchIdFromUrl(this.getHostUrl()) + ".xml"), "", Parser.xmlParser());
            object = BooruPoolProducer.getPoolInfoFromElement(this.baseUrl, object.getElementsByTag("pool").first());
            this.setGalleryMetadata(object.getTitle(), object.getDescription());
            return true;
        }
        return false;
    }
}

