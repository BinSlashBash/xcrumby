package com.crumby.impl.pururin;

import com.crumby.impl.device.DeviceFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;
import java.util.ArrayList;

public class PururinThumbsFragment extends GalleryGridFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837613;
    public static final String BREADCRUMB_NAME = "gallery";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;

    /* renamed from: com.crumby.impl.pururin.PururinThumbsFragment.1 */
    class C12821 extends UniversalProducer {
        private boolean done;

        C12821() {
        }

        protected String getScriptName() {
            return PururinThumbsFragment.class.getSimpleName();
        }

        protected String getBaseUrl() {
            return PururinFragment.BASE_URL;
        }

        protected String getRegexForMatchingId() {
            return PururinThumbsFragment.REGEX_URL;
        }

        protected ArrayList<GalleryImage> fetchGalleryImages(int pageNumber) throws Exception {
            if (!this.done) {
                return super.fetchGalleryImages(pageNumber);
            }
            onFinishedDownloading(new ArrayList(), false);
            return new ArrayList();
        }

        protected void onFinishedDownloading(ArrayList<GalleryImage> galleryImages, boolean updateHostImageViews) {
            if (!(galleryImages == null || galleryImages.isEmpty())) {
                this.done = PururinThumbsFragment.BREADCRUMB_ALT_NAME;
            }
            super.onFinishedDownloading(galleryImages, updateHostImageViews);
        }
    }

    static {
        REGEX_URL = "http://pururin.com/gallery/" + CAPTURE_NUMERIC_REPEATING + DeviceFragment.REGEX_URL;
        BREADCRUMB_PARENT_CLASS = PururinFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new C12821();
    }
}
