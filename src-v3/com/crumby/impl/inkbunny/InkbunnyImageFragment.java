package com.crumby.impl.inkbunny;

import android.net.Uri;
import com.crumby.impl.BooruImageFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import java.util.ArrayList;

public class InkbunnyImageFragment extends BooruImageFragment {
    public static final String API_ROOT = "https://inkbunny.net/api_submissions.php?";
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String DISPLAY_NAME = "inkbunny";
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;

    /* renamed from: com.crumby.impl.inkbunny.InkbunnyImageFragment.1 */
    class C12801 extends SingleGalleryProducer {
        C12801() {
        }

        protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> arrayList) throws Exception {
            InkbunnyProducer.jsonObjectToImage(JSON_PARSER.parse(GalleryProducer.fetchUrl(InkbunnyImageFragment.API_ROOT + "&sid=" + InkbunnyProducer.SESSION_ID + "&submission_ids=" + GalleryViewerFragment.matchIdFromUrl(InkbunnyImageFragment.REGEX_URL, getHostUrl()))).getAsJsonObject().get("submissions").getAsJsonArray().get(0).getAsJsonObject());
        }
    }

    static {
        REGEX_URL = InkbunnyFragment.REGEX_BASE + "/submissionview\\.php\\?id=([0-9]+)*";
        BREADCRUMB_PARENT_CLASS = InkbunnyFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new C12801();
    }

    protected String getTagUrl(String z) {
        return "https://inkbunny.net/api_search.php?sid=" + InkbunnyProducer.SESSION_ID + "&text=" + Uri.encode(z);
    }
}
