package com.crumby.lib.fragment.adapter;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.ImageView;
import com.crumby.C0065R;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.widget.firstparty.grow.GrowGridView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class GalleryGridAdapter extends GalleryListAdapter {
    private int currentScrollState;
    int lastVisibleItem;

    /* renamed from: com.crumby.lib.fragment.adapter.GalleryGridAdapter.1 */
    class C01041 implements Runnable {
        C01041() {
        }

        public void run() {
            GalleryGridAdapter.this.padBottom();
        }
    }

    public void initialize(GalleryList fragment) {
        super.initialize(fragment);
        this.imageWrapperId = C0065R.layout.grid_image_wrapper;
        this.padOnScroll = new C01041();
    }

    protected GrowGridView getList() {
        return (GrowGridView) super.getList();
    }

    protected boolean isInSequence() {
        return getList().getNumColumns() == 1;
    }

    protected int getNumColumns() {
        return getList().getNumColumns() != -1 ? getList().getNumColumns() : getList().getCurrentColumns();
    }

    protected int getColumnWidth() {
        return getList().getColumnWidth();
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Context context = getGalleryList().getContext();
        int firstVisibleItem = view.getFirstVisiblePosition();
        this.currentScrollState = scrollState;
        if (context != null && (context instanceof GalleryViewer)) {
            if (firstVisibleItem == 0 || this.lastVisibleItem > firstVisibleItem) {
                ((GalleryViewer) context).showOmnibar();
            } else if (this.currentScrollState == 2) {
                ((GalleryViewer) context).hideOmnibar();
            }
            this.lastVisibleItem = firstVisibleItem;
        }
        super.onScrollStateChanged(view, scrollState);
    }

    public int prepareHighlight(int position) {
        return super.prepareHighlight(position);
    }

    protected void loadThumbnailWithPicasso(GalleryImage image, ImageView thumbnail, int position) {
        String imageUrl = isInSequence() ? image.getImageUrl() : GalleryGridFragment.THUMBNAIL_HEIGHT * getColumnWidth() > 60000 ? image.getThumbnailUrl() : image.getSmallThumbnailUrl();
        if (imageUrl == null) {
            imageUrl = image.getThumbnailUrl();
        }
        if (imageUrl != null && !imageUrl.equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
            RequestCreator request = Picasso.with(thumbnail.getContext()).load(imageUrl);
            if (image.isSplit()) {
                request = request.transform(new CropTransformation(image, position));
            } else if (isInSequence()) {
                int width = image.getWidth();
                if (width != 0) {
                    int height = image.getHeight();
                    if (height != 0) {
                        int screenWidth = thumbnail.getResources().getDisplayMetrics().widthPixels;
                        int screenHeight = thumbnail.getResources().getDisplayMetrics().heightPixels;
                        int newWidth = screenWidth;
                        int newHeight = image.getHeight();
                        if (width > screenWidth) {
                            newHeight = (int) (((double) screenWidth) * ((double) (width / height)));
                        } else {
                            newWidth = width;
                        }
                        newHeight = Math.min(newHeight, screenHeight * 2);
                        if (newHeight > 0) {
                            request.resize(newWidth, newHeight).centerInside();
                        }
                    }
                }
            } else {
                request.resize(getColumnWidth(), GalleryGridFragment.THUMBNAIL_HEIGHT).centerCrop();
            }
            request.error((int) C0065R.drawable.ic_action_error).into(thumbnail, this.progress.getCallback(image, thumbnail));
        }
    }
}
