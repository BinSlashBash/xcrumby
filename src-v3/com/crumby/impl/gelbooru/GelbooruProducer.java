package com.crumby.impl.gelbooru;

import android.net.Uri;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

public class GelbooruProducer extends GalleryProducer {
    private final String apiRoot;
    private final String baseUrl;
    private final String safeApiRoot;
    private final Class topLevelClass;

    public GelbooruProducer(String baseUrl, String apiRoot, String safeApiRoot, Class topLevelClass) {
        this.apiRoot = apiRoot;
        this.safeApiRoot = safeApiRoot;
        this.baseUrl = baseUrl;
        this.topLevelClass = topLevelClass;
    }

    public static GalleryImage convertElementToImage(Element el, String baseUrl) throws Exception {
        String fileURL = el.attr("file_url");
        GalleryImage tempImage = new GalleryImage(el.attr("preview_url"), baseUrl + "index.php?page=post&s=view&id=" + el.attr("id"), null, Integer.parseInt(el.attr("width")), Integer.parseInt(el.attr("height")));
        tempImage.setImageUrl(fileURL);
        String tags = el.attr("tags");
        if (tags != null) {
            tempImage.setTags(tags.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR));
        }
        tempImage.setDescription(el.attr("source"));
        return tempImage;
    }

    private Document getGelbooruXml(int pageNumber) throws Exception {
        String tags = GalleryProducer.getQueryParameter(Uri.parse(getHostUrl()), getHostUrl(), "tags");
        String url = this.apiRoot;
        if (tags != null && !tags.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
            url = url + "&tags=" + Uri.encode(tags + GalleryViewer.getBlacklist());
        } else if (FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(this.topLevelClass)) {
            url = this.safeApiRoot + Uri.encode(GalleryViewer.getBlacklist());
        }
        return Jsoup.parse(GalleryProducer.fetchUrl(url + "&pid=" + pageNumber), UnsupportedUrlFragment.DISPLAY_NAME, Parser.xmlParser());
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        Document doc = getGelbooruXml(pageNumber);
        ArrayList<GalleryImage> images = new ArrayList();
        Iterator i$ = doc.getElementsByTag("post").iterator();
        while (i$.hasNext()) {
            Element post = (Element) i$.next();
            GalleryImage anotherImage = convertElementToImage(post, this.baseUrl);
            if (Boolean.parseBoolean(post.attr("has_comments"))) {
                anotherImage.setReload(true);
            }
            images.add(anotherImage);
        }
        return images;
    }
}
