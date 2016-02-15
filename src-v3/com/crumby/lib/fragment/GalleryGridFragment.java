package com.crumby.lib.fragment;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.C0065R;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.GalleryGridAdapter;
import com.crumby.lib.fragment.adapter.GalleryListAdapter;
import com.crumby.lib.fragment.adapter.SelectAllOnClickListener;
import com.crumby.lib.fragment.producer.GalleryProducer;
import com.crumby.lib.widget.firstparty.GalleryGridDefaultHeader;
import com.crumby.lib.widget.firstparty.fragment_options.CustomToggleButton;
import com.crumby.lib.widget.firstparty.grow.ColumnCounter;
import com.crumby.lib.widget.firstparty.grow.GrowGridView;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;
import com.crumby.lib.widget.firstparty.omnibar.Breadcrumb;
import com.google.android.gms.location.GeofenceStatusCodes;
import it.sephiroth.android.library.tooltip.TooltipManager;
import it.sephiroth.android.library.tooltip.TooltipManager.ClosePolicy;
import it.sephiroth.android.library.tooltip.TooltipManager.Gravity;
import java.util.List;

public abstract class GalleryGridFragment extends GalleryListFragment {
    protected static final int NO_FORM = 0;
    public static final int SELECT_ALL_AND_THEN_SAVE_TOOLTIP = 1;
    public static final int SELECT_ALL_TOOLTIP = 0;
    private static boolean showSelectAllSaveHint;
    private static boolean shownSelectAllImagesHint;
    private static boolean shownTutorial;
    private String descriptionToBeAdded;
    private CustomToggleButton searchGallery;
    private ImageButton selectAll;
    private ImageButton web;

    /* renamed from: com.crumby.lib.fragment.GalleryGridFragment.1 */
    class C00961 implements OnClickListener {
        C00961() {
        }

        public void onClick(View v) {
            GalleryGridFragment.this.getViewer().showOmnibar();
        }
    }

    /* renamed from: com.crumby.lib.fragment.GalleryGridFragment.3 */
    class C00973 implements OnClickListener {
        final /* synthetic */ SharedPreferences val$prefs;

        C00973(SharedPreferences sharedPreferences) {
            this.val$prefs = sharedPreferences;
        }

        public void onClick(View view) {
            GalleryGridFragment.this.getView().findViewById(C0065R.id.grow_grid_tutorial).setVisibility(8);
            Editor editor = this.val$prefs.edit();
            editor.putBoolean("shownTutorial", true);
            editor.commit();
            GalleryGridFragment.shownTutorial = true;
        }
    }

    /* renamed from: com.crumby.lib.fragment.GalleryGridFragment.2 */
    class C07352 extends SelectAllOnClickListener {
        C07352(List x0, MultiSelect x1) {
            super(x0, x1);
        }

