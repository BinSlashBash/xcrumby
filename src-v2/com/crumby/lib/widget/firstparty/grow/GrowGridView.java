/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.preference.PreferenceManager
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.MotionEvent
 *  android.view.ScaleGestureDetector
 *  android.view.ScaleGestureDetector$OnScaleGestureListener
 *  android.view.ScaleGestureDetector$SimpleOnScaleGestureListener
 *  android.view.View
 *  android.view.ViewPropertyAnimator
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemLongClickListener
 *  android.widget.GridView
 *  android.widget.ImageView
 *  android.widget.ListAdapter
 */
package com.crumby.lib.widget.firstparty.grow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.CrDb;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryListFragment;
import com.crumby.lib.fragment.ImageClickListener;
import com.crumby.lib.fragment.adapter.GalleryListAdapter;
import com.crumby.lib.fragment.adapter.GridImageWrapper;
import com.crumby.lib.widget.firstparty.ScreenCapture;
import com.crumby.lib.widget.firstparty.grow.ColumnCounter;
import com.crumby.lib.widget.thirdparty.HeaderGridView;
import com.squareup.picasso.Picasso;
import java.lang.reflect.Field;

public class GrowGridView
extends HeaderGridView {
    private static int viewGridWidth;
    private volatile boolean canScale = true;
    private ColumnCounter columnCount;
    private int currentWidth;
    private int focus;
    private GalleryImage hostImage;
    private ScaleGestureDetector mScaleDetector;
    private ScreenCapture screenCapture;

    public GrowGridView(Context context) {
        super(context);
    }

    public GrowGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GrowGridView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    private boolean alterColumnCount(int n2) {
        int n3 = this.getColumns(n2);
        this.columnCount.set(n3);
        if (GrowGridView.hasReachedLimit(n2, n3)) {
            n2 = Math.min(this.getScreenWidth() - this.getRequestedHorizontalSpacing(), Math.max(n2, GalleryListFragment.MINIMUM_THUMBNAIL_WIDTH));
            this.columnCount.set(this.getColumns(n2));
            this.columnCount.indicateLimit();
            return false;
        }
        this.columnCount.indicateFree();
        return true;
    }

    private void animateGridScaleEnd(float f2, int n2) {
        this.setScaleX(f2);
        this.setScaleY(f2);
        int n3 = this.getChildCount();
        for (int i2 = 0; i2 < n3; ++i2) {
            this.getChildAt(i2).setAlpha(1.0f);
        }
        this.currentWidth = n2;
        this.setColumnWidth(this.currentWidth);
        this.setVisibility(0);
        this.screenCapture.hide();
        this.animate().setDuration(750).scaleX(1.0f).scaleY(1.0f).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator) {
                GrowGridView.this.canScale = true;
                GrowGridView.this.setVisibility(0);
                GrowGridView.this.screenCapture.hide();
            }
        }).start();
    }

    private void endScaling(float f2, boolean bl2) {
        int n2 = (int)((float)this.currentWidth * f2 * f2);
        int n3 = this.getColumns(n2);
        Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_GRID, "resize", "" + n3 + "");
        this.setEnabled(true);
        this.columnCount.hide();
        if (bl2) {
            this.animateGridScaleEnd(f2, n2);
        }
        this.scrollToCenter(this.focus);
        ((ImageClickListener)this.getOnItemLongClickListener()).enable();
        this.unpause();
    }

    private View getCenterWrapper() {
        View view = this.getChildAt(0);
        int n2 = this.getWidth() / 2;
        int n3 = this.getHeight() / 2;
        CrDb.d("grow grid view", "grid center height & width: " + n3 + " " + n2);
        Rect rect = new Rect(n2 - 50, n3 - 50, n2 + 50, n3 + 50);
        n3 = this.getChildCount();
        for (n2 = 0; n2 < n3; ++n2) {
            Rect rect2 = new Rect();
            View view2 = this.getChildAt(n2);
            view2.getHitRect(rect2);
            view2.clearAnimation();
            view2.setPressed(false);
            if (view2.getBackground() != null) {
                view2.setBackgroundDrawable(this.getResources().getDrawable(2130837656));
                view2.getBackground().invalidateSelf();
            }
            view2.invalidate();
            if (rect2.intersect(rect)) {
                CrDb.d("grow grid view", "contained at " + n2);
                view = view2;
            }
            view2.setAlpha(0.2f);
        }
        view.setPressed(true);
        view.setAlpha(1.0f);
        return view;
    }

    private int getColumns(int n2) {
        n2 = Math.max(n2, GalleryListFragment.MINIMUM_THUMBNAIL_WIDTH);
        return this.getScreenWidth() / (this.getRequestedHorizontalSpacing() + n2);
    }

    private GalleryListAdapter getGalleryAdapter() {
        return (GalleryListAdapter)((HeaderGridView.HeaderViewGridAdapter)this.getAdapter()).getWrappedAdapter();
    }

    private int getScreenHeight() {
        return this.getResources().getDisplayMetrics().heightPixels;
    }

    public static boolean hasReachedLimit(int n2, int n3) {
        if (n2 < GalleryListFragment.MINIMUM_THUMBNAIL_WIDTH || n3 < 1) {
            return true;
        }
        return false;
    }

    private void pause() {
        int n2 = this.getChildCount();
        this.getGalleryAdapter().pause();
        for (int i2 = 0; i2 < n2; ++i2) {
            if (this.getChildAt(i2).getTag() == null) continue;
            GridImageWrapper gridImageWrapper = (GridImageWrapper)this.getChildAt(i2).getTag();
            Picasso.with(this.getContext()).cancelRequest(gridImageWrapper.getImageView());
        }
    }

    private boolean scaleEvent(float f2) {
        float f3 = f2 * f2;
        int n2 = (int)((float)this.currentWidth * f3);
        CrDb.d("grow grid view", "mScaleFactor: " + f3);
        if (!this.alterColumnCount(n2)) {
            return false;
        }
        this.screenCapture.scale(f2);
        return true;
    }

    private void scrollToCenter(final int n2) {
        CrDb.d("grow grid view", "focus position 2: " + n2 + " num columns: " + this.getNumColumns() + " current width: " + this.getCurrentWidth() + " screen width:" + this.getScreenWidth());
        n2 = this.getGalleryAdapter().prepareHighlight(n2);
        CrDb.d("grow grid view", "grid position:" + n2);
        Handler handler = new Handler();
        this.setEnabled(false);
        this.setSelection(n2);
        handler.postDelayed(new Runnable(){

            @Override
            public void run() {
                GrowGridView.this.setEnabled(true);
                GrowGridView.this.setSelection(n2);
            }
        }, 1000);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void startScaling() {
        View view = this.getCenterWrapper();
        int n2 = 0;
        try {
            int n3;
            n2 = n3 = ((GridImageWrapper)view.getTag()).getImage().getPosition();
        }
        catch (NullPointerException var3_2) {
            var3_2.printStackTrace();
        }
        this.startScaling(n2);
    }

    private void unpause() {
        this.getGalleryAdapter().unpause();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void checkForPersistence() {
        if (this.getContext() == null || !PreferenceManager.getDefaultSharedPreferences((Context)this.getContext()).getBoolean("crumbyPersistGridColumns", true) || this.currentWidth == viewGridWidth) {
            return;
        }
        this.setColumnWidth(viewGridWidth);
    }

    @SuppressLint(value={"NewApi"})
    public int getColumnWidth() {
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getColumnWidth();
        }
        try {
            Field field = GridView.class.getDeclaredField("mColumnWidth");
            field.setAccessible(true);
            Integer n2 = (Integer)field.get((Object)this);
            field.setAccessible(false);
            int n3 = n2;
            return n3;
        }
        catch (NoSuchFieldException var2_2) {
            throw new RuntimeException(var2_2);
        }
        catch (IllegalAccessException var2_3) {
            throw new RuntimeException(var2_3);
        }
    }

    public int getCurrentColumns() {
        return this.getColumns(this.currentWidth);
    }

    public int getCurrentWidth() {
        return this.currentWidth;
    }

    public int getRequestedHorizontalSpacing() {
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getRequestedHorizontalSpacing();
        }
        try {
            Field field = this.getClass().getDeclaredField("mRequestedHorizontalSpacing");
            field.setAccessible(true);
            int n2 = field.getInt((Object)this);
            return n2;
        }
        catch (NoSuchFieldException var2_2) {
            return 0;
        }
        catch (IllegalAccessException var2_3) {
            return 0;
        }
    }

    public int getScreenWidth() {
        return this.getResources().getDisplayMetrics().widthPixels;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void initialize(ImageView imageView, ColumnCounter columnCounter, GalleryImage galleryImage) {
        this.columnCount = columnCounter;
        this.screenCapture = new ScreenCapture((View)this, imageView);
        this.hostImage = galleryImage;
        this.currentWidth = PreferenceManager.getDefaultSharedPreferences((Context)this.getContext()).getBoolean("crumbyPersistGridColumns", true) ? viewGridWidth : galleryImage.getViewGridWidth();
        if (this.currentWidth == 0) {
            this.currentWidth = Math.max(this.getScreenWidth() / 5, GalleryListFragment.MINIMUM_THUMBNAIL_WIDTH);
        }
        this.setColumnWidth(this.currentWidth);
    }

    protected void onDetachedFromWindow() {
        CrDb.d("grow grid view", "detached!");
        if (this.screenCapture != null) {
            this.screenCapture.recycle();
            this.screenCapture = null;
        }
        super.onDetachedFromWindow();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mScaleDetector = new ScaleGestureDetector(this.getContext(), (ScaleGestureDetector.OnScaleGestureListener)new ScaleListener());
    }

    protected void onSizeChanged(int n2, int n3, int n4, int n5) {
        super.onSizeChanged(n2, n3, n4, n5);
        if (this.screenCapture != null) {
            this.screenCapture.setupScreenShot();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mScaleDetector.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

    public void setColumnWidth(int n2) {
        this.hostImage.setViewGridWidth(n2);
        viewGridWidth = n2;
        super.setColumnWidth(n2);
    }

    public void startScaling(int n2) {
        ((ImageClickListener)this.getOnItemLongClickListener()).disable();
        CrDb.d("grow grid view", "focus position 1: " + n2);
        this.focus = n2;
        this.columnCount.show();
        this.pause();
        this.canScale = false;
        this.screenCapture.resetScreenshot();
        this.currentWidth = this.getColumnWidth() - 10;
        this.scaleEvent(1.0f);
        this.setEnabled(false);
    }

    public void zoom(float f2, float f3, float f4, final boolean bl2) {
        CrDb.d("grow grid view", "real zoom " + this.getX() + " " + this.getY());
        int n2 = (int)((float)this.currentWidth * f4);
        n2 = this.getScreenWidth() / n2;
        this.columnCount.set(n2);
        CrDb.d("grow grid view", "to zoom " + f2 + " " + f3);
        if (bl2) {
            this.currentWidth = (int)((float)this.currentWidth * f4);
            this.setColumnWidth(this.currentWidth);
        }
        ImageView imageView = this.screenCapture.getScreenShot();
        imageView.animate().scaleX(f4).scaleY(f4).x(f2).y(f3).setDuration(500).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter((View)imageView){
            final /* synthetic */ View val$screenShot;

            /*
             * Enabled aggressive block sorting
             */
            public void onAnimationEnd(Animator object) {
                object = GrowGridView.this;
                boolean bl22 = !bl2;
                ((GrowGridView)((Object)object)).endScaling(1.0f, bl22);
                this.val$screenShot.setX(0.0f);
                this.val$screenShot.setY(0.0f);
                this.val$screenShot.setAlpha(0.5f);
                this.val$screenShot.setVisibility(8);
                if (bl2) {
                    GrowGridView.this.canScale = true;
                    GrowGridView.this.setVisibility(0);
                }
            }
        });
    }

    public void zoomIntoSequence(View view, int n2) {
        int[] arrn = new int[2];
        view.getLocationOnScreen(arrn);
        CrDb.d("grow grid view", "zoom x:" + arrn[0] + " zoom y:" + arrn[1]);
        for (int i2 = this.getChildCount() - 1; i2 >= 0; --i2) {
            if (this.getChildAt(i2) == view) continue;
            this.getChildAt(i2).setAlpha(0.0f);
        }
        this.startScaling(n2);
        float f2 = this.getScreenWidth() / this.currentWidth;
        float f3 = (float)this.getWidth() * Math.abs(1.0f - f2) / 2.0f;
        float f4 = (float)this.getHeight() * Math.abs(1.0f - f2) / 2.0f;
        float f5 = arrn[1];
        this.screenCapture.resetAlpha();
        CrDb.d("grow grid view", "factor: " + f2 + " endx: " + (f3 -= (float)arrn[0] * f2));
        this.zoom(f3, f4 - (f5 * f2 - 50.0f * f2), f2, true);
    }

    public void zoomOutOfCenter() {
        this.startScaling();
        float f2 = (float)GalleryGridFragment.MINIMUM_THUMBNAIL_WIDTH / (float)this.currentWidth;
        CrDb.d("grow grid view", "zoom " + f2);
        this.zoom(0.0f, 0.0f, f2, false);
    }

    private class ScaleListener
    extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private float mScaleFactor;

        private ScaleListener() {
            this.mScaleFactor = 1.0f;
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float f2 = this.mScaleFactor * scaleGestureDetector.getScaleFactor();
            if (GrowGridView.this.scaleEvent(f2)) {
                this.mScaleFactor = f2;
                return true;
            }
            return false;
        }

        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            if (!GrowGridView.this.canScale) {
                return false;
            }
            GrowGridView.this.startScaling();
            this.mScaleFactor = 1.0f;
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            GrowGridView.this.endScaling(this.mScaleFactor, true);
        }
    }

}

