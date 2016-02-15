package com.crumby.impl.ehentai;

import com.crumby.impl.device.DeviceFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryScraper;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HentaiGalleryProducer extends GalleryScraper {
    protected void postInitialize() {
        this.pageArg = "page=";
    }

    protected ArrayList<GalleryImage> parseGalleryImagesHtml(Document doc) throws NullPointerException {
        ArrayList<GalleryImage> images = new ArrayList();
        int i = 0;
        Iterator i$ = doc.getElementsByClass("it2").iterator();
        while (i$.hasNext()) {
            Element el = (Element) i$.next();
            String[] a = el.html().split("~", 4);
            String galleryUrl = el.parent().getElementsByClass("it5").first().child(0).attr("href");
            if (a.length == 4 && a[0].equals("init")) {
                int i2 = i + 1;
                GalleryImage galleryImage = new GalleryImage("http://" + a[1] + DeviceFragment.REGEX_BASE + a[2], galleryUrl, i + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + a[3]);
                galleryImage.setLinksToGallery(true);
                images.add(galleryImage);
                i = i2;
            }
        }
        return images;
    }
}
