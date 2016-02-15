package com.crumby.lib.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.GalleryList;
import com.crumby.lib.fragment.adapter.GalleryListAdapter;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import com.crumby.lib.widget.firstparty.DisplayError;
import com.crumby.lib.widget.firstparty.ErrorView;

public abstract class GalleryListFragment extends GalleryViewerFragment implements GalleryList, GalleryClickHandler {
    public static int MINIMUM_THUMBNAIL_WIDTH;
    public static int THUMBNAIL_HEIGHT;
    protected GalleryListAdapter adapter;
    boolean clicked;
    protected ViewGroup description;
    private ErrorView errorView;
    protected AbsListView list;

    /* renamed from: com.crumby.lib.fragment.GalleryListFragment.1 */
    class C01001 implements Runnable {
        final /* synthetic */ int val$realPosition;

        C01001(int i) {
            this.val$realPosition = i;
        }

        public void run() {
            if (GalleryListFragment.this.getActivity() != null && GalleryListFragment.this.list != null) {
                GalleryListFragment.this.list.setSelection(this.val$realPosition);
            }
        }
    }

    static {
        MINIMUM_THUMBNAIL_WIDTH = 100;
        THUMBNAIL_HEIGHT = 150;
    }

    public GalleryListAdapter createListAdapter() {
        return new GalleryListAdapter();
    }

    public ErrorView getErrorView() {
        return this.errorView;
    }

    public void showError(DisplayError error, String reason, String url) {
        if (this.errorView != null) {
            this.errorView.show(error, reason, url);
        }
    }

    private void attachImageClickListener() {
        ImageClickListener imageClickListener = new ImageClickListener(this, ((GalleryViewer) getActivity()).getMultiSelect(), "grid");
        this.list.setOnItemClickListener(imageClickListener);
        this.list.setOnItemLongClickListener(imageClickListener);
    }

    protected void initializeAdapter() {
        this.adapter = createListAdapter();
        this.adapter.initialize(this);
        this.adapter.startFetching();
        this.list.setClipChildren(false);
        this.list.setAdapter(this.adapter);
        this.list.setOnScrollListener(this.adapter);
    }

    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateAbslistView(inflater);
        this.errorView = (ErrorView) view.findViewById(C0065R.id.error_view);
        attachImageClickListener();
        initializeAdapter();
        return view;
    }

    protected View inflateAbslistView(LayoutInflater inflater) {
        View view = inflater.inflate(C0065R.layout.gallery_list_fragment, null);
        this.list = (AbsListView) view.findViewById(C0065R.id.gallery_output);
        return view;
    }

    public void stopLoading() {
        this.adapter.stopLoading();
    }

    public AbsListView getList() {
        return this.list;
    }

    public boolean undo() {
        if (this.errorView == null || !this.errorView.close()) {
            return false;
        }
        return true;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.adapter == null) {
            return;
        }
        if (isVisibleToUser) {
            this.adapter.notifyDataSetChanged();
        } else {
            this.adapter.cancelPicassoLoad();
        }
    }

    public void deferSetDescription(String update) {
    }

    public boolean willAllowPaging(MotionEvent ev) {
        return false;
    }

    public void prepareForRefresh() {
    }

    public void goToImage(View view, GalleryImage image, int position) {
        if (!this.clicked) {
            this.clicked = true;
            applyGalleryImageChange(view, image, position);
        }
    }

    public void applyGalleryImageChange(View view, GalleryImage image, int position) {
        if (image.isALinkToGallery()) {
            postUrlChangeToBus(image.getLinkUrl(), null);
        } else {
            postUrlChangeToBusWithProducer(image);
        }
    }

    public void onResume() {
        super.onResume();
        resume();
    }

    public void resume() {
        if (this.producer != null && !checkIfAllowedToResume()) {
            scrollToImageInList(this.producer.getCurrentImageFocus());
        }
    }

    public void scrollToImageInList(int position) {
        if (position != -1) {
            int realPosition = this.adapter.prepareHighlight(position);
            CrDb.m0d("viewer fragment", "setting to:" + position);
            this.list.setSelection(position);
            this.list.postDelayed(new C01001(realPosition), 500);
        }
    }

    public void showClutter() {
    }

    public void hideClutter() {
    }

    public void redraw() {
        super.redraw();
        if (this.adapter != null) {
            this.adapter.notifyDataSetChanged();
        }
    }

    public GalleryConsumer getConsumer() {
        return this.adapter;
    }

    public Context getContext() {
        return getActivity();
    }

    public void onDestroyView() {
        if (this.adapter != null) {
            this.adapter.destroy();
        }
        if (getView() != null) {
            ((ViewGroup) getView()).removeView(this.list);
        }
        this.list.setAdapter(null);
        this.list = null;
        this.adapter = null;
        super.onDestroyView();
    }
}
