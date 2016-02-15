package com.crumby.lib.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.C0065R;
import com.crumby.CrDb;
import com.crumby.CrumbyApp;
import com.crumby.lib.widget.firstparty.GalleryImageTutorial;
import com.crumby.lib.widget.firstparty.ScreenCapture;
import com.crumby.lib.widget.thirdparty.ObservableScrollView;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import uk.co.senab.photoview.ExpandContainerScaleListener;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageScrollView extends ObservableScrollView implements ExpandContainerScaleListener {
    public static boolean userWantsFullScreen;
    private boolean animating;
    int imageHeight;
    private View imageView;
    private View imageViewContainer;
    private boolean inFullScreen;
    boolean measuredHeightIsDeadWrong;
    private View metadata;
    private boolean omniSearchHackActive;
    private OnImageScaleListener onImageScaleListener;
    private View revertFromFullScreen;
    private float savedHeight;
    private float savedTopY;
    private ScreenCapture screenCapture;
    private TextView title;
    private GalleryImageTutorial tutorial;

    /* renamed from: com.crumby.lib.widget.ImageScrollView.1 */
    class C01181 implements Runnable {
        C01181() {
        }

        public void run() {
            int height = ImageScrollView.this.imageHeight;
            ImageScrollView.this.imageHeight = 0;
            ImageScrollView.this.render(height);
        }
    }

    /* renamed from: com.crumby.lib.widget.ImageScrollView.2 */
    class C01192 implements OnClickListener {
        C01192() {
        }

        public void onClick(View v) {
            Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "revert from full screen", null);
            ImageScrollView.this.contract();
        }
    }

    /* renamed from: com.crumby.lib.widget.ImageScrollView.3 */
    class C01203 implements AnimationListener {
        final /* synthetic */ float val$y;

        C01203(float f) {
            this.val$y = f;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            if (this.val$y == ImageScrollView.this.savedHeight) {
                ImageScrollView.this.metadata.setVisibility(0);
            } else {
                ImageScrollView.this.metadata.setVisibility(8);
            }
            ImageScrollView.this.animating = false;
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    public ImageScrollView(Context context) {
        super(context);
    }

    public ImageScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean expand() {
        return expand(true);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.measuredHeightIsDeadWrong) {
            postDelayed(new C01181(), 0);
            this.measuredHeightIsDeadWrong = false;
        }
        if (this.omniSearchHackActive && w != oldw) {
            omniSearchHackReset();
        }
    }

    private void omniSearchHackReset() {
        this.omniSearchHackActive = false;
        this.metadata.setMinimumHeight(0);
        this.screenCapture.setPadding(0, 0, 0, 0);
        this.screenCapture.hide();
        findViewById(C0065R.id.image_view_metadata_container).setVisibility(0);
    }

    public boolean isInFullScreen() {
        return this.inFullScreen;
    }

    public boolean expand(boolean animate) {
        if (!(this.inFullScreen || this.animating)) {
            if (this.onImageScaleListener != null) {
                this.onImageScaleListener.onExpand();
            }
            int fullScreen = getMeasuredHeight();
            if (fullScreen != 0) {
                if (this.tutorial != null) {
                    this.tutorial.clearTutorial();
                }
                View top = findViewById(C0065R.id.image_view_page_top);
                top.clearAnimation();
                if (this.savedTopY == 0.0f) {
                    this.savedTopY = top.getY();
                }
                if (animate) {
                    smoothScrollTo(0, 0);
                    startAnimation(this.imageViewContainer, ((float) fullScreen) + CrumbyApp.convertDpToPx(getResources(), BitmapDescriptorFactory.HUE_ORANGE));
                    top.animate().y(0.0f);
                } else {
                    forceHeight(this.imageViewContainer, fullScreen);
                    this.imageViewContainer.setPadding(this.imageViewContainer.getPaddingLeft(), 0, this.imageViewContainer.getPaddingRight(), this.imageViewContainer.getPaddingBottom());
                    this.metadata.setVisibility(8);
                    top.setY(0.0f);
                }
                this.revertFromFullScreen.setVisibility(0);
                this.inFullScreen = true;
                userWantsFullScreen = true;
                this.title.setSingleLine(false);
                return true;
            }
            this.measuredHeightIsDeadWrong = true;
        }
        return false;
    }

    public boolean contract() {
        return contract(true);
    }

    private void forceHeight(View view, int height) {
        if (view.getLayoutParams().height != height) {
            view.getLayoutParams().height = height;
        }
    }

    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.measuredHeightIsDeadWrong = true;
    }

    public void setTutorial(GalleryImageTutorial tutorial) {
        this.tutorial = tutorial;
    }

    public boolean contract(boolean animate) {
        if (!this.inFullScreen || this.animating) {
            return false;
        }
        View top = findViewById(C0065R.id.image_view_page_top);
        top.clearAnimation();
        if (animate) {
            startAnimation(this.imageViewContainer, this.savedHeight);
            if (this.savedTopY != 0.0f) {
                top.animate().y(this.savedTopY);
            }
        } else {
            forceHeight(this.imageViewContainer, (int) this.savedHeight);
            this.imageViewContainer.setPadding(this.imageViewContainer.getPaddingLeft(), (int) getResources().getDimension(C0065R.dimen.photo_view_container_top), this.imageViewContainer.getPaddingRight(), this.imageViewContainer.getPaddingBottom());
            if (this.savedTopY != 0.0f) {
                top.setY(this.savedTopY);
            }
        }
        this.metadata.setVisibility(0);
        this.revertFromFullScreen.setVisibility(8);
        this.inFullScreen = false;
        userWantsFullScreen = false;
        this.title.setSingleLine(true);
        if (this.onImageScaleListener == null) {
            return true;
        }
        this.onImageScaleListener.onContract();
        return true;
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.imageView = findViewById(C0065R.id.photo_view);
        ((PhotoViewAttacher) ((PhotoView) this.imageView).getIPhotoViewImplementation()).setExpandContainerScaleListener(this);
        this.imageViewContainer = findViewById(C0065R.id.photo_view_container);
        this.metadata = findViewById(C0065R.id.image_view_metadata);
        this.revertFromFullScreen = findViewById(C0065R.id.revert_image_view);
        this.revertFromFullScreen.setOnClickListener(new C01192());
        this.imageViewContainer.setClickable(true);
        this.screenCapture = new ScreenCapture(findViewById(C0065R.id.image_view_metadata_container), (ImageView) findViewById(C0065R.id.omni_search_glitch_fix));
        this.title = (TextView) findViewById(C0065R.id.photo_title);
        this.savedHeight = getResources().getDimension(C0065R.dimen.minimum_image_view_height);
        if (userWantsFullScreen) {
            expand(false);
        }
    }

    private void startAnimation(View view, float y) {
        PhotoViewContainerResize animation = new PhotoViewContainerResize(view, (int) y);
        this.animating = true;
        animation.setAnimationListener(new C01203(y));
        animation.setInterpolator(getContext(), 17563654);
        animation.setDuration(300);
        view.startAnimation(animation);
    }

    public void render(int defaultHeight) {
        if (defaultHeight != this.imageHeight || userWantsFullScreen != this.inFullScreen) {
            this.imageHeight = defaultHeight;
            float atMostSeventyPercentHeight = ((float) getResources().getDisplayMetrics().heightPixels) * 0.7f;
            this.savedHeight = (float) ((int) Math.min(atMostSeventyPercentHeight, Math.max(getResources().getDimension(C0065R.dimen.minimum_image_view_height), Math.min((float) this.imageHeight, atMostSeventyPercentHeight))));
            if (userWantsFullScreen) {
                this.inFullScreen = false;
                expand(false);
                return;
            }
            this.inFullScreen = true;
            contract(false);
        }
    }

    public void toggle() {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "toggle full screen", null);
        if (this.inFullScreen) {
            contract();
        } else {
            expand();
        }
    }

    public int getLastChildBottom() {
        return getChildAt(getChildCount() - 1).getBottom();
    }

    public int getDistanceFromBottom() {
        return getLastChildBottom();
    }

    public void omniSearchIsShowingHack(boolean captureScreen) {
        if (!this.inFullScreen && !this.omniSearchHackActive) {
            this.omniSearchHackActive = true;
            this.metadata.setMinimumHeight(this.metadata.getMeasuredHeight());
            View container = findViewById(C0065R.id.image_view_metadata_container);
            int top = getScrollY() - this.metadata.getTop();
            CrDb.m0d("image scroll view", getScrollY() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.metadata.getTop());
            int bottom = top + Math.min(container.getHeight(), getResources().getDisplayMetrics().heightPixels);
            if (top > 0) {
                this.screenCapture.setPadding(0, top, 0, 0);
            }
            if (captureScreen) {
                this.screenCapture.captureAndShowScreenAsync(top, bottom);
            }
            findViewById(C0065R.id.image_view_metadata_container).setVisibility(8);
        }
    }

    public void omniSearchIsNotShowingHack() {
        if (!this.inFullScreen && this.omniSearchHackActive) {
            omniSearchHackReset();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.onImageScaleListener = null;
        CrDb.m0d("image scroll view", "detached!");
        if (this.screenCapture != null) {
            this.screenCapture.recycle();
            this.screenCapture = null;
        }
    }

    public void setOnImageScaleListener(OnImageScaleListener listener) {
        this.onImageScaleListener = listener;
    }
}
