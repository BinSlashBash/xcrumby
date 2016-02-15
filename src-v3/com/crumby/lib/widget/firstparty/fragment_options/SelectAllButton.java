package com.crumby.lib.widget.firstparty.fragment_options;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.SelectAllOnClickListener;
import com.crumby.lib.fragment.producer.GalleryConsumer;
import java.util.List;

public class SelectAllButton extends CustomToggleButton implements GalleryConsumer {
    SelectAllOnClickListener clickListener;

    public SelectAllButton(Context context) {
        super(context);
    }

    public SelectAllButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectAllButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setChecked(boolean checked) {
        this.clickListener.setChecked(checked);
    }

    public boolean isChecked() {
        if (this.clickListener == null) {
            return false;
        }
        return this.clickListener.isChecked();
    }

    public void toggle() {
        this.clickListener.toggle();
    }

    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        this.clickListener = (SelectAllOnClickListener) l;
    }

    public void showError(Exception e) {
    }

    public void finishLoading() {
    }

    public boolean addImages(List<GalleryImage> list) {
        return false;
    }

    public void notifyDataSetChanged() {
    }
}
