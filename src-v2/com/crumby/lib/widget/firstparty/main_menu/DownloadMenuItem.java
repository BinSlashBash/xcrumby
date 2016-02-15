/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package com.crumby.lib.widget.firstparty.main_menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.crumby.CrDb;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.download.ImageDownload;
import com.crumby.lib.download.ImageDownloadManager;
import com.crumby.lib.download.ScrollStateKeeper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class DownloadMenuItem
extends RelativeLayout {
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

    public DownloadMenuItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DownloadMenuItem(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void mainThreadUpdate(ImageDownload imageDownload) {
        boolean bl2 = this.download != imageDownload || this.lastProgress != imageDownload.getProgress();
        this.lastProgress = imageDownload.getProgress();
        this.download = imageDownload;
        CrDb.d("download menu item", "checking if download has changed. " + imageDownload.getDownloadUri());
        if (bl2) {
            CrDb.d("download menu item", "download has changed. " + imageDownload.getDownloadUri());
            this.imageView.setAlpha(1.0f);
            Drawable drawable2 = this.getResources().getDrawable(2130837610);
            this.filename.setText((CharSequence)imageDownload.getImage().getRequestedFilename());
            this.filename.setTextColor(defaultColor);
            this.bgView.setImageBitmap(null);
            Picasso.with(this.getContext()).cancelRequest(this.bgView);
            Drawable drawable3 = drawable2;
            switch (imageDownload.getProgress()) {
                default: {
                    drawable3 = drawable2;
                    break;
                }
                case -1: {
                    drawable3 = this.getResources().getDrawable(2130837635);
                    this.filename.setText((CharSequence)("Err: " + this.filename.getText()));
                    this.filename.setTextColor(this.getResources().getColor(2131427541));
                    break;
                }
                case -2: {
                    drawable3 = this.getResources().getDrawable(2130837635);
                }
                case 0: {
                    break;
                }
                case 100: {
                    drawable3 = this.getResources().getDrawable(2130837604);
                    this.imageView.setAlpha(0.5f);
                    this.setBackground();
                }
            }
            this.imageView.setImageDrawable(drawable3);
            this.invalidate();
        }
    }

    private void setBackground() {
        if (this.getMeasuredWidth() == 0 || !this.hasDownloaded() || !this.keeper.canShowListItems()) {
            return;
        }
        this.bgViewSrc = this.download.getDownloadUri();
        CrDb.d("download menu item", "setting background for " + this.download.getDownloadUri());
        Picasso.with(this.getContext()).load("file://" + this.download.getDownloadUri()).resize(this.getMeasuredWidth(), this.getMeasuredHeight() - 2).centerCrop().into(this.bgView);
    }

    public boolean canBeRestarted() {
        return this.download.canBeRestarted();
    }

    public void cancel() {
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

    protected void onSizeChanged(int n2, int n3, int n4, int n5) {
        super.onSizeChanged(n2, n3, n4, n5);
        this.setBackground();
    }

    public void restartDownload() {
        this.invalidateDownloadChangeFlag();
        this.imageView.setImageDrawable(this.getResources().getDrawable(2130837610));
        ImageDownloadManager.INSTANCE.restart(this.download);
    }

    public void setKeeper(ScrollStateKeeper scrollStateKeeper) {
        this.keeper = scrollStateKeeper;
    }

    public void stopDownload() {
        this.invalidateDownloadChangeFlag();
        this.imageView.setImageDrawable(this.getResources().getDrawable(2130837635));
        this.download.stop();
    }

    public void update(ImageDownload imageDownload, int n2) {
        if (!this.initialized) {
            this.id = (TextView)this.findViewById(2131493060);
            this.filename = (TextView)this.findViewById(2131493061);
            this.imageView = (ImageView)this.findViewById(2131493062);
            this.bgView = (ImageView)this.findViewById(2131493059);
            if (defaultColor == 0) {
                defaultColor = this.filename.getCurrentTextColor();
            }
            this.initialized = true;
        }
        this.id.setText((CharSequence)("" + n2 + ""));
        this.mainThreadUpdate(imageDownload);
    }
}

