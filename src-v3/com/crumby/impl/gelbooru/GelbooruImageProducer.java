package com.crumby.impl.gelbooru;

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

public class GelbooruImageProducer extends SingleGalleryProducer {
    private String apiRoot;
    private String baseUrl;
    private String regexUrl;

    public GelbooruImageProducer(String baseUrl, String regexUrl, String apiRoot) {
        this.baseUrl = baseUrl;
        this.regexUrl = regexUrl;
        this.apiRoot = apiRoot;
    }

    protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> galleryImages) throws Exception {
        String id = GalleryViewerFragment.matchIdFromUrl(this.regexUrl, getHostUrl());
        GalleryImage image = new GalleryImage();
        if (getHostImage() == null || getHostImage().getImageUrl() == null || getHostImage().getTags() == null) {
            image = GelbooruProducer.convertElementToImage(Jsoup.parse(GalleryProducer.fetchUrl(this.apiRoot + "&id=" + id), UnsupportedUrlFragment.DISPLAY_NAME, Parser.xmlParser()).getElementsByTag("post").first(), this.baseUrl);
        }
        Elements comments = Jsoup.parse(GalleryProducer.fetchUrl(this.apiRoot + "&s=comment&post_id=" + id), UnsupportedUrlFragment.DISPLAY_NAME, Parser.xmlParser()).getElementsByTag("comment");
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
