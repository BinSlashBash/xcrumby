package com.crumby.impl.sankakuchan;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.impl.deviantart.DeviantArtAttributes;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.CachedProducer;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.HomeGalleryList;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.ArrayList;
import java.util.List;

public class SankakuChanImageFragment extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;
    protected HomeGalleryList homeGalleryList;

    static {
        REGEX_URL = SankakuChanFragment.REGEX_BASE + "/post/show/([0-9]+).*";
        BREADCRUMB_PARENT_CLASS = SankakuChanFragment.class;
    }

    protected GalleryProducer createProducer() {
        return new SankakuChanImageProducer();
    }

    protected int getBooruLayout() {
        return C0065R.layout.sankaku_image_metadata;
    }

    protected ViewGroup inflateMetadataLayout(LayoutInflater inflater) {
        ViewGroup view = super.inflateMetadataLayout(inflater);
        this.homeGalleryList = (HomeGalleryList) view.findViewById(C0065R.id.gallery_horizontal_list);
        return view;
    }

    public boolean addImages(List<GalleryImage> images) {
        boolean fetchAgain = super.addImages(images);
        if (images.size() > 0) {
            images.remove(0);
            ArrayList images1 = new ArrayList();
            images1.addAll(images);
            getImage().setAttributes(new DeviantArtAttributes(images1));
        }
        return fetchAgain;
    }

    protected void fillMetadataView() {
        if (!this.reloading) {
            super.fillMetadataView();
        }
    }

    protected void alternateFillMetadata() {
        try {
            ArrayList images = ((DeviantArtAttributes) getImage().attr()).getSimilarImages();
            if (images == null || images.isEmpty()) {
                this.homeGalleryList.setVisibility(8);
                return;
            }
            this.homeGalleryList.initialize(new GalleryImage(UnsupportedUrlFragment.DISPLAY_NAME, null, "Similar Images"), new CachedProducer(images), getViewer().getMultiSelect(), BREADCRUMB_ALT_NAME);
        } catch (NullPointerException e) {
            this.homeGalleryList.setVisibility(8);
        }
    }

    protected String getTagUrl(String tag) {
        return "https://chan.sankakucomplex.com?&tags=" + Uri.encode(tag.replace(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, "_"));
    }
}
