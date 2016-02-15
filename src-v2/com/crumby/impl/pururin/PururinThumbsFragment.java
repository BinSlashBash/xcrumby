/*
 * Decompiled with CFR 0_110.
 */
package com.crumby.impl.pururin;

import com.crumby.impl.pururin.PururinFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;
import java.util.ArrayList;

public class PururinThumbsFragment
extends GalleryGridFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837613;
    public static final String BREADCRUMB_NAME = "gallery";
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;

    static {
        REGEX_URL = "http://pururin.com/gallery/" + CAPTURE_NUMERIC_REPEATING + "/.*";
        BREADCRUMB_PARENT_CLASS = PururinFragment.class;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new UniversalProducer(){
            private boolean done;

            @Override
            protected ArrayList<GalleryImage> fetchGalleryImages(int n2) throws Exception {
                if (this.done) {
                    this.onFinishedDownloading(new ArrayList<GalleryImage>(), false);
                    return new ArrayList<GalleryImage>();
                }
                return super.fetchGalleryImages(n2);
            }

            @Override
            protected String getBaseUrl() {
                return "http://pururin.com";
            }

            @Override
            protected String getRegexForMatchingId() {
                return PururinThumbsFragment.REGEX_URL;
            }

            @Override
            protected String getScriptName() {
                return PururinThumbsFragment.class.getSimpleName();
            }

            @Override
            protected void onFinishedDownloading(ArrayList<GalleryImage> arrayList, boolean bl2) {
                if (arrayList != null && !arrayList.isEmpty()) {
                    this.done = true;
                }
                super.onFinishedDownloading(arrayList, bl2);
            }
        };
    }

}

