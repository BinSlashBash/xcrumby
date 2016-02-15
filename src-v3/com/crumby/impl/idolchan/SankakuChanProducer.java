package com.crumby.impl.idolchan;

import android.net.Uri;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.router.FragmentRouter;
import com.crumby.lib.universal.UniversalProducer;

public class SankakuChanProducer extends UniversalProducer {
    public String getHostUrl() {
        String tags = GalleryProducer.getQueryParameter(Uri.parse(super.getHostUrl()), super.getHostUrl(), "tags");
        String url = getBaseUrl() + "?tags=";
        if (tags == null && FragmentRouter.INSTANCE.wantsSafeImagesInTopLevelGallery(SankakuChanFragment.class)) {
            tags = "rating:safe";
        }
        if (!(tags == null || tags.equals(UnsupportedUrlFragment.DISPLAY_NAME))) {
            url = url + Uri.encode(tags);
        }
        return url + Uri.encode(GalleryViewer.getBlacklist());
    }

    protected String getScriptName() {
        return SankakuChanFragment.class.getSimpleName();
    }

    protected String getBaseUrl() {
        return SankakuChanFragment.BASE_URL;
    }

    protected String getRegexForMatchingId() {
        return SankakuChanFragment.REGEX_URL;
    }
}
