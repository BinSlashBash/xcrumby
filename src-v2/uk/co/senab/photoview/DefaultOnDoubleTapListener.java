/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.graphics.RectF
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnDoubleTapListener
 *  android.view.MotionEvent
 *  android.view.View
 *  android.widget.ImageView
 */
package uk.co.senab.photoview;

import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import uk.co.senab.photoview.ExpandContainerScaleListener;
import uk.co.senab.photoview.PhotoViewAttacher;

public class DefaultOnDoubleTapListener
implements GestureDetector.OnDoubleTapListener {
    private PhotoViewAttacher photoViewAttacher;

    public DefaultOnDoubleTapListener(PhotoViewAttacher photoViewAttacher) {
        this.setPhotoViewAttacher(photoViewAttacher);
    }

    public boolean onDoubleTap(MotionEvent motionEvent) {
        float f2;
        float f3;
        block6 : {
            float f4;
            block5 : {
                if (this.photoViewAttacher == null) {
                    return false;
                }
                this.photoViewAttacher.expandContainerScaleListener.expand();
                try {
                    f4 = this.photoViewAttacher.getScale();
                    f2 = motionEvent.getX();
                    f3 = motionEvent.getY();
                    if (f4 >= this.photoViewAttacher.getMediumScale()) break block5;
                    this.photoViewAttacher.setScale(this.photoViewAttacher.getMediumScale(), f2, f3, true);
                    return true;
                }
                catch (ArrayIndexOutOfBoundsException var1_2) {
                    return true;
                }
            }
            if (f4 < this.photoViewAttacher.getMediumScale() || f4 >= this.photoViewAttacher.getMaximumScale()) break block6;
            this.photoViewAttacher.setScale(this.photoViewAttacher.getMaximumScale(), f2, f3, true);
            return true;
        }
        this.photoViewAttacher.setScale(this.photoViewAttacher.getMinimumScale(), f2, f3, true);
        return true;
    }

    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        float f2;
        float f3;
        RectF rectF;
        if (this.photoViewAttacher == null) {
            return false;
        }
        ImageView imageView = this.photoViewAttacher.getImageView();
        if (this.photoViewAttacher.expandContainerScaleListener.expand()) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "single tap to full screen", null);
        }
        if (this.photoViewAttacher.getOnPhotoTapListener() != null && (rectF = this.photoViewAttacher.getDisplayRect()) != null && rectF.contains(f3 = motionEvent.getX(), f2 = motionEvent.getY())) {
            f3 = (f3 - rectF.left) / rectF.width();
            f2 = (f2 - rectF.top) / rectF.height();
            this.photoViewAttacher.getOnPhotoTapListener().onPhotoTap((View)imageView, f3, f2);
            return true;
        }
        if (this.photoViewAttacher.getOnViewTapListener() != null) {
            this.photoViewAttacher.getOnViewTapListener().onViewTap((View)imageView, motionEvent.getX(), motionEvent.getY());
        }
        if (this.photoViewAttacher.getMinimumScale() != this.photoViewAttacher.getScale()) return false;
        if (!this.photoViewAttacher.expandContainerScaleListener.contract()) return false;
        Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "single tap to exit full screen", null);
        return false;
    }

    public void setPhotoViewAttacher(PhotoViewAttacher photoViewAttacher) {
        this.photoViewAttacher = photoViewAttacher;
    }
}

