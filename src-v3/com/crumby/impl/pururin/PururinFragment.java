package com.crumby.impl.pururin;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.impl.crumby.CrumbyGalleryFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.Iterator;

public class PururinFragment extends GalleryGridFragment {
    public static final String BASE_URL = "http://pururin.com";
    public static final int BREADCRUMB_ICON = 2130837680;
    public static final String BREADCRUMB_NAME = "pururin";
    public static final String DISPLAY_NAME = "Pururin";
    public static final String REGEX_BASE;
    public static final String REGEX_URL;
    public static final String ROOT_NAME = "pururin.com";
    public static final int SEARCH_FORM_ID = 2130903094;

    /* renamed from: com.crumby.impl.pururin.PururinFragment.1 */
    class C12811 extends UniversalProducer {
        private String tagId;

        C12811() {
        }

        protected ArrayList<GalleryImage> getImagesFromJson(JsonNode node) throws Exception {
            try {
                this.tagId = node.get("tagID").asText();
            } catch (NullPointerException e) {
            }
            return super.getImagesFromJson(node);
        }

        protected void onFinishedDownloading(ArrayList<GalleryImage> galleryImages, boolean updateHostImageViews) {
            for (GalleryImage image : getImages()) {
                Iterator i$ = galleryImages.iterator();
                while (i$.hasNext()) {
                    GalleryImage newImage = (GalleryImage) i$.next();
                    if (newImage.getLinkUrl() != null && newImage.getLinkUrl().equals(image.getLinkUrl())) {
                        super.onFinishedDownloading(new ArrayList(), updateHostImageViews);
                        return;
                    }
                }
            }
            super.onFinishedDownloading(galleryImages, updateHostImageViews);
        }

        protected String getRegexForMatchingId() {
            return null;
        }

        protected String getScriptName() {
            return PururinFragment.class.getSimpleName();
        }

        protected String getBaseUrl() {
            return PururinFragment.BASE_URL;
        }

        public String getMatchingId() {
            return this.tagId;
        }
    }

    static {
        REGEX_BASE = GalleryViewerFragment.buildBasicRegexBase(ROOT_NAME);
        REGEX_URL = REGEX_BASE + CrumbyGalleryFragment.REGEX_URL;
    }

    protected int getHeaderLayout() {
        return C0065R.layout.omniform_container;
    }

    protected void setupHeaderLayout(ViewGroup header) {
        ((OmniformContainer) header).showAsInGrid(getImage().getLinkUrl());
    }

    public String getSearchPrefix() {
        return BREADCRUMB_NAME;
    }

    public String getSearchArgumentName() {
        return "q";
    }

    public void setImage(GalleryImage image) {
        super.setImage(image);
        if (image.getLinkUrl().contains("pururin.com?q=")) {
            image.setLinkUrl(image.getLinkUrl().replace("pururin.com?q=", "pururin.com/search?q="));
        }
    }

    public void applyGalleryImageChange(View view, GalleryImage image, int position) {
        postUrlChangeToBusWithProducer(image);
    }

    protected GalleryProducer createProducer() {
        return new C12811();
    }
}