        public void onClick(View v) {
            toggle();
            GalleryGridFragment.this.adapter.notifyDataSetChanged();
            if (GalleryGridFragment.showSelectAllSaveHint) {
                GalleryGridFragment.showSelectAllSaveHint = false;
                Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "select all", "complete: " + GalleryGridFragment.this.getImage().getLinkUrl() + " @ position " + GalleryGridFragment.this.list.getFirstVisiblePosition());
                TooltipManager.getInstance(GalleryGridFragment.this.getActivity()).remove(GalleryGridFragment.NO_FORM);
                TooltipManager.getInstance(GalleryGridFragment.this.getActivity()).create(GalleryGridFragment.SELECT_ALL_AND_THEN_SAVE_TOOLTIP).anchor(GalleryGridFragment.this.getActivity().findViewById(C0065R.id.main_menu_button), Gravity.BOTTOM).closePolicy(ClosePolicy.None, 10000).activateDelay(500).withStyleId(C0065R.style.tutorial_hint).text("With 'Select All Images', you can save hundreds of images at a time. Try it out!").maxWidth(GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE).show();
            }
        }
    }

    protected void alterThisBreadcrumbText(List<Breadcrumb> breadcrumbs, String prefix, String search) {
        for (Breadcrumb breadcrumb : breadcrumbs) {
            if (breadcrumb.getFragmentIndex().getFragmentClass().equals(getClass())) {
                breadcrumb.setBreadcrumbText(prefix + ": " + search);
                return;
            }
        }
    }

    protected void appendSearchQuery(List<Breadcrumb> breadcrumbs, String prefix, String argumentName) {
        Uri uri = Uri.parse(getUrl());
        if (GalleryProducer.getQueryParameter(uri, getUrl(), argumentName) != null) {
            alterThisBreadcrumbText(breadcrumbs, prefix, GalleryProducer.getQueryParameter(uri, getUrl(), argumentName));
        }
        super.setBreadcrumbs((List) breadcrumbs);
    }

    public String getSearchPrefix() {
        return null;
    }

    public String getSearchArgumentName() {
        return null;
    }

    public void setBreadcrumbs(List<Breadcrumb> breadcrumbs) {
        if (!(getSearchPrefix() == null || getSearchArgumentName() == null)) {
            appendSearchQuery(breadcrumbs, getSearchPrefix(), getSearchArgumentName());
        }
        super.setBreadcrumbs((List) breadcrumbs);
    }

    private GrowGridView grid() {
        return (GrowGridView) this.list;
    }

    protected int getHeaderLayout() {
        return NO_FORM;
    }

    protected void setupHeaderLayout(ViewGroup header) {
    }

    protected View inflateAbslistView(LayoutInflater inflater) {
        View savedView = inflater.inflate(C0065R.layout.gallery_grid_fragment, null, false);
        GrowGridView grid = (GrowGridView) savedView.findViewById(C0065R.id.gallery_output);
        this.list = grid;
        grid.initialize((ImageView) savedView.findViewById(C0065R.id.grid_bitmap), (ColumnCounter) savedView.findViewById(C0065R.id.column_count), getImage());
        if (getHeaderLayout() != 0) {
            ViewGroup header = (ViewGroup) inflater.inflate(getHeaderLayout(), null);
            grid.addHeaderView(header);
            setupHeaderLayout(header);
        } else {
            GalleryGridDefaultHeader defaultGridHeader = (GalleryGridDefaultHeader) inflater.inflate(C0065R.layout.gallery_metadata, null);
            defaultGridHeader.initialize(getImage());
            grid.addHeaderView(defaultGridHeader);
        }
        this.web = (ImageButton) savedView.findViewById(C0065R.id.view_fragment_in_web);
        this.selectAll = (ImageButton) savedView.findViewById(C0065R.id.select_all_images);
        this.searchGallery = (CustomToggleButton) savedView.findViewById(C0065R.id.search_gallery);
        return savedView;
    }

    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.createFragmentView(inflater, container, savedInstanceState);
        view.findViewById(C0065R.id.show_omnibar).setOnClickListener(new C00961());
        if (getUserVisibleHint()) {
            showGrowGridTutorial((RelativeLayout) view);
        }
        return view;
    }

    protected void initializeAdapter() {
        super.initializeAdapter();
        if (this.descriptionToBeAdded != null) {
            getImage().setDescription(this.descriptionToBeAdded);
        }
        this.selectAll.setVisibility(NO_FORM);
        this.selectAll.setOnClickListener(new C07352(this.producer.getImages(), ((GalleryViewer) getActivity()).getMultiSelect()));
    }

    public GalleryListAdapter createListAdapter() {
        return new GalleryGridAdapter();
    }

    public void refreshGalleryMetadataView() {
        if (this.adapter != null) {
        }
    }

    public void deferSetDescription(String description) {
        this.descriptionToBeAdded = description;
    }

    public boolean willAllowPaging(MotionEvent ev) {
        return false;
    }

    public void prepareForRefresh() {
        if (this.producer != null) {
            this.producer.shareAndSetCurrentImageFocus(this.list.getFirstVisiblePosition());
        }
    }

    public void expandToImage(View view, GalleryImage image, int position) {
        grid().zoomIntoSequence(view, image.getPosition());
    }

    public void onPause() {
        super.onPause();
    }

    public void redraw() {
        super.redraw();
        ViewGroup parent = (ViewGroup) this.selectAll.getParent();
        parent.removeView(this.selectAll);
        parent.addView(this.selectAll, SELECT_ALL_AND_THEN_SAVE_TOOLTIP);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && getView() != null) {
            if (!shownTutorial) {
                showGrowGridTutorial((RelativeLayout) getView());
            }
            if (this.list != null) {
                grid().checkForPersistence();
            }
        }
    }

    public void showSelectAllImagesHint() {
        if (!shownSelectAllImagesHint && !PreferenceManager.getDefaultSharedPreferences(this.list.getContext()).getBoolean("shownSelectAllImagesHint", false)) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "select all", "start: " + getImage().getLinkUrl() + " @ position " + this.list.getFirstVisiblePosition());
            TooltipManager.getInstance(getActivity()).create(NO_FORM).toggleArrow(true).anchor(this.selectAll, Gravity.BOTTOM).closePolicy(ClosePolicy.None, 10000).activateDelay(500).withStyleId(C0065R.style.tutorial_hint).text("Did you try the 'select all images' button yet?").maxWidth(GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE).show();
            Editor editor = PreferenceManager.getDefaultSharedPreferences(this.list.getContext()).edit();
            editor.putBoolean("shownSelectAllImagesHint", true);
            editor.commit();
            shownSelectAllImagesHint = true;
            showSelectAllSaveHint = true;
        }
    }

    private void showGrowGridTutorial(RelativeLayout layout) {
        if (!shownTutorial && layout != null && !getErrorView().isShowing() && this.producer != null && this.producer.getImages() != null && !this.producer.getImages().isEmpty()) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.list.getContext());
            if (prefs.getBoolean("shownTutorial", false)) {
                shownTutorial = true;
            } else if (layout.findViewById(C0065R.id.close_grow_grid_tutorial) == null) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.TUTORIAL, "show grid", null);
                View view = View.inflate(this.list.getContext(), C0065R.layout.grow_grid_tutorial, null);
                layout.addView(view, new LayoutParams(-1, -1));
                view.setAlpha(0.0f);
                view.findViewById(C0065R.id.close_grow_grid_tutorial).setOnClickListener(new C00973(prefs));
                view.animate().alpha(GalleryViewer.PROGRESS_COMPLETED).setDuration(500).start();
            }
        }
    }

    public void showGrowGridTutorial() {
        showGrowGridTutorial((RelativeLayout) getView());
    }
}
