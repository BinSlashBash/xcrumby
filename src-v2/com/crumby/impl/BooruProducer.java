/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.crumby.impl;

import android.net.Uri;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class BooruProducer
extends GalleryProducer {
    protected final String baseUrl;
    private Set<String> filteredBlacklist;
    protected boolean prepend;
    private int skipTags = 0;
    protected final Class topLevelClass;

    public BooruProducer(String string2, Class class_) {
        this.baseUrl = string2;
        this.topLevelClass = class_;
    }

    public BooruProducer(String string2, Class class_, boolean bl2) {
        this(string2, class_);
        this.prepend = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean checkForTags(String[] arrstring, String string2) {
        if (arrstring == null || Arrays.binarySearch(arrstring, string2) < 0) {
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static GalleryImage convertElementToImage(String object, Element object2, boolean bl2) throws Exception {
        int n2;
        String string2;
        String string3;
        String string4;
        String string5;
        int n3;
        int n4;
        string2 = string5 = object2.attr("file_url");
        if (bl2) {
            string2 = (String)object + string5;
        }
        string3 = object2.attr("description");
        n2 = 100;
        n3 = 100;
        try {
            n2 = n4 = Integer.parseInt(object2.attr("width"));
        }
        catch (NumberFormatException var7_5) {}
        try {
            n3 = n4 = Integer.parseInt(object2.attr("height"));
        }
        catch (NumberFormatException var7_4) {}
        string5 = string4 = object2.attr("preview_url");
        if (bl2) {
            string5 = (String)object + string4;
        }
        object = new GalleryImage(string5, (String)object + "/post/show/" + object2.attr("id"), null, n2, n3);
        object.setImageUrl(string2);
        object2 = object2.attr("tags");
        if (object2 != null) {
            object.setTags(object2.split(" "));
        }
        object.setDescription(string3);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Document getXml(int n2) throws Exception {
        String string2;
        Object object;
        String string3;
        block6 : {
            object = BooruProducer.getQueryParameter(Uri.parse((String)this.getHostUrl()), this.getHostUrl(), "tags");
            string2 = string3 = this.baseUrl + "/post/index.xml?";
            if (n2 > 0) {
                string2 = string3 + "&page=" + (n2 + 1);
            }
            if (object != null) {
                string3 = object;
                if (!object.equals("")) break block6;
            }
            string3 = FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(this.topLevelClass) ? "rating:safe" : "";
        }
        this.skipTags = 6 - string3.split(" ").length;
        n2 = 0;
        this.filteredBlacklist = new LinkedHashSet<String>();
        object = GalleryViewer.BLACK_LISTED_TAGS.iterator();
        while (object.hasNext()) {
            String string4 = (String)object.next();
            if (n2 >= this.skipTags) {
                this.filteredBlacklist.add(string4);
            }
            ++n2;
        }
        return Jsoup.parse(BooruProducer.fetchUrl(string2 + "&tags=" + Uri.encode((String)new StringBuilder().append(string3).append(GalleryViewer.getBlacklist(this.skipTags)).toString())), "", Parser.xmlParser());
    }

    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        Document document = this.getXml(n2);
        ArrayList<GalleryImage> arrayList = new ArrayList<GalleryImage>();
        for (Element element : document.getElementsByTag("post")) {
            GalleryImage galleryImage = BooruProducer.convertElementToImage(this.baseUrl, element, this.prepend);
            if (Boolean.parseBoolean(element.attr("has_comments"))) {
                galleryImage.setReload(true);
            }
            arrayList.add(galleryImage);
        }
        return arrayList;
    }

    @Override
    protected void filterOutUndesiredImages(ArrayList<GalleryImage> arrayList) {
        ArrayList<GalleryImage> arrayList2 = new ArrayList<GalleryImage>();
        if (this.filteredBlacklist == null) {
            return;
        }
        block0 : for (GalleryImage galleryImage : arrayList) {
            if (galleryImage.getTags() == null) continue;
            for (String string2 : this.filteredBlacklist) {
                if (!this.checkForTags(galleryImage.getTags(), string2)) continue;
                arrayList2.add(galleryImage);
                continue block0;
            }
        }
        arrayList.removeAll(arrayList2);
        super.filterOutUndesiredImages(arrayList);
    }
}

