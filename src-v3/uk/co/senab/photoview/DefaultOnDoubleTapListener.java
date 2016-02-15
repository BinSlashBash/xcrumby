package uk.co.senab.photoview;

import android.graphics.RectF;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;

public class DefaultOnDoubleTapListener implements OnDoubleTapListener {
    private PhotoViewAttacher photoViewAttacher;

    public DefaultOnDoubleTapListener(PhotoViewAttacher photoViewAttacher) {
        setPhotoViewAttacher(photoViewAttacher);
    }

    public void setPhotoViewAttacher(PhotoViewAttacher newPhotoViewAttacher) {
        this.photoViewAttacher = newPhotoViewAttacher;
    }

    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (this.photoViewAttacher == null) {
            return false;
        }
        ImageView imageView = this.photoViewAttacher.getImageView();
        if (this.photoViewAttacher.expandContainerScaleListener.expand()) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "single tap to full screen", null);
        }
        if (this.photoViewAttacher.getOnPhotoTapListener() != null) {
            RectF displayRect = this.photoViewAttacher.getDisplayRect();
            if (displayRect != null) {
                float x = e.getX();
                float y = e.getY();
                if (displayRect.contains(x, y)) {
                    this.photoViewAttacher.getOnPhotoTapListener().onPhotoTap(imageView, (x - displayRect.left) / displayRect.width(), (y - displayRect.top) / displayRect.height());
                    return true;
                }
            }
        }
        if (this.photoViewAttacher.getOnViewTapListener() != null) {
            this.photoViewAttacher.getOnViewTapListener().onViewTap(imageView, e.getX(), e.getY());
        }
        if (this.photoViewAttacher.getMinimumScale() != this.photoViewAttacher.getScale() || !this.photoViewAttacher.expandContainerScaleListener.contract()) {
            return false;
        }
        Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "single tap to exit full screen", null);
        return false;
    }

    public boolean onDoubleTap(MotionEvent ev) {
        if (this.photoViewAttacher == null) {
            return false;
        }
        this.photoViewAttacher.expandContainerScaleListener.expand();
        try {
            float scale = this.photoViewAttacher.getScale();
            float x = ev.getX();
            float y = ev.getY();
            if (scale < this.photoViewAttacher.getMediumScale()) {
                this.photoViewAttacher.setScale(this.photoViewAttacher.getMediumScale(), x, y, true);
                return true;
            } else if (scale < this.photoViewAttacher.getMediumScale() || scale >= this.photoViewAttacher.getMaximumScale()) {
                this.photoViewAttacher.setScale(this.photoViewAttacher.getMinimumScale(), x, y, true);
                return true;
            } else {
                this.photoViewAttacher.setScale(this.photoViewAttacher.getMaximumScale(), x, y, true);
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }
    }

    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
