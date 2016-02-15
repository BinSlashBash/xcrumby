package com.crumby.impl.deviantart;

import android.net.Uri;
import com.crumby.CrDb;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.android.gms.plus.PlusShare;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class DeviantArtProducer extends GalleryProducer {
    protected static final String API_URL = "http://backend.deviantart.com/";
    protected static final String RSS_API_URL = "http://backend.deviantart.com/rss.xml?";

    public static String modifyDescription(String description) {
        if (description == null || !description.contains("<img")) {
            return description;
        }
        return description.substring(0, description.lastIndexOf("<img"));
    }

    protected String getRssUrlForSearch(int pageNumber, String query) {
        return "http://backend.deviantart.com/rss.xml?offset=" + (pageNumber * 60) + "&q=" + Uri.encode(query);
    }

    protected String getRssUrl(int pageNumber) {
        String search = GalleryProducer.getParameterInUrl(getHostUrl(), "q");
        if (search != null) {
            return getRssUrlForSearch(pageNumber, search);
        }
        return "http://backend.deviantart.com/rss.xml?offset=" + (pageNumber * 24) + "&order=67108864";
    }

    protected void getImagesFromReallyStupidSyndication(ArrayList<GalleryImage> galleryImages, int pageNumber) throws Exception {
        JsonNode parentNode = new ObjectMapper().readTree(XML.toJSONObject(GalleryProducer.legacyfetchUrl(getRssUrl(pageNumber))).toString()).findParent("item");
        if (parentNode != null) {
            if (getHostImage().getTitle() == null && getHostImage().getDescription() == null) {
                setGalleryMetadata(parentNode.get(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE).asText(), parentNode.get(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION).asText());
            }
            if (parentNode.get("item") != null) {
                Iterator it = ((ArrayNode) parentNode.get("item")).iterator();
                while (it.hasNext()) {
                    JsonNode deviantNode = (JsonNode) it.next();
                    JsonNode imageNode = deviantNode.get("media:content");
                    ArrayNode thumbnailsNode = (ArrayNode) deviantNode.get("media:thumbnail");
                    int thumbnailHeight = 0;
                    String thumbnailUrl = UnsupportedUrlFragment.DISPLAY_NAME;
                    if (thumbnailsNode != null) {
                        int height;
                        Iterator i$ = thumbnailsNode.iterator();
                        while (i$.hasNext()) {
                            JsonNode thumbnailNode = (JsonNode) i$.next();
                            height = thumbnailNode.get("height").asInt();
                            if (height > thumbnailHeight) {
                                thumbnailHeight = height;
                                thumbnailUrl = thumbnailNode.get(PlusShare.KEY_CALL_TO_ACTION_URL).asText();
                            }
                        }
                        int width = 0;
                        height = 0;
                        if (imageNode.get("width") != null) {
                            width = imageNode.get("width").asInt();
                            height = imageNode.get("height").asInt();
                        }
                        GalleryImage image = new GalleryImage(thumbnailUrl, deviantNode.get("link").asText(), deviantNode.get(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE).asText(), width, height);
                        image.setImageUrl(imageNode.get(PlusShare.KEY_CALL_TO_ACTION_URL).asText());
                        image.setReload(true);
                        image.setAttributes(new DeviantArtAttributes(modifyDescription(deviantNode.get(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION).asText())));
                        galleryImages.add(image);
                    }
                }
            }
        }
    }

    protected String getDefaultUrl(int pageNumber) {
        return "http://www.deviantart.com/?order=67108864&offset=" + (pageNumber * 24);
    }

    protected void getImagesFromWebPage(ArrayList<GalleryImage> galleryImages, int pageNumber) throws Exception {
        Element moreLikeThis = Jsoup.parse(GalleryProducer.fetchUrl(getDefaultUrl(pageNumber))).getElementsByClass("page-results").first();
        if (moreLikeThis != null) {
            Iterator i$ = moreLikeThis.children().iterator();
            while (i$.hasNext()) {
                try {
                    GalleryImage image = convertLinkToImage(((Element) i$.next()).getElementsByTag("a").first(), false);
                    if (image != null) {
                        galleryImages.add(image);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
        ArrayList<GalleryImage> galleryImages = new ArrayList();
        if (pageNumber == 0 || GalleryProducer.getParameterInUrl(getHostUrl(), "q") != null) {
            getImagesFromReallyStupidSyndication(galleryImages, pageNumber);
        } else {
            getImagesFromWebPage(galleryImages, pageNumber);
        }
        return galleryImages;
    }

    public static GalleryImage convertLinkToImage(Element link, boolean ignoreThumb) {
        if (!link.hasClass("thumb") && !ignoreThumb) {
            return null;
        }
        Element imageElement = link.getElementsByTag("img").first();
        if (imageElement == null) {
            return null;
        }
        int width;
        int height;
        String imageUrl = link.attr("data-super-full-img");
        try {
            width = Integer.parseInt(link.attr("data-super-full-width"));
            height = Integer.parseInt(link.attr("data-super-full-height"));
        } catch (NumberFormatException e) {
            try {
                width = Integer.parseInt(link.attr("data-super-width"));
                height = Integer.parseInt(link.attr("data-super-height"));
                imageUrl = link.attr("data-super-img");
            } catch (NumberFormatException e2) {
                width = 0;
                height = 0;
                imageUrl = imageElement.attr("src");
            }
        }
        GalleryImage image = new GalleryImage(imageElement.attr("src"), link.attr("href"), link.attr(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE), width, height);
        CrDb.m0d("deviantart producer", "===================");
        CrDb.m0d("deviantart producer", link.attr("data-super-full-img"));
        CrDb.m0d("deviantart producer", link.attr("data-super-img") + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        CrDb.m0d("deviantart producer", imageUrl);
        CrDb.m0d("deviantart producer", "===================");
        image.setImageUrl(imageUrl);
        image.setReload(true);
        return image;
    }
}
