package com.crumby.lib.widget.firstparty.main_menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.download.ImageDownload;
import com.crumby.lib.download.ImageDownloadManager;
import com.crumby.lib.download.ScrollStateKeeper;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.wallet.fragment.Dimension;
import com.squareup.picasso.Picasso;

public class DownloadMenuItem extends RelativeLayout {
    private static int defaultColor;
    private static int defaultFlags;
    private String bgImageUri;
    private ImageView bgView;
    private String bgViewSrc;
    private ImageDownload download;
    private TextView filename;
    private TextView id;
    private ImageView imageView;
    private boolean initialized;
    ScrollStateKeeper keeper;
    int lastProgress;

    public DownloadMenuItem(Context context) {
        super(context);
    }

    public DownloadMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DownloadMenuItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setKeeper(ScrollStateKeeper keeper) {
        this.keeper = keeper;
    }

    public void update(ImageDownload imageDownload, int id) {
        if (!this.initialized) {
            this.id = (TextView) findViewById(C0065R.id.menu_download_item_id);
            this.filename = (TextView) findViewById(C0065R.id.menu_download_item);
            this.imageView = (ImageView) findViewById(C0065R.id.download_thumbnail);
            this.bgView = (ImageView) findViewById(C0065R.id.download_background);
            if (defaultColor == 0) {
                defaultColor = this.filename.getCurrentTextColor();
            }
            this.initialized = true;
        }
        this.id.setText(id + UnsupportedUrlFragment.DISPLAY_NAME);
        mainThreadUpdate(imageDownload);
    }

    private void mainThreadUpdate(ImageDownload download) {
        boolean downloadHasChanged = (this.download == download && this.lastProgress == download.getProgress()) ? false : true;
        this.lastProgress = download.getProgress();
        this.download = download;
        CrDb.m0d("download menu item", "checking if download has changed. " + download.getDownloadUri());
        if (downloadHasChanged) {
            CrDb.m0d("download menu item", "download has changed. " + download.getDownloadUri());
            this.imageView.setAlpha(GalleryViewer.PROGRESS_COMPLETED);
            Drawable icon = getResources().getDrawable(C0065R.drawable.ic_action_cancel);
            this.filename.setText(download.getImage().getRequestedFilename());
            this.filename.setTextColor(defaultColor);
            this.bgView.setImageBitmap(null);
            Picasso.with(getContext()).cancelRequest(this.bgView);
            switch (download.getProgress()) {
                case Dimension.WRAP_CONTENT /*-2*/:
                    icon = getResources().getDrawable(C0065R.drawable.ic_action_refresh);
                    break;
                case TurnBasedMatch.MATCH_VARIANT_DEFAULT /*-1*/:
                    icon = getResources().getDrawable(C0065R.drawable.ic_action_refresh);
                    this.filename.setText("Err: " + this.filename.getText());
                    this.filename.setTextColor(getResources().getColor(C0065R.color.ErrorRed));
                    break;
                case LocationRequest.PRIORITY_HIGH_ACCURACY /*100*/:
                    icon = getResources().getDrawable(C0065R.drawable.ic_action_accept);
                    this.imageView.setAlpha(0.5f);
                    setBackground();
                    break;
            }
            this.imageView.setImageDrawable(icon);
            invalidate();
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setBackground();
    }

    private void setBackground() {
        if (getMeasuredWidth() != 0 && hasDownloaded() && this.keeper.canShowListItems()) {
            this.bgViewSrc = this.download.getDownloadUri();
            CrDb.m0d("download menu item", "setting background for " + this.download.getDownloadUri());
            Picasso.with(getContext()).load("file://" + this.download.getDownloadUri()).resize(getMeasuredWidth(), getMeasuredHeight() - 2).centerCrop().into(this.bgView);
        }
    }

    public String getURI() {
        return this.download.getDownloadUri();
    }

    public boolean hasDownloaded() {
        if (this.download == null) {
            return false;
        }
        return this.download.isDone();
    }

    public void invalidateDownloadChangeFlag() {
        this.lastProgress = -9999999;
    }

    public void stopDownload() {
        invalidateDownloadChangeFlag();
        this.imageView.setImageDrawable(getResources().getDrawable(C0065R.drawable.ic_action_refresh));
        this.download.stop();
    }

    public void restartDownload() {
        invalidateDownloadChangeFlag();
        this.imageView.setImageDrawable(getResources().getDrawable(C0065R.drawable.ic_action_cancel));
        ImageDownloadManager.INSTANCE.restart(this.download);
    }

    public void cancel() {
    }

    public boolean canBeRestarted() {
        return this.download.canBeRestarted();
    }
}
