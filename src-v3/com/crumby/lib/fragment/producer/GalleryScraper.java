package com.crumby.lib.fragment.producer;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class GalleryScraper extends GalleryProducer {
    final String userAgent;

    protected abstract ArrayList<GalleryImage> parseGalleryImagesHtml(Document document) throws NullPointerException;

    public GalleryScraper() {
        this.userAgent = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X; de-de) AppleWebKit/523.10.3 (KHTML, like Gecko) Version/3.0.4 Safari/523.10";
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        ArrayList<GalleryImage> arrayList = null;
        try {
            return parseGalleryImagesHtml(Jsoup.connect(getHostImage().getLinkUrl() + (getHostImage().getLinkUrl().indexOf("?") == -1 ? "?" : UnsupportedUrlFragment.DISPLAY_NAME) + "&" + this.pageArg + pageNumber).userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X; de-de) AppleWebKit/523.10.3 (KHTML, like Gecko) Version/3.0.4 Safari/523.10").get());
        } catch (IOException e) {
            e.printStackTrace();
            return arrayList;
        } catch (NullPointerException e2) {
            try {
                Thread.sleep(6000);
                return fetchGalleryImages(pageNumber);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return arrayList;
            }
        }
    }
}
