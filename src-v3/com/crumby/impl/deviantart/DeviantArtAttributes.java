package com.crumby.impl.deviantart;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.ExtraAttributes;
import com.crumby.lib.GalleryImage;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DeviantArtAttributes extends ExtraAttributes {
    private String htmlDescription;
    private ArrayList<GalleryImage> similarImages;

    public DeviantArtAttributes(Document doc, String htmlDescription) {
        this(htmlDescription);
        Elements deviantArtPreview = doc.getElementsByClass("deviation-mlt-preview-body");
        if (!deviantArtPreview.isEmpty()) {
            Element preview = deviantArtPreview.get(deviantArtPreview.size() - 1);
            this.similarImages = new ArrayList();
            Iterator i$ = preview.getElementsByTag("a").iterator();
            while (i$.hasNext()) {
                GalleryImage image = DeviantArtProducer.convertLinkToImage((Element) i$.next(), true);
                if (!(image == null || image.getThumbnailUrl() == null || image.getThumbnailUrl().equals(UnsupportedUrlFragment.DISPLAY_NAME))) {
                    this.similarImages.add(image);
                }
            }
        }
    }

    public DeviantArtAttributes(ArrayList<GalleryImage> images) {
        this.similarImages = images;
    }

    public DeviantArtAttributes(String htmlDescription) {
        this.htmlDescription = htmlDescription;
    }

    public ArrayList<GalleryImage> getSimilarImages() {
        return this.similarImages;
    }

    public String getHtmlDescription() {
        return this.htmlDescription;
    }
}
