package com.crumby.impl.imgur;

import com.crumby.lib.fragment.producer.GalleryProducer;
import com.google.android.gms.plus.PlusShare;
import com.google.gson.JsonObject;
import java.io.IOException;
import uk.co.senab.photoview.IPhotoView;

public class ImgurSubredditProducer extends ImgurProducer {
    String subredditId;

    protected boolean fetchMetadata() throws IOException {
        JsonObject subreddit = JSON_PARSER.parse(GalleryProducer.fetchUrl("http://www.reddit.com/r/" + this.subredditId + "/about.json")).getAsJsonObject().get("data").getAsJsonObject();
        String description = subreddit.get(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION).getAsString();
        setGalleryMetadata(subreddit.get(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE).getAsString(), description.substring(0, Math.min(IPhotoView.DEFAULT_ZOOM_DURATION, description.length())));
        return false;
    }

    public void postInitialize() {
        this.subredditId = ImgurSubredditFragment.matchIdFromUrl(getHostUrl());
    }

    protected String getApiUrl(int pageNumber) {
        return "https://imgur-apiv3.p.mashape.com/3/gallery/r/" + this.subredditId + "/time/" + pageNumber;
    }
}
