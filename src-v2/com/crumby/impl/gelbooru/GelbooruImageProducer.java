/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.gelbooru;

import com.crumby.impl.gelbooru.GelbooruProducer;
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

public class GelbooruImageProducer
extends SingleGalleryProducer {
    private String apiRoot;
    private String baseUrl;
    private String regexUrl;

    public GelbooruImageProducer(String string2, String string3, String string4) {
        this.baseUrl = string2;
        this.regexUrl = string3;
        this.apiRoot = string4;
    }

    @Override
    protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> arrayList) throws Exception {
        Object object = GalleryViewerFragment.matchIdFromUrl(this.regexUrl, this.getHostUrl());
        GalleryImage galleryImage = new GalleryImage();
        if (this.getHostImage() == null || this.getHostImage().getImageUrl() == null || this.getHostImage().getTags() == null) {
            galleryImage = GelbooruProducer.convertElementToImage(Jsoup.parse(GelbooruImageProducer.fetchUrl(this.apiRoot + "&id=" + (String)object), "", Parser.xmlParser()).getElementsByTag("post").first(), this.baseUrl);
        }
        Object object2 = Jsoup.parse(GelbooruImageProducer.fetchUrl(this.apiRoot + "&s=comment&post_id=" + (String)object), "", Parser.xmlParser()).getElementsByTag("comment");
        object = new ArrayList();
        object2 = object2.iterator();
        while (object2.hasNext()) {
            Element element = (Element)object2.next();
            object.add(new ImageComment(element.attr("creator"), element.attr("body"), null));
        }
        galleryImage.setComments((List<ImageComment>)object);
        arrayList.add(galleryImage);
    }
}

