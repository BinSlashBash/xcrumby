/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.LinearLayout
 */
package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.crumby.GalleryViewer;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.widget.firstparty.GalleryPanel;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.crumby.lib.widget.firstparty.omnibar.BreadcrumbContainer;
import com.crumby.lib.widget.firstparty.omnibar.FragmentSuggestionsHolder;
import com.crumby.lib.widget.firstparty.omnibar.FragmentSuggestionsIndicator;
import com.crumby.lib.widget.firstparty.omnibar.OmnibarModal;
import com.crumby.lib.widget.firstparty.omnibar.OmniformContainer;
import com.crumby.lib.widget.firstparty.omnibar.OmnisearchField;
import java.util.List;

public class OmnibarView
extends LinearLayout {
    private BreadcrumbContainer breadcrumbContainer;
    private GalleryPanel galleryPanel;
    private OmnibarModal omnibarModal;
    private OmnisearchField searchField;

    public OmnibarView(Context context) {
        super(context);
    }

    public OmnibarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public OmnibarView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    private void hideBreadcrumbs() {
        this.breadcrumbContainer.hide();
        this.searchField.show(this.breadcrumbContainer.getCurrentBreadcrumbs());
    }

    private void showBreadcrumbs() {
        this.searchField.hide();
        this.breadcrumbContainer.show();
    }

    public void clearBreadcrumbs() {
        this.breadcrumbContainer.removeBreadcrumbs();
    }

    public void dismissGalleryPanel() {
        this.omnibarModal.hide();
        this.galleryPanel.hide();
    }

    public void dismissOmnibarModal() {
        this.breadcrumbContainer.focus();
        this.omnibarModal.hide();
    }

    public boolean galleryPanelShowing() {
        return this.galleryPanel.isShowing();
    }

    public void hideModals() {
        this.clearFocus();
        this.showBreadcrumbs();
        this.galleryPanel.hide();
        this.omnibarModal.hide();
    }

    public void initialize(final GalleryViewer galleryViewer) {
        this.setHideBreadcrumbListener(new View.OnClickListener(){

            public void onClick(View view) {
                galleryViewer.showOmniSearch();
            }
        });
        this.setOmnibarModal(galleryViewer.findViewById(2131493071), galleryViewer.getLayoutInflater(), new View.OnClickListener(){

            public void onClick(View view) {
                galleryViewer.hideOmniSearch();
            }
        });
        this.setGalleryPanel(galleryViewer.findViewById(2131492999));
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.searchField = (OmnisearchField)this.findViewById(2131492897);
        this.breadcrumbContainer = (BreadcrumbContainer)this.findViewById(2131492907);
        this.breadcrumbContainer.initialize(this);
        this.searchField.setBreadcrumbContainer(this.breadcrumbContainer);
    }

    public void override(List<Breadcrumb> list) {
        this.breadcrumbContainer.override(list);
    }

    public void pageLoading() {
        this.breadcrumbContainer.stopLoadingMode();
    }

    public void pageNotLoading() {
        this.breadcrumbContainer.refreshMode();
    }

    public void setGalleryPanel(View view) {
        this.galleryPanel = (GalleryPanel)view;
        this.galleryPanel.setOmnibarView(this);
    }

    public void setHideBreadcrumbListener(View.OnClickListener onClickListener) {
        this.breadcrumbContainer.setClickable(true);
        this.breadcrumbContainer.setOnClickListener(onClickListener);
    }

    public void setOmnibarModal(View view, LayoutInflater layoutInflater, View.OnClickListener onClickListener) {
        this.omnibarModal = (OmnibarModal)view;
        this.omnibarModal.setOnClickListener(onClickListener);
        this.omnibarModal.shareSuggestionsViewWith(this.searchField);
        this.searchField.setProgressIndicator((FragmentSuggestionsIndicator)view.findViewById(2131493075));
        this.searchField.setOmniformContainer((OmniformContainer)view.findViewById(2131493079));
    }

    public void showAccount() {
        this.searchField.showAccount();
    }

    public void showGalleryPanel(int n2, Breadcrumb breadcrumb) {
        this.omnibarModal.show(false);
        this.galleryPanel.rebuildForm(n2, breadcrumb);
    }

    public void showOmniSearchModal() {
        this.hideBreadcrumbs();
        this.galleryPanel.hide();
        this.omnibarModal.show(true);
    }

    public void update(GalleryViewerFragment galleryViewerFragment, boolean bl2) {
        this.showBreadcrumbs();
        this.searchField.setLink(galleryViewerFragment);
        this.breadcrumbContainer.newPage(galleryViewerFragment, bl2);
    }

}

