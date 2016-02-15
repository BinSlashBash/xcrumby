package com.crumby.lib.widget.firstparty.grow;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.GridImageWrapper;
import com.crumby.lib.widget.firstparty.multiselect.ImageCheckable;

public class ImagePressWrapper extends FrameLayout implements ImageCheckable {
    private static final int[] CHECKED_STATE_SET;
    private boolean checked;

    public ImagePressWrapper(Context context) {
        super(context);
    }

    public ImagePressWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImagePressWrapper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public GalleryImage getImage() {
        return ((GridImageWrapper) getTag()).getImage();
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (getImage() != null) {
            getImage().setChecked(checked);
        }
        refreshDrawableState();
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void toggle() {
        setChecked(!this.checked);
    }

    static {
        CHECKED_STATE_SET = new int[]{16842912};
    }

    protected int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 2);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }
}
