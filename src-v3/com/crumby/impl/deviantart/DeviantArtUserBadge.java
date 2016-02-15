package com.crumby.impl.deviantart;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.widget.GalleryImageView;

public class DeviantArtUserBadge extends LinearLayout implements GalleryImageView {
    private ImageView avatar;
    private TextView badge;
    private GalleryImage image;

    public DeviantArtUserBadge(Context context) {
        super(context);
    }

    public DeviantArtUserBadge(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DeviantArtUserBadge(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initialize(GalleryImage image) {
    }

    public void update() {
    }
}
