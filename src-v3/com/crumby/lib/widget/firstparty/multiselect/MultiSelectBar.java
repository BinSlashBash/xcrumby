package com.crumby.lib.widget.firstparty.multiselect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.crumby.C0065R;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.download.ImageDownloadManager;
import it.sephiroth.android.library.tooltip.TooltipManager;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class MultiSelectBar extends LinearLayout implements OnClickListener, MultiSelect {
    private boolean animating;
    private ImageButton bookmark;
    private ImageButton close;
    private TextView galleries;
    private int galleryCount;
    private int imageCount;
    private LinkedHashSet<GalleryImage> images;
    private ImageButton save;
    private ImageButton share;
    private GalleryViewer viewer;

    /* renamed from: com.crumby.lib.widget.firstparty.multiselect.MultiSelectBar.1 */
    class C01471 extends AnimatorListenerAdapter {
        C01471() {
        }

        public void onAnimationEnd(Animator animation) {
            MultiSelectBar.this.setVisibility(8);
            MultiSelectBar.this.animating = false;
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.multiselect.MultiSelectBar.2 */
    class C01482 extends AnimatorListenerAdapter {
        C01482() {
        }

        public void onAnimationEnd(Animator animation) {
            MultiSelectBar.this.animating = false;
        }
    }

    public MultiSelectBar(Context context) {
        super(context);
    }

    public MultiSelectBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiSelectBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void resetAnimation() {
        clearAnimation();
        setAlpha(GalleryViewer.PROGRESS_COMPLETED);
        this.animating = false;
    }

    public void hide() {
        if (this.animating) {
            setVisibility(8);
            resetAnimation();
            return;
        }
        this.animating = true;
        animate().alpha(0.0f).setDuration(300).setListener(new C01471());
    }

    private void show() {
        if (!this.images.isEmpty()) {
            setVisibility(0);
            if (this.animating) {
                this.animating = false;
                setVisibility(0);
                resetAnimation();
                return;
            }
            this.animating = true;
            animate().alpha(GalleryViewer.PROGRESS_COMPLETED).setListener(new C01482());
        }
    }

    public void initialize(GalleryViewer viewer) {
        setVisibility(4);
        setAlpha(0.0f);
        this.images = new LinkedHashSet();
        this.galleries = (TextView) findViewById(C0065R.id.galleries_selected);
        this.close = (ImageButton) findViewById(C0065R.id.close_multi_selectbar);
        this.close.setOnClickListener(this);
        this.save = (ImageButton) findViewById(C0065R.id.save_image);
        this.share = (ImageButton) findViewById(C0065R.id.share_image);
        this.bookmark = (ImageButton) findViewById(C0065R.id.bookmark_image);
        this.save.setOnClickListener(this);
        this.share.setOnClickListener(this);
        this.bookmark.setOnClickListener(this);
        this.viewer = viewer;
    }

    private void countImages() {
        this.imageCount = 0;
        this.galleryCount = 0;
        Iterator i$ = this.images.iterator();
        while (i$.hasNext()) {
            if (((GalleryImage) i$.next()).isALinkToGallery()) {
                this.galleryCount++;
            } else {
                this.imageCount++;
            }
        }
    }

    private void updateText() {
        countImages();
        String text = UnsupportedUrlFragment.DISPLAY_NAME;
        if (this.galleryCount != 0) {
            text = text + this.galleryCount + " galleries";
            if (this.imageCount != 0) {
                text = text + ", ";
            }
        }
        if (this.imageCount != 0) {
            text = text + this.imageCount + " images";
        }
        this.galleries.setText(text);
    }

    public void add(GalleryImage image) {
        this.images.add(image);
        show();
        updateText();
    }

    public void add(Collection<GalleryImage> images) {
        this.images.addAll(images);
        show();
        updateText();
    }

    public void remove(Collection<GalleryImage> newImages) {
        if (this.images.removeAll(newImages)) {
            updateText();
            if (this.images.isEmpty()) {
                hide();
            }
        }
    }

    public boolean remove(GalleryImage image) {
        if (this.images.remove(image)) {
            updateText();
            if (this.images.isEmpty()) {
                hide();
                return true;
            }
        }
        return false;
    }

    public boolean hasImages() {
        if (this.images == null || this.images.isEmpty()) {
            return false;
        }
        return true;
    }

    public void clear() {
        if (this.images == null || !this.images.isEmpty()) {
            Iterator i$ = this.images.iterator();
            while (i$.hasNext()) {
                ((GalleryImage) i$.next()).setChecked(false);
            }
            this.images.clear();
            hide();
            this.viewer.redrawPage();
        }
    }

    public void onClick(View v) {
        countImages();
        if (v == this.close) {
            clear();
        } else if (v == this.save) {
            TooltipManager.getInstance(this.viewer).remove(0);
            TooltipManager.getInstance(this.viewer).remove(1);
            this.viewer.showOmnibar();
            ImageDownloadManager.INSTANCE.downloadAll(this.images);
            clear();
        } else if (v != this.share && v == this.bookmark) {
        }
    }

    public boolean isOpen() {
        return getVisibility() == 0;
    }
}
