/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl.gelbooru;

import android.net.Uri;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class GelbooruProducer
extends GalleryProducer {
    private final String apiRoot;
    private final String baseUrl;
    private final String safeApiRoot;
    private final Class topLevelClass;

    public GelbooruProducer(String string2, String string3, String string4, Class class_) {
        this.apiRoot = string3;
        this.safeApiRoot = string4;
        this.baseUrl = string2;
        this.topLevelClass = class_;
    }

    public static GalleryImage convertElementToImage(Element element, String object) throws Exception {
        String string2 = element.attr("file_url");
        object = new GalleryImage(element.attr("preview_url"), (String)object + "index.php?page=post&s=view&id=" + element.attr("id"), null, Integer.parseInt(element.attr("width")), Integer.parseInt(element.attr("height")));
        object.setImageUrl(string2);
        string2 = element.attr("tags");
        if (string2 != null) {
            object.setTags(string2.split(" "));
        }
        object.setDescription(element.attr("source"));
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Document getGelbooruXml(int n2) throws Exception {
        String string2 = GelbooruProducer.getQueryParameter(Uri.parse((String)this.getHostUrl()), this.getHostUrl(), "tags");
        String string3 = this.apiRoot;
        if (string2 != null && !string2.equals("")) {
            string3 = string3 + "&tags=" + Uri.encode((String)new StringBuilder().append(string2).append(GalleryViewer.getBlacklist()).toString());
            return Jsoup.parse(GelbooruProducer.fetchUrl(string3 + "&pid=" + n2), "", Parser.xmlParser());
        }
        if (!FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(this.topLevelClass)) return Jsoup.parse(GelbooruProducer.fetchUrl(string3 + "&pid=" + n2), "", Parser.xmlParser());
        string3 = this.safeApiRoot + Uri.encode((String)GalleryViewer.getBlacklist());
        return Jsoup.parse(GelbooruProducer.fetchUrl(string3 + "&pid=" + n2), "", Parser.xmlParser());
    }

    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        Document document = this.getGelbooruXml(n2);
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        for (Element element : document.getElementsByTag("post")) {
            GalleryImage galleryImage = GelbooruProducer.convertElementToImage(element, this.baseUrl);
            if (Boolean.parseBoolean(element.attr("has_comments"))) {
                galleryImage.setReload(true);
            }
            arrayList.add(galleryImage);
        }
        return arrayList;
    }
}

