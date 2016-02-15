package com.crumby.lib.widget.firstparty;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.C0065R;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.widget.GalleryImageView;

public class GalleryGridDefaultHeader extends LinearLayout implements GalleryImageView {
    private TextView description;
    private GalleryImage image;
    private TextView title;

    public GalleryGridDefaultHeader(Context context) {
        super(context);
    }

    public GalleryGridDefaultHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryGridDefaultHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.title = (TextView) findViewById(C0065R.id.gallery_title);
        this.description = (TextView) findViewById(C0065R.id.gallery_description);
    }

    public void initialize(GalleryImage image) {
        this.image = image;
        image.addView(this);
        update();
    }

    public void update() {
        if (this.image != null) {
            this.title.setText(this.image.getTitle());
            if (!(this.image.getTitle() == null || this.image.getTitle().equals(UnsupportedUrlFragment.DISPLAY_NAME))) {
                this.title.setVisibility(0);
            }
            this.description.setText(this.image.getDescription());
            if (this.image.getDescription() != null && !this.image.getDescription().equals(UnsupportedUrlFragment.DISPLAY_NAME)) {
                this.description.setVisibility(0);
            }
        }
    }

    protected void onDetachedFromWindow() {
        if (this.image != null) {
            this.image.removeView(this);
        }
        super.onDetachedFromWindow();
    }
}
