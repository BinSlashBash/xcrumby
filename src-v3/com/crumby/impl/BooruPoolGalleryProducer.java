package com.crumby.impl;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.impl.danbooru.DanbooruPoolGalleryFragment;
import com.crumby.impl.device.DeviceFragment;
import com.crumby.impl.e621.e621PoolGalleryFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

public class BooruPoolGalleryProducer extends BooruProducer {
    private String poolID;
    private String postUrl;
    private String regexUrl;

    public BooruPoolGalleryProducer(String baseUrl, Class topLevelClass, String regexUrl, String postUrl, boolean prepend) {
        super(baseUrl, topLevelClass, prepend);
        this.regexUrl = regexUrl;
        this.postUrl = postUrl;
    }

    protected boolean fetchMetadata() throws Exception {
        if (getHostImage().getTitle() != null && getHostImage().getDescription() != null) {
            return false;
        }
        GalleryImage image = BooruPoolProducer.getPoolInfoFromElement(this.baseUrl, Jsoup.parse(GalleryProducer.fetchUrl(this.baseUrl + DeviceFragment.REGEX_BASE + e621PoolGalleryFragment.matchIdFromUrl(getHostUrl()) + ".xml"), UnsupportedUrlFragment.DISPLAY_NAME, Parser.xmlParser()).getElementsByTag(DanbooruPoolGalleryFragment.BREADCRUMB_NAME).first());
        setGalleryMetadata(image.getTitle(), image.getDescription());
        return true;
    }

    private Document getPoolXml(int pageNumber) throws Exception {
        String url = this.baseUrl + ".xml?&id=" + this.poolID;
        if (pageNumber > 0) {
            url = url + "&page=" + (pageNumber + 1);
        }
        return Jsoup.parse(GalleryProducer.fetchUrl(url), UnsupportedUrlFragment.DISPLAY_NAME, Parser.xmlParser());
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        this.poolID = GalleryViewerFragment.matchIdFromUrl(this.regexUrl, getHostUrl());
        Document doc = getPoolXml(pageNumber);
        ArrayList<GalleryImage> images = new ArrayList();
        Iterator i$ = doc.getElementsByTag("post").iterator();
        while (i$.hasNext()) {
            images.add(BooruProducer.convertElementToImage(this.postUrl, (Element) i$.next(), this.prepend));
        }
        return images;
    }
}
