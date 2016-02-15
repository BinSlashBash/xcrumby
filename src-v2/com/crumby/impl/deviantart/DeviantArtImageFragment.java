/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Handler
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.webkit.WebView
 */
package com.crumby.impl.deviantart;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.crumby.GalleryViewer;
import com.crumby.impl.deviantart.DeviantArtAttributes;
import com.crumby.impl.deviantart.DeviantArtImageProducer;
import com.crumby.impl.deviantart.DeviantArtUserFragment;
import com.crumby.lib.ExtraAttributes;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryImageFragment;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.fragment.producer.SingleGalleryProducer;
import com.crumby.lib.widget.HomeGalleryList;
import com.crumby.lib.widget.ImageScrollView;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbListModifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DeviantArtImageFragment
extends GalleryImageFragment {
    public static final boolean BREADCRUMB_ALT_NAME = true;
    public static final int BREADCRUMB_ICON = 2130837634;
    public static final Class BREADCRUMB_PARENT_CLASS;
    public static final String REGEX_URL;
    public static final boolean SUGGESTABLE = true;
    private WebView description;
    private HomeGalleryList homeGalleryList;
    private View loading;

    static {
        REGEX_URL = DeviantArtUserFragment.REGEX_BASE + "/art/.*\\-([0-9]*)";
        BREADCRUMB_PARENT_CLASS = DeviantArtUserFragment.class;
    }

    private GalleryImageFragment getThis() {
        return this;
    }

    @Override
    protected GalleryProducer createProducer() {
        return new DeviantArtImageProducer();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void fillMetadataView() {
        if (this.getImage().attr() == null || this.getActivity() == null) {
            return;
        }
        String string2 = ((DeviantArtAttributes)this.getImage().attr()).getHtmlDescription();
        if (string2 == null) {
            this.invalidateAlreadyRenderedMetadataFlag();
            return;
        }
        this.loading.setVisibility(8);
        DeviantArtImageFragment.loadWebViewHtml(string2, this.description);
        if (this.getImage().attr() != null && ((DeviantArtAttributes)this.getImage().attr()).getSimilarImages() != null) {
            new Handler().postDelayed(new Runnable(){

                /*
                 * Enabled aggressive block sorting
                 * Lifted jumps to return sites
                 */
                @Override
                public void run() {
                    if (DeviantArtImageFragment.this.getActivity() == null) {
                        return;
                    }
                    GalleryImage galleryImage = new GalleryImage("", "http://deviantart.com/morelikethis/" + GalleryViewerFragment.matchIdFromUrl(DeviantArtImageFragment.REGEX_URL, DeviantArtImageFragment.this.getImage().getLinkUrl()), "Similar Images");
                    if (DeviantArtImageFragment.this.homeGalleryList == null) return;
                    if (((DeviantArtAttributes)DeviantArtImageFragment.this.getImage().attr()).getSimilarImages() == null) return;
                    DeviantArtImageFragment.this.homeGalleryList.initialize(galleryImage, new SingleGalleryProducer(){

                        @Override
                        protected void fetchGalleryImagesOnce(ArrayList<GalleryImage> arrayList) throws Exception {
                            arrayList.addAll(((DeviantArtAttributes)DeviantArtImageFragment.this.getImage().attr()).getSimilarImages());
                        }
                    }, DeviantArtImageFragment.this.getViewer().getMultiSelect(), true);
                }

            }, 400);
        }
        this.getImageScrollView().fullScroll(33);
    }

    @Override
    protected ViewGroup inflateMetadataLayout(LayoutInflater layoutInflater) {
        layoutInflater = (ViewGroup)layoutInflater.inflate(2130903059, null);
        this.description = (WebView)layoutInflater.findViewById(2131492943);
        this.description.setBackgroundColor(0);
        this.loading = layoutInflater.findViewById(2131492944);
        this.homeGalleryList = (HomeGalleryList)layoutInflater.findViewById(2131492988);
        layoutInflater.findViewById(2131492941).bringToFront();
        return layoutInflater;
    }

    @Override
    public void onDestroyView() {
        this.murderWebview(this.description);
        super.onDestroyView();
    }

    @Override
    public void setBreadcrumbs(BreadcrumbListModifier breadcrumbListModifier) {
        List<Breadcrumb> list = breadcrumbListModifier.getChildren();
        String string2 = this.getImage().getLinkUrl();
        string2 = DeviantArtImageFragment.matchIdFromUrl(DeviantArtUserFragment.REGEX_URL, string2);
        list.get(1).alter("http://" + string2 + "." + "deviantart.com");
        list.get(1).setAlpha(1.0f);
        super.setBreadcrumbs(breadcrumbListModifier);
    }

}

