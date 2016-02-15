package com.crumby.lib.widget.thirdparty;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import com.crumby.GalleryViewer;
import com.google.android.gms.maps.model.GroundOverlayOptions;

public class ZoomOutPageTransformer implements PageTransformer {
    private static final float MIN_ALPHA = 0.5f;
    private static final float MIN_SCALE = 0.85f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
        if (position < GroundOverlayOptions.NO_DIMENSION) {
            view.setAlpha(0.0f);
        } else if (position <= GalleryViewer.PROGRESS_COMPLETED) {
            float scaleFactor = Math.max(MIN_SCALE, GalleryViewer.PROGRESS_COMPLETED - Math.abs(position));
            float vertMargin = (((float) pageHeight) * (GalleryViewer.PROGRESS_COMPLETED - scaleFactor)) / 2.0f;
            float horzMargin = (((float) pageWidth) * (GalleryViewer.PROGRESS_COMPLETED - scaleFactor)) / 2.0f;
            if (position < 0.0f) {
                view.setTranslationX(horzMargin - (vertMargin / 2.0f));
            } else {
                view.setTranslationX((-horzMargin) + (vertMargin / 2.0f));
            }
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setAlpha((((scaleFactor - MIN_SCALE) / 0.14999998f) * MIN_ALPHA) + MIN_ALPHA);
        } else {
            view.setAlpha(0.0f);
        }
    }
}
