/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.ViewPropertyAnimator
 *  android.widget.TextView
 */
package com.crumby.lib.widget.firstparty.main_menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;
import android.widget.TextView;
import com.crumby.lib.download.ImageDownload;
import com.crumby.lib.download.ImageDownloadListener;
import com.crumby.lib.download.ImageDownloadManager;
import java.util.ArrayList;

public class DownloadIndicator
extends TextView
implements ImageDownloadListener {
    public DownloadIndicator(Context context) {
        super(context);
    }

    public DownloadIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DownloadIndicator(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setCount(int n2) {
        this.setText((CharSequence)("" + n2 + ""));
        this.setAlpha(1.0f);
        this.clearAnimation();
        if (n2 <= 0) {
            this.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(2130837604), null, null, null);
            this.setText(null);
            this.animate().alpha(0.0f).setStartDelay(300).setDuration(1000);
        } else {
            this.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        this.invalidate();
    }

    private void updateCounter() {
        this.post(new Runnable(){

            @Override
            public void run() {
                int n2 = ImageDownloadManager.INSTANCE.getDownloadingCount();
                DownloadIndicator.this.setCount(n2);
            }
        });
    }

    @Override
    public void terminate() {
        this.setCount(0);
    }

    @Override
    public void update(ImageDownload imageDownload) {
        this.updateCounter();
    }

    @Override
    public void update(ArrayList<ImageDownload> arrayList) {
        this.updateCounter();
    }

}

