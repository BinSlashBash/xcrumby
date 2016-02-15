package com.crumby.impl.danbooru;

import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DanbooruPoolGalleryProducer extends DanbooruGalleryProducer {
    private String poolId;

    protected GalleryImage getGalleryImage(JsonObject galleryImageObj) {
        GalleryImage image = super.getGalleryImage(galleryImageObj);
        image.setLinkUrl(deriveUrl(galleryImageObj.get("id"), "/posts/") + "?pool_id=" + this.poolId);
        image.setImageUrl(deriveUrl(galleryImageObj.get("large_file_url"), UnsupportedUrlFragment.DISPLAY_NAME));
        return image;
    }

    protected boolean fetchMetadata() throws IOException {
        if (getHostImage().getTitle() != null || getHostImage().getDescription() != null) {
            return false;
        }
        getHostImage().copy(DanbooruPoolProducer.getGalleryImage(JSON_PARSER.parse(GalleryProducer.fetchUrl("http://danbooru.donmai.us/pools/" + this.poolId + ".json")).getAsJsonObject()));
        return true;
    }

    public URL convertUrlToApi(String url) {
        String apiUrlString = DanbooruGalleryProducer.API_URL;
        this.poolId = DanbooruPoolGalleryFragment.matchIdFromUrl(url.toString());
        try {
            return new URL(apiUrlString + "tags=pool:" + this.poolId);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
