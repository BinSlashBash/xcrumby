package com.crumby.impl.ehentai;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class HentaiSequenceProducer extends GalleryProducer {
    private boolean halt;
    private String imageUrl;

    public void postInitialize() {
        this.imageUrl = getHostImage().getLinkUrl();
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int currentPage) throws Exception {
        if (this.halt) {
            return null;
        }
        ArrayList<GalleryImage> image = new ArrayList();
        Iterator i$ = Jsoup.connect(this.imageUrl).get().getElementsByTag("img").iterator();
        while (i$.hasNext()) {
            Element imageEl = (Element) i$.next();
            if (!(imageEl.attr("style") == null || imageEl.attr("style").equals(UnsupportedUrlFragment.DISPLAY_NAME))) {
                String nextImageUrl = imageEl.parent().attr("href");
                GalleryImage galleryImage = new GalleryImage(null, null, null);
                galleryImage.setImageUrl(imageEl.attr("src"));
                if (this.imageUrl.equals(nextImageUrl)) {
                    this.halt = true;
                }
                this.imageUrl = nextImageUrl;
                image.add(galleryImage);
            }
        }
        return image;
    }
}
