package com.crumby.lib.widget.firstparty.fragment_options;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.download.ImageDownloadManager;

public class SaveImageButton extends ImageButton implements OnClickListener {
    private GalleryImage image;

    public SaveImageButton(Context context) {
        super(context);
    }

    public SaveImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SaveImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initialize(GalleryImage image) {
        this.image = image;
        setOnClickListener(this);
        render();
    }

    public void render() {
        if (this.image.getImageUrl() == null) {
            hide();
        } else {
            show();
        }
    }

    public void show() {
        setVisibility(0);
    }

    public void hide() {
        setVisibility(8);
    }

    public void save() {
        ImageDownloadManager.INSTANCE.downloadOne(this.image);
    }

    public void onClick(View view) {
        save();
    }
}
