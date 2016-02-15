/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.lib.fragment.producer;

import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class GalleryScraper
extends GalleryProducer {
    final String userAgent = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X; de-de) AppleWebKit/523.10.3 (KHTML, like Gecko) Version/3.0.4 Safari/523.10";

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
        try {
            String string2;
            if (this.getHostImage().getLinkUrl().indexOf("?") == -1) {
                string2 = "?";
                return this.parseGalleryImagesHtml(Jsoup.connect(this.getHostImage().getLinkUrl() + string2 + "&" + this.pageArg + n2).userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X; de-de) AppleWebKit/523.10.3 (KHTML, like Gecko) Version/3.0.4 Safari/523.10").get());
            }
            string2 = "";
            return this.parseGalleryImagesHtml(Jsoup.connect(this.getHostImage().getLinkUrl() + string2 + "&" + this.pageArg + n2).userAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X; de-de) AppleWebKit/523.10.3 (KHTML, like Gecko) Version/3.0.4 Safari/523.10").get());
        }
        catch (IOException var2_3) {
            var2_3.printStackTrace();
            return null;
        }
        catch (NullPointerException var2_4) {
            try {
                Thread.sleep(6000);
                return this.fetchGalleryImages(n2);
            }
            catch (InterruptedException var2_6) {
                var2_6.printStackTrace();
                return null;
            }
        }
    }

    protected abstract ArrayList<GalleryImage> parseGalleryImagesHtml(Document var1) throws NullPointerException;
}

