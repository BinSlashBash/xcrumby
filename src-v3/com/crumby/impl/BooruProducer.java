package com.crumby.impl;

import android.net.Uri;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.plus.PlusShare;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

public class BooruProducer extends GalleryProducer {
    protected final String baseUrl;
    private Set<String> filteredBlacklist;
    protected boolean prepend;
    private int skipTags;
    protected final Class topLevelClass;

    public BooruProducer(String baseUrl, Class topLevelClass) {
        this.skipTags = 0;
        this.baseUrl = baseUrl;
        this.topLevelClass = topLevelClass;
    }

    public BooruProducer(String baseUrl, Class topLevelClass, boolean prepend) {
        this(baseUrl, topLevelClass);
        this.prepend = prepend;
    }

    public static GalleryImage convertElementToImage(String baseUrl, Element el, boolean prepend) throws Exception {
        String fileURL = el.attr("file_url");
        if (prepend) {
            fileURL = baseUrl + fileURL;
        }
        String descrip = el.attr(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
        int width = 100;
        int height = 100;
        try {
            width = Integer.parseInt(el.attr("width"));
        } catch (NumberFormatException e) {
        }
        try {
            height = Integer.parseInt(el.attr("height"));
        } catch (NumberFormatException e2) {
        }
        String thumbnailUrl = el.attr("preview_url");
        if (prepend) {
            thumbnailUrl = baseUrl + thumbnailUrl;
        }
        GalleryImage tempImage = new GalleryImage(thumbnailUrl, baseUrl + "/post/show/" + el.attr("id"), null, width, height);
        tempImage.setImageUrl(fileURL);
        String tags = el.attr("tags");
        if (tags != null) {
            tempImage.setTags(tags.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR));
        }
        tempImage.setDescription(descrip);
        return tempImage;
    }

    private boolean checkForTags(String[] tags, String blacklistedTag) {
        if (tags != null && Arrays.binarySearch(tags, blacklistedTag) >= 0) {
            return true;
        }
        return false;
    }

    private Document getXml(int pageNumber) throws Exception {
        String tags = GalleryProducer.getQueryParameter(Uri.parse(getHostUrl()), getHostUrl(), "tags");
        String url = this.baseUrl + "/post/index.xml?";
        if (pageNumber > 0) {
            url = url + "&page=" + (pageNumber + 1);
        }
        if (tags == null || tags.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
            if (FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(this.topLevelClass)) {
                tags = "rating:safe";
            } else {
                tags = UnsupportedUrlFragment.DISPLAY_NAME;
            }
        }
        this.skipTags = 6 - tags.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).length;
        int i = 0;
        this.filteredBlacklist = new LinkedHashSet();
        Iterator i$ = GalleryViewer.BLACK_LISTED_TAGS.iterator();
        while (i$.hasNext()) {
            String tag = (String) i$.next();
            if (i >= this.skipTags) {
                this.filteredBlacklist.add(tag);
            }
            i++;
        }
        return Jsoup.parse(GalleryProducer.fetchUrl(url + "&tags=" + Uri.encode(tags + GalleryViewer.getBlacklist(this.skipTags))), UnsupportedUrlFragment.DISPLAY_NAME, Parser.xmlParser());
    }

    protected void filterOutUndesiredImages(ArrayList<GalleryImage> galleryImages) {
        List<GalleryImage> remove = new ArrayList();
        if (this.filteredBlacklist != null) {
            Iterator it = galleryImages.iterator();
            while (it.hasNext()) {
                GalleryImage image = (GalleryImage) it.next();
                if (image.getTags() != null) {
                    for (String blacklistedTag : this.filteredBlacklist) {
                        if (checkForTags(image.getTags(), blacklistedTag)) {
                            remove.add(image);
                            break;
                        }
                    }
                }
            }
            galleryImages.removeAll(remove);
            super.filterOutUndesiredImages(galleryImages);
        }
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        Document doc = getXml(pageNumber);
        ArrayList<GalleryImage> images = new ArrayList();
        Iterator i$ = doc.getElementsByTag("post").iterator();
        while (i$.hasNext()) {
            Element post = (Element) i$.next();
            GalleryImage anotherImage = convertElementToImage(this.baseUrl, post, this.prepend);
            if (Boolean.parseBoolean(post.attr("has_comments"))) {
                anotherImage.setReload(true);
            }
            images.add(anotherImage);
        }
        return images;
    }
}
