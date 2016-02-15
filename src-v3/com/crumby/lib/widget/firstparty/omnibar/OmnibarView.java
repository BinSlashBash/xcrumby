package com.crumby.lib.widget.firstparty.omnibar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.crumby.C0065R;
import com.crumby.GalleryViewer;
import com.crumby.lib.fragment.GalleryViewerFragment;
import com.crumby.lib.widget.firstparty.GalleryPanel;
import java.util.List;

public class OmnibarView extends LinearLayout {
    private BreadcrumbContainer breadcrumbContainer;
    private GalleryPanel galleryPanel;
    private OmnibarModal omnibarModal;
    private OmnisearchField searchField;

    /* renamed from: com.crumby.lib.widget.firstparty.omnibar.OmnibarView.1 */
    class C01541 implements OnClickListener {
        final /* synthetic */ GalleryViewer val$viewer;

        C01541(GalleryViewer galleryViewer) {
            this.val$viewer = galleryViewer;
        }

        public void onClick(View v) {
            this.val$viewer.showOmniSearch();
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.omnibar.OmnibarView.2 */
    class C01552 implements OnClickListener {
        final /* synthetic */ GalleryViewer val$viewer;

        C01552(GalleryViewer galleryViewer) {
            this.val$viewer = galleryViewer;
        }

        public void onClick(View v) {
            this.val$viewer.hideOmniSearch();
        }
    }

    public OmnibarView(Context context) {
        super(context);
    }

    public OmnibarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OmnibarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initialize(GalleryViewer viewer) {
        setHideBreadcrumbListener(new C01541(viewer));
        setOmnibarModal(viewer.findViewById(C0065R.id.omnibar_modal), viewer.getLayoutInflater(), new C01552(viewer));
        setGalleryPanel(viewer.findViewById(C0065R.id.gallery_panel));
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.searchField = (OmnisearchField) findViewById(C0065R.id.omnisearch_field);
        this.breadcrumbContainer = (BreadcrumbContainer) findViewById(C0065R.id.breadcrumb_container);
        this.breadcrumbContainer.initialize(this);
        this.searchField.setBreadcrumbContainer(this.breadcrumbContainer);
    }

    public void hideModals() {
        clearFocus();
        showBreadcrumbs();
        this.galleryPanel.hide();
        this.omnibarModal.hide();
    }

    public void showOmniSearchModal() {
        hideBreadcrumbs();
        this.galleryPanel.hide();
        this.omnibarModal.show(true);
    }

    public void setHideBreadcrumbListener(OnClickListener hideBreadcrumbListener) {
        this.breadcrumbContainer.setClickable(true);
        this.breadcrumbContainer.setOnClickListener(hideBreadcrumbListener);
    }

    public void pageLoading() {
        this.breadcrumbContainer.stopLoadingMode();
    }

    public void pageNotLoading() {
        this.breadcrumbContainer.refreshMode();
    }

    public void setOmnibarModal(View omnibarModal, LayoutInflater inflater, OnClickListener listener) {
        this.omnibarModal = (OmnibarModal) omnibarModal;
        this.omnibarModal.setOnClickListener(listener);
        this.omnibarModal.shareSuggestionsViewWith(this.searchField);
        this.searchField.setProgressIndicator((FragmentSuggestionsIndicator) omnibarModal.findViewById(C0065R.id.searching_suggestions));
        this.searchField.setOmniformContainer((OmniformContainer) omnibarModal.findViewById(C0065R.id.omniform_container));
    }

    public void update(GalleryViewerFragment fragment, boolean truncate) {
        showBreadcrumbs();
        this.searchField.setLink(fragment);
        this.breadcrumbContainer.newPage(fragment, truncate);
    }

    public void setGalleryPanel(View galleryPanel) {
        this.galleryPanel = (GalleryPanel) galleryPanel;
        this.galleryPanel.setOmnibarView(this);
    }

    public void showGalleryPanel(int searchFormId, Breadcrumb breadcrumbListening) {
        this.omnibarModal.show(false);
        this.galleryPanel.rebuildForm(searchFormId, breadcrumbListening);
    }

    public void dismissOmnibarModal() {
        this.breadcrumbContainer.focus();
        this.omnibarModal.hide();
    }

    public void dismissGalleryPanel() {
        this.omnibarModal.hide();
        this.galleryPanel.hide();
    }

    public boolean galleryPanelShowing() {
        return this.galleryPanel.isShowing();
    }

    public void override(List<Breadcrumb> breadcrumbs) {
        this.breadcrumbContainer.override(breadcrumbs);
    }

    private void showBreadcrumbs() {
        this.searchField.hide();
        this.breadcrumbContainer.show();
    }

    private void hideBreadcrumbs() {
        this.breadcrumbContainer.hide();
        this.searchField.show(this.breadcrumbContainer.getCurrentBreadcrumbs());
    }

    public void showAccount() {
        this.searchField.showAccount();
    }

    public void clearBreadcrumbs() {
        this.breadcrumbContainer.removeBreadcrumbs();
    }
}
