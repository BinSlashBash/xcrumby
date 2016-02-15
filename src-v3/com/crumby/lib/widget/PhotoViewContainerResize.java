package com.crumby.lib.widget;

import android.view.View;
import android.view.animation.Transformation;
import com.crumby.C0065R;
import com.crumby.lib.widget.thirdparty.ResizeHeightAnimation;

public class PhotoViewContainerResize extends ResizeHeightAnimation {
    private int diff;
    private final int endPaddingTop;
    private final int startPaddingTop;

    public PhotoViewContainerResize(View v, int newh) {
        super(v, newh);
        this.startPaddingTop = v.getPaddingTop();
        this.diff = (int) v.getResources().getDimension(C0065R.dimen.photo_view_container_top);
        if (this.startPaddingTop == 0) {
            this.endPaddingTop = this.diff;
            return;
        }
        this.endPaddingTop = 0;
        this.diff = -this.diff;
    }

    protected void applyTransformation(float interpolatedTime, Transformation t) {
        getView().setPadding(getView().getPaddingLeft(), this.startPaddingTop + ((int) (((float) this.diff) * interpolatedTime)), getView().getPaddingRight(), getView().getPaddingBottom());
        super.applyTransformation(interpolatedTime, t);
    }
}
