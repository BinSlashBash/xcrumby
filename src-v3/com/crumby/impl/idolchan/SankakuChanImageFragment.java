package com.crumby.impl.idolchan;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.crumby.C0065R;
import com.crumby.impl.BooruImageFragment;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import com.crumby.lib.widget.HomeGalleryList;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.ArrayList;
import java.util.List;

public class SankakuChanImageFragment extends BooruImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;
    private HomeGalleryList homeGalleryList;
    private ArrayList<GalleryImage> images;

    /* renamed from: com.crumby.impl.idolchan.SankakuChanImageFragment.1 */
    class C12771 extends SingleGalleryProducer {
        C12771() {
        }

        protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> galleryImages) throws Exception {
            galleryImages.addAll(SankakuChanImageFragment.this.images);
        }
    }

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
            this.images = new ArrayList();
            this.images.addAll(images);
            this.images.remove(0);
        }
        return fetchAgain;
    }

    protected void alternateFillMetadata() {
        if (this.images == null || this.images.isEmpty()) {
            this.homeGalleryList.setVisibility(8);
            return;
        }
        this.homeGalleryList.initialize(new GalleryImage(UnsupportedUrlFragment.DISPLAY_NAME, null, "Similar Images"), new C12771(), getViewer().getMultiSelect(), BREADCRUMB_ALT_NAME);
    }

    protected String getTagUrl(String tag) {
        return "https://idol.sankakucomplex.com?&tags=" + Uri.encode(tag.replace(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, "_"));
    }
}
