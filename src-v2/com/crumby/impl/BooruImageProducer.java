/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl;

import com.crumby.impl.BooruProducer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.ImageComment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class BooruImageProducer
extends SingleGalleryProducer {
    protected final String baseUrl;
    private final boolean prepend;
    private final String regexUrl;

    public BooruImageProducer(String string2, String string3) {
        this(string2, string3, false);
    }

    public BooruImageProducer(String string2, String string3, boolean bl2) {
        this.regexUrl = string3;
        this.baseUrl = string2;
        this.prepend = bl2;
    }

    @Override
    protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> arrayList) throws Exception {
        Object object = GalleryViewerFragment.matchIdFromUrl(this.regexUrl, this.getHostUrl());
        Object object2 = new GalleryImage();
        if (this.getHostImage() == null || this.getHostImage().getImageUrl() == null || this.getHostImage().getTags() == null) {
            object2 = Jsoup.parse(BooruImageProducer.fetchUrl(this.getApiUrlForImage((String)object)), "", Parser.xmlParser()).getElementsByTag("post").first();
            object2 = BooruProducer.convertElementToImage(this.baseUrl, (Element)object2, this.prepend);
        }
        Object object3 = Jsoup.parse(BooruImageProducer.fetchUrl(this.baseUrl + "/comment/index.xml?post_id=" + (String)object), "", Parser.xmlParser()).getElementsByTag("comment");
        object = new ArrayList();
        object3 = object3.iterator();
        while (object3.hasNext()) {
            Element element = (Element)object3.next();
            object.add(new ImageComment(element.attr("creator"), element.attr("body"), null));
        }
        object2.setComments((List<ImageComment>)object);
        arrayList.add((GalleryImage)object2);
    }

    protected String getApiUrlForImage(String string2) {
        return this.baseUrl + "/post/show/" + string2 + ".xml";
    }
}

