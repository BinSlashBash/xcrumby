/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.danbooru;

import com.crumby.impl.danbooru.DanbooruGalleryProducer;
import com.crumby.impl.danbooru.DanbooruPoolGalleryFragment;
import com.crumby.impl.danbooru.DanbooruPoolProducer;
import com.crumby.lib.GalleryImage;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DanbooruPoolGalleryProducer
extends DanbooruGalleryProducer {
    private String poolId;

    @Override
    public URL convertUrlToApi(String object) {
        this.poolId = DanbooruPoolGalleryFragment.matchIdFromUrl(object.toString());
        try {
            object = new URL("http://danbooru.donmai.us/posts.json?" + "tags=pool:" + this.poolId);
            return object;
        }
        catch (MalformedURLException var1_2) {
            var1_2.printStackTrace();
            return null;
        }
    }

    @Override
    protected boolean fetchMetadata() throws IOException {
        if (this.getHostImage().getTitle() == null && this.getHostImage().getDescription() == null) {
            GalleryImage galleryImage = DanbooruPoolProducer.getGalleryImage(JSON_PARSER.parse(DanbooruPoolGalleryProducer.fetchUrl("http://danbooru.donmai.us/pools/" + this.poolId + ".json")).getAsJsonObject());
            this.getHostImage().copy(galleryImage);
            return true;
        }
        return false;
    }

    @Override
    protected GalleryImage getGalleryImage(JsonObject jsonObject) {
        GalleryImage galleryImage = super.getGalleryImage(jsonObject);
        galleryImage.setLinkUrl(this.deriveUrl(jsonObject.get("id"), "/posts/") + "?pool_id=" + this.poolId);
        galleryImage.setImageUrl(this.deriveUrl(jsonObject.get("large_file_url"), ""));
        return galleryImage;
    }
}

