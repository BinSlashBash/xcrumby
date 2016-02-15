package com.crumby.lib.widget.firstparty.fragment_options;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.adapter.ImageWrapper;
import com.crumby.lib.widget.firstparty.multiselect.MultiSelect;

public class SelectFragmentButton extends CustomToggleButton implements OnClickListener {
    GalleryImage image;
    private MultiSelect multiSelect;

    public SelectFragmentButton(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public SelectFragmentButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public SelectFragmentButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnClickListener(this);
    }

    public void onClick(View v) {
        toggle();
    }

    public void setChecked(boolean b) {
        this.image.setChecked(b);
        if (this.image.isChecked()) {
            this.multiSelect.add(this.image);
        } else {
            this.multiSelect.remove(this.image);
        }
    }

    public boolean isChecked() {
        if (this.image == null) {
            return false;
        }
        return this.image.isChecked();
    }

    public void toggle() {
        setChecked(!this.image.isChecked());
    }

    public void initialize(GalleryImage image, MultiSelect multiSelect) {
        this.image = image;
        setTag(new ImageWrapper(image));
        this.multiSelect = multiSelect;
        setVisibility(0);
    }
}
