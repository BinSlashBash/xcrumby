/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.crumby.impl.pururin;

import android.view.View;
import android.view.ViewGroup;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.universal.UniversalProducer;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;

public class PururinFragment
extends GalleryGridFragment {
    public static final String BASE_URL = "http://pururin.com";
    public static final int BREADCRUMB_ICON = 2130837680;
    public static final String BREADCRUMB_NAME = "pururin";
    public static final String DISPLAY_NAME = "Pururin";
    public static final String REGEX_BASE = PururinFragment.buildBasicRegexBase("pururin.com");
    public static final String REGEX_URL = REGEX_BASE + ".*";
    public static final String ROOT_NAME = "pururin.com";
    public static final int SEARCH_FORM_ID = 2130903094;

    @Override
    public void applyGalleryImageChange(View view, GalleryImage galleryImage, int n2) {
        this.postUrlChangeToBusWithProducer(galleryImage);
    }

    @Override
    protected GalleryProducer createProducer() {
        return new UniversalProducer(){
            private String tagId;

            @Override
            protected String getBaseUrl() {
                return "http://pururin.com";
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            protected ArrayList<GalleryImage> getImagesFromJson(JsonNode jsonNode) throws Exception {
                try {
                    this.tagId = jsonNode.get("tagID").asText();
                }
                catch (NullPointerException var2_2) {
                    return super.getImagesFromJson(jsonNode);
                }
                do {
                    return super.getImagesFromJson(jsonNode);
                    break;
                } while (true);
            }

            @Override
            public String getMatchingId() {
                return this.tagId;
            }

            @Override
            protected String getRegexForMatchingId() {
                return null;
            }

            @Override
            protected String getScriptName() {
                return PururinFragment.class.getSimpleName();
            }

            @Override
            protected void onFinishedDownloading(ArrayList<GalleryImage> arrayList, boolean bl2) {
                for (GalleryImage galleryImage : this.getImages()) {
                    for (GalleryImage galleryImage2 : arrayList) {
                        if (galleryImage2.getLinkUrl() == null || !galleryImage2.getLinkUrl().equals(galleryImage.getLinkUrl())) continue;
                        super.onFinishedDownloading(new ArrayList<GalleryImage>(), bl2);
                        return;
                    }
                }
                super.onFinishedDownloading(arrayList, bl2);
            }
        };
    }

    @Override
    protected int getHeaderLayout() {
        return 2130903105;
    }

    @Override
    public String getSearchArgumentName() {
        return "q";
    }

    @Override
    public String getSearchPrefix() {
        return "pururin";
    }

    @Override
    public void setImage(GalleryImage galleryImage) {
        super.setImage(galleryImage);
        if (galleryImage.getLinkUrl().contains("pururin.com?q=")) {
            galleryImage.setLinkUrl(galleryImage.getLinkUrl().replace("pururin.com?q=", "pururin.com/search?q="));
        }
    }

    @Override
    protected void setupHeaderLayout(ViewGroup viewGroup) {
        ((OmniformContainer)viewGroup).showAsInGrid(this.getImage().getLinkUrl());
    }

}

