package com.crumby.impl.ehentai;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryScraper;
import com.google.android.gms.plus.PlusShare;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HentaiSubGalleryProducer extends GalleryScraper {
    private Map<String, GalleryImage> generatedImages;

    public void postInitialize() {
        this.pageArg = "p=";
        this.generatedImages = new HashMap();
    }

    String parseStyle(String style, String start, String end) {
        String parsed = style.substring(style.indexOf(start));
        return parsed.substring(start.length(), parsed.indexOf(end));
    }

    protected ArrayList<GalleryImage> parseGalleryImagesHtml(Document doc) {
        if (getCurrentPage() == 1) {
            Document document = doc;
            setGalleryMetadata(document.getElementsByTag(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE).get(0).html(), UnsupportedUrlFragment.DISPLAY_NAME);
        }
        ArrayList<GalleryImage> images = new ArrayList();
        String str = "gdtm";
        int same = 0;
        Iterator i$ = doc.getElementById("gdt").getElementsByClass(r18).iterator();
        while (i$.hasNext()) {
            Element el = (Element) i$.next();
            String styleAttribute = el.child(0).attr("style");
            String offsetString = styleAttribute.substring(styleAttribute.indexOf("background"));
            String thumbnailUrl = parseStyle(styleAttribute, "url(", ")");
            GalleryImage galleryImage = new GalleryImage(thumbnailUrl, el.child(0).child(0).attr("href"), null, Integer.parseInt(parseStyle(styleAttribute, "width:", "px")), Integer.parseInt(parseStyle(styleAttribute, "height:", "px")), Integer.parseInt(parseStyle(offsetString.substring(offsetString.indexOf(")")), "-", "px")));
            galleryImage.setReload(true);
            if (this.generatedImages.get(thumbnailUrl) != null) {
                same++;
            } else {
                this.generatedImages.put(thumbnailUrl, galleryImage);
            }
            images.add(galleryImage);
        }
        if (same == images.size()) {
            return null;
        }
        return images;
    }
}
