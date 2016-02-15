package com.crumby.lib.widget.firstparty.grow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.GalleryViewer;
import com.crumby.impl.crumby.UnsupportedUrlFragment;
import com.crumby.lib.GalleryImage;
import com.crumby.lib.fragment.GalleryGridFragment;
import com.crumby.lib.fragment.GalleryListFragment;
import com.crumby.lib.fragment.ImageClickListener;
import com.crumby.lib.fragment.adapter.GalleryListAdapter;
import com.crumby.lib.fragment.adapter.GridImageWrapper;
import com.crumby.lib.widget.firstparty.ScreenCapture;
import com.crumby.lib.widget.firstparty.main_menu.MainMenu;
import com.crumby.lib.widget.thirdparty.HeaderGridView;
import com.crumby.lib.widget.thirdparty.HeaderGridView.HeaderViewGridAdapter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.squareup.picasso.Picasso;
import java.lang.reflect.Field;

public class GrowGridView extends HeaderGridView {
    private static int viewGridWidth;
    private volatile boolean canScale;
    private ColumnCounter columnCount;
    private int currentWidth;
    private int focus;
    private GalleryImage hostImage;
    private ScaleGestureDetector mScaleDetector;
    private ScreenCapture screenCapture;

    /* renamed from: com.crumby.lib.widget.firstparty.grow.GrowGridView.1 */
    class C01321 extends AnimatorListenerAdapter {
        C01321() {
        }

        public void onAnimationEnd(Animator animation) {
            GrowGridView.this.canScale = true;
            GrowGridView.this.setVisibility(0);
            GrowGridView.this.screenCapture.hide();
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.grow.GrowGridView.2 */
    class C01332 implements Runnable {
        final /* synthetic */ int val$gridPosition;

        C01332(int i) {
            this.val$gridPosition = i;
        }

        public void run() {
            GrowGridView.this.setEnabled(true);
            GrowGridView.this.setSelection(this.val$gridPosition);
        }
    }

    /* renamed from: com.crumby.lib.widget.firstparty.grow.GrowGridView.3 */
    class C01343 extends AnimatorListenerAdapter {
        final /* synthetic */ boolean val$preemptEndAnimation;
        final /* synthetic */ View val$screenShot;

        C01343(boolean z, View view) {
            this.val$preemptEndAnimation = z;
            this.val$screenShot = view;
        }

        public void onAnimationEnd(Animator animation) {
            GrowGridView.this.endScaling(GalleryViewer.PROGRESS_COMPLETED, !this.val$preemptEndAnimation);
            this.val$screenShot.setX(0.0f);
            this.val$screenShot.setY(0.0f);
            this.val$screenShot.setAlpha(0.5f);
            this.val$screenShot.setVisibility(8);
            if (this.val$preemptEndAnimation) {
                GrowGridView.this.canScale = true;
                GrowGridView.this.setVisibility(0);
            }
        }
    }

    private class ScaleListener extends SimpleOnScaleGestureListener {
        private float mScaleFactor;

        private ScaleListener() {
            this.mScaleFactor = GalleryViewer.PROGRESS_COMPLETED;
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            if (!GrowGridView.this.canScale) {
                return false;
            }
            GrowGridView.this.startScaling();
            this.mScaleFactor = GalleryViewer.PROGRESS_COMPLETED;
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
            GrowGridView.this.endScaling(this.mScaleFactor, true);
        }

        public boolean onScale(ScaleGestureDetector detector) {
            float mScaleFactorCur = this.mScaleFactor * detector.getScaleFactor();
            if (!GrowGridView.this.scaleEvent(mScaleFactorCur)) {
                return false;
            }
            this.mScaleFactor = mScaleFactorCur;
            return true;
        }
    }

    public GrowGridView(Context context) {
        super(context);
        this.canScale = true;
    }

    public GrowGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.canScale = true;
    }

    public GrowGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.canScale = true;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }

