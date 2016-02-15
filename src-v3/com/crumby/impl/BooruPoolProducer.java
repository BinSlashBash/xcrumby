package com.crumby.impl;

import android.net.Uri;
import com.crumby.impl.crumby.CrumbyProducer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.impl.danbooru.DanbooruPoolGalleryFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.plus.PlusShare;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

public class BooruPoolProducer extends BooruProducer {
    public BooruPoolProducer(String baseUrl, Class topLevelClass) {
        super(baseUrl, topLevelClass);
    }

    private Document getPoolXml(int pageNumber) throws Exception {
        String url = this.baseUrl + "/index.xml?";
        Uri uri = Uri.parse(url.toString());
        String query = GalleryProducer.getParameterInUrl(getHostUrl(), "query");
        if (query != null) {
            url = url + "&query=" + query;
        }
        if (pageNumber > 0) {
            url = url + "&page=" + (pageNumber + 1);
        }
        return Jsoup.parse(GalleryProducer.fetchUrl(url), UnsupportedUrlFragment.DISPLAY_NAME, Parser.xmlParser());
    }

    public static GalleryImage getPoolInfoFromElement(String baseUrl, Element el) throws Exception {
        String id = el.attr("id");
        GalleryImage galImg = new GalleryImage(CrumbyProducer.getSnapshot(baseUrl + "/show/" + id), baseUrl + "/show/" + id, el.attr("name").replace("_", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR));
        try {
            galImg.setDescription(el.getElementsByTag(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION).text());
        } catch (NullPointerException e) {
        }
        return galImg;
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        ArrayList<GalleryImage> galleryImages = new ArrayList();
        Document doc = getPoolXml(pageNumber);
        int size = doc.getElementsByTag(DanbooruPoolGalleryFragment.BREADCRUMB_NAME).size();
        for (int i = 0; i < size; i++) {
            GalleryImage galleryImage = getPoolInfoFromElement(this.baseUrl, doc.getElementsByTag(DanbooruPoolGalleryFragment.BREADCRUMB_NAME).get(i));
            galleryImage.setPage(pageNumber);
            galleryImages.add(galleryImage);
        }
        return galleryImages;
    }
}
