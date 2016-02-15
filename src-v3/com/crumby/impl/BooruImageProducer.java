package com.crumby.impl;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.ImageComment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class BooruImageProducer extends SingleGalleryProducer {
    protected final String baseUrl;
    private final boolean prepend;
    private final String regexUrl;

    public BooruImageProducer(String baseUrl, String regexUrl, boolean prepend) {
        this.regexUrl = regexUrl;
        this.baseUrl = baseUrl;
        this.prepend = prepend;
    }

    public BooruImageProducer(String baseUrl, String regexUrl) {
        this(baseUrl, regexUrl, false);
    }

    protected String getApiUrlForImage(String id) {
        return this.baseUrl + "/post/show/" + id + ".xml";
    }

    protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> galleryImages) throws Exception {
        String id = GalleryViewerFragment.matchIdFromUrl(this.regexUrl, getHostUrl());
        GalleryImage image = new GalleryImage();
        if (getHostImage() == null || getHostImage().getImageUrl() == null || getHostImage().getTags() == null) {
            image = BooruProducer.convertElementToImage(this.baseUrl, Jsoup.parse(GalleryProducer.fetchUrl(getApiUrlForImage(id)), UnsupportedUrlFragment.DISPLAY_NAME, Parser.xmlParser()).getElementsByTag("post").first(), this.prepend);
        }
        Elements comments = Jsoup.parse(GalleryProducer.fetchUrl(this.baseUrl + "/comment/index.xml?post_id=" + id), UnsupportedUrlFragment.DISPLAY_NAME, Parser.xmlParser()).getElementsByTag("comment");
        ArrayList<ImageComment> imageComments = new ArrayList();
        Iterator i$ = comments.iterator();
        while (i$.hasNext()) {
            Element comment = (Element) i$.next();
            imageComments.add(new ImageComment(comment.attr("creator"), comment.attr("body"), null));
        }
        image.setComments(imageComments);
        galleryImages.add(image);
    }
}