    public boolean onTouchEvent(MotionEvent ev) {
        this.mScaleDetector.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    public int getCurrentWidth() {
        return this.currentWidth;
    }

    private View getCenterWrapper() {
        View center = getChildAt(0);
        int gridCenterWidth = getWidth() / 2;
        int gridCenterHeight = getHeight() / 2;
        CrDb.m0d("grow grid view", "grid center height & width: " + gridCenterHeight + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + gridCenterWidth);
        Rect click = new Rect(gridCenterWidth - 50, gridCenterHeight - 50, gridCenterWidth + 50, gridCenterHeight + 50);
        int size = getChildCount();
        for (int i = 0; i < size; i++) {
            Rect rect = new Rect();
            View tempView = getChildAt(i);
            tempView.getHitRect(rect);
            tempView.clearAnimation();
            tempView.setPressed(false);
            if (tempView.getBackground() != null) {
                tempView.setBackgroundDrawable(getResources().getDrawable(C0065R.drawable.image_view_wrapper_bg));
                tempView.getBackground().invalidateSelf();
            }
            tempView.invalidate();
            if (rect.intersect(click)) {
                CrDb.m0d("grow grid view", "contained at " + i);
                center = tempView;
            }
            tempView.setAlpha(0.2f);
        }
        center.setPressed(true);
        center.setAlpha(GalleryViewer.PROGRESS_COMPLETED);
        return center;
    }

    private void pause() {
        int childCount = getChildCount();
        getGalleryAdapter().pause();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i).getTag() != null) {
                Picasso.with(getContext()).cancelRequest(((GridImageWrapper) getChildAt(i).getTag()).getImageView());
            }
        }
    }

    public void startScaling(int focus) {
        ((ImageClickListener) getOnItemLongClickListener()).disable();
        CrDb.m0d("grow grid view", "focus position 1: " + focus);
        this.focus = focus;
        this.columnCount.show();
        pause();
        this.canScale = false;
        this.screenCapture.resetScreenshot();
        this.currentWidth = getColumnWidth() - 10;
        scaleEvent(GalleryViewer.PROGRESS_COMPLETED);
        setEnabled(false);
    }

    @SuppressLint({"NewApi"})
    public int getColumnWidth() {
        if (VERSION.SDK_INT >= 16) {
            return super.getColumnWidth();
        }
        try {
            Field field = GridView.class.getDeclaredField("mColumnWidth");
            field.setAccessible(true);
            Integer value = (Integer) field.get(this);
            field.setAccessible(false);
            return value.intValue();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }

    private void animateGridScaleEnd(float mScaleFactor, int endWidth) {
        setScaleX(mScaleFactor);
        setScaleY(mScaleFactor);
        int size = getChildCount();
        for (int i = 0; i < size; i++) {
            getChildAt(i).setAlpha(GalleryViewer.PROGRESS_COMPLETED);
        }
        this.currentWidth = endWidth;
        setColumnWidth(this.currentWidth);
        setVisibility(0);
        this.screenCapture.hide();
        animate().setDuration(750).scaleX(GalleryViewer.PROGRESS_COMPLETED).scaleY(GalleryViewer.PROGRESS_COMPLETED).setListener(new C01321()).start();
    }

    private int getColumns(int columnWidth) {
        return getScreenWidth() / (getRequestedHorizontalSpacing() + Math.max(columnWidth, GalleryListFragment.MINIMUM_THUMBNAIL_WIDTH));
    }

    public int getRequestedHorizontalSpacing() {
        int i = 0;
        if (VERSION.SDK_INT >= 16) {
            return super.getRequestedHorizontalSpacing();
        }
        try {
            Field field = getClass().getDeclaredField("mRequestedHorizontalSpacing");
            field.setAccessible(true);
            return field.getInt(this);
        } catch (NoSuchFieldException e) {
            return i;
        } catch (IllegalAccessException e2) {
            return i;
        }
    }

    private GalleryListAdapter getGalleryAdapter() {
        return (GalleryListAdapter) ((HeaderViewGridAdapter) getAdapter()).getWrappedAdapter();
    }

    private void scrollToCenter(int position) {
        CrDb.m0d("grow grid view", "focus position 2: " + position + " num columns: " + getNumColumns() + " current width: " + getCurrentWidth() + " screen width:" + getScreenWidth());
        int gridPosition = getGalleryAdapter().prepareHighlight(position);
        CrDb.m0d("grow grid view", "grid position:" + gridPosition);
        Handler handler = new Handler();
        setEnabled(false);
        setSelection(gridPosition);
        handler.postDelayed(new C01332(gridPosition), 1000);
    }

    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    private void endScaling(float mScaleFactor, boolean animateGrid) {
        int endWidth = (int) ((((float) this.currentWidth) * mScaleFactor) * mScaleFactor);
        Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_GRID, "resize", getColumns(endWidth) + UnsupportedUrlFragment.DISPLAY_NAME);
        setEnabled(true);
        this.columnCount.hide();
        if (animateGrid) {
            animateGridScaleEnd(mScaleFactor, endWidth);
        }
        scrollToCenter(this.focus);
        ((ImageClickListener) getOnItemLongClickListener()).enable();
        unpause();
    }

    private void unpause() {
        getGalleryAdapter().unpause();
    }

    public static boolean hasReachedLimit(int width, int columns) {
        return width < GalleryListFragment.MINIMUM_THUMBNAIL_WIDTH || columns < 1;
    }

    private boolean alterColumnCount(int width) {
        int columns = getColumns(width);
        this.columnCount.set(columns);
        if (hasReachedLimit(width, columns)) {
            this.columnCount.set(getColumns(Math.min(getScreenWidth() - getRequestedHorizontalSpacing(), Math.max(width, GalleryListFragment.MINIMUM_THUMBNAIL_WIDTH))));
            this.columnCount.indicateLimit();
            return false;
        }
        this.columnCount.indicateFree();
        return true;
    }

    private boolean scaleEvent(float mScaleFactorCur) {
        float original = mScaleFactorCur;
        mScaleFactorCur *= mScaleFactorCur;
        int width = (int) (((float) this.currentWidth) * mScaleFactorCur);
        CrDb.m0d("grow grid view", "mScaleFactor: " + mScaleFactorCur);
        if (!alterColumnCount(width)) {
            return false;
        }
        this.screenCapture.scale(original);
        return true;
    }

    public void zoomIntoSequence(View view, int position) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        CrDb.m0d("grow grid view", "zoom x:" + location[0] + " zoom y:" + location[1]);
        for (int i = getChildCount() - 1; i >= 0; i--) {
            if (getChildAt(i) != view) {
                getChildAt(i).setAlpha(0.0f);
            }
        }
        startScaling(position);
        float factor = (float) (getScreenWidth() / this.currentWidth);
        float endx = ((((float) getWidth()) * Math.abs(GalleryViewer.PROGRESS_COMPLETED - factor)) / 2.0f) - (((float) location[0]) * factor);
        float endy = ((((float) getHeight()) * Math.abs(GalleryViewer.PROGRESS_COMPLETED - factor)) / 2.0f) - ((((float) location[1]) * factor) - (50.0f * factor));
        this.screenCapture.resetAlpha();
        CrDb.m0d("grow grid view", "factor: " + factor + " endx: " + endx);
        zoom(endx, endy, factor, true);
    }

    public void zoom(float endX, float endY, float factor, boolean preemptEndAnimation) {
        CrDb.m0d("grow grid view", "real zoom " + getX() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getY());
        this.columnCount.set(getScreenWidth() / ((int) (((float) this.currentWidth) * factor)));
        CrDb.m0d("grow grid view", "to zoom " + endX + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + endY);
        if (preemptEndAnimation) {
            this.currentWidth = (int) (((float) this.currentWidth) * factor);
            setColumnWidth(this.currentWidth);
        }
        View screenShot = this.screenCapture.getScreenShot();
        screenShot.animate().scaleX(factor).scaleY(factor).x(endX).y(endY).setDuration(500).setListener(new C01343(preemptEndAnimation, screenShot));
    }

    private void startScaling() {
        int position = 0;
        try {
            position = ((GridImageWrapper) getCenterWrapper().getTag()).getImage().getPosition();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        startScaling(position);
    }

    public void zoomOutOfCenter() {
        startScaling();
        float factor = ((float) GalleryGridFragment.MINIMUM_THUMBNAIL_WIDTH) / ((float) this.currentWidth);
        CrDb.m0d("grow grid view", "zoom " + factor);
        zoom(0.0f, 0.0f, factor, false);
    }

    public int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    public void initialize(ImageView screenShot, ColumnCounter columnCount, GalleryImage hostImage) {
        this.columnCount = columnCount;
        this.screenCapture = new ScreenCapture(this, screenShot);
        this.hostImage = hostImage;
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(MainMenu.PERSIST_GRID_COLUMNS, true)) {
            this.currentWidth = viewGridWidth;
        } else {
            this.currentWidth = hostImage.getViewGridWidth();
        }
        if (this.currentWidth == 0) {
            this.currentWidth = Math.max(getScreenWidth() / 5, GalleryListFragment.MINIMUM_THUMBNAIL_WIDTH);
        }
        setColumnWidth(this.currentWidth);
    }

    public void setColumnWidth(int columnWidth) {
        this.hostImage.setViewGridWidth(columnWidth);
        viewGridWidth = columnWidth;
        super.setColumnWidth(columnWidth);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.screenCapture != null) {
            this.screenCapture.setupScreenShot();
        }
    }

    public int getCurrentColumns() {
        return getColumns(this.currentWidth);
    }

    public void checkForPersistence() {
        if (getContext() != null && PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(MainMenu.PERSIST_GRID_COLUMNS, true) && this.currentWidth != viewGridWidth) {
            setColumnWidth(viewGridWidth);
        }
    }

    protected void onDetachedFromWindow() {
        CrDb.m0d("grow grid view", "detached!");
        if (this.screenCapture != null) {
            this.screenCapture.recycle();
            this.screenCapture = null;
        }
        super.onDetachedFromWindow();
    }
}
