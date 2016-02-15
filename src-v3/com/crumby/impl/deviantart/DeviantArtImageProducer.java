package com.crumby.impl.deviantart;

import android.net.Uri;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import com.google.android.gms.plus.PlusShare;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DeviantArtImageProducer extends SingleGalleryProducer {
    protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> galleryImages) throws Exception {
        Document doc = Jsoup.parse(GalleryProducer.legacyfetchUrl(getHostUrl()));
        int index = 0;
        if (doc.getElementsByTag("h1").size() > 1) {
            index = 1;
        }
        String title = doc.getElementsByTag("h1").get(index).getElementsByTag("a").text();
        String description = doc.getElementsByClass("dev-description").first().getElementsByClass("text").html();
        GalleryImage image = new GalleryImage(null, null, title);
        String imageUrl = "http://st.deviantart.net/minish/main/errors/fella-not-found.png";
        JsonObject element = JSON_PARSER.parse(GalleryProducer.legacyfetchUrl("http://backend.deviantart.com/oembed?url=" + Uri.encode(getHostUrl()))).getAsJsonObject();
        image.setImageUrl(element.get(PlusShare.KEY_CALL_TO_ACTION_URL).getAsString());
        image.setWidth(element.get("width").getAsInt());
        image.setHeight(element.get("height").getAsInt());
        if (description == null) {
            description = UnsupportedUrlFragment.DISPLAY_NAME;
        }
        image.setAttributes(new DeviantArtAttributes(doc, description));
        galleryImages.add(image);
    }
}
