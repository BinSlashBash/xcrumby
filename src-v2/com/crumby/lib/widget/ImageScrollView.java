/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewPropertyAnimator
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.crumby.lib.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import com.crumby.Analytics;
import com.crumby.AnalyticsCategories;
import com.crumby.CrDb;
import com.crumby.CrumbyApp;
import com.crumby.lib.widget.OnImageScaleListener;
import com.crumby.lib.widget.PhotoViewContainerResize;
import com.crumby.lib.widget.firstparty.GalleryImageTutorial;
import com.crumby.lib.widget.firstparty.ScreenCapture;
import com.crumby.lib.widget.thirdparty.ObservableScrollView;
import uk.co.senab.photoview.ExpandContainerScaleListener;
import uk.co.senab.photoview.IPhotoView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageScrollView
extends ObservableScrollView
implements ExpandContainerScaleListener {
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

    public ImageScrollView(Context context) {
        super(context);
    }

    public ImageScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ImageScrollView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    private void forceHeight(View view, int n2) {
        if (view.getLayoutParams().height == n2) {
            return;
        }
        view.getLayoutParams().height = n2;
    }

    private void omniSearchHackReset() {
        this.omniSearchHackActive = false;
        this.metadata.setMinimumHeight(0);
        this.screenCapture.setPadding(0, 0, 0, 0);
        this.screenCapture.hide();
        this.findViewById(2131493035).setVisibility(0);
    }

    private void startAnimation(View view, final float f2) {
        PhotoViewContainerResize photoViewContainerResize = new PhotoViewContainerResize(view, (int)f2);
        this.animating = true;
        photoViewContainerResize.setAnimationListener(new Animation.AnimationListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onAnimationEnd(Animation animation) {
                if (f2 == ImageScrollView.this.savedHeight) {
                    ImageScrollView.this.metadata.setVisibility(0);
                } else {
                    ImageScrollView.this.metadata.setVisibility(8);
                }
                ImageScrollView.this.animating = false;
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
        photoViewContainerResize.setInterpolator(this.getContext(), 17563654);
        photoViewContainerResize.setDuration(300);
        view.startAnimation((Animation)photoViewContainerResize);
    }

    @Override
    public boolean contract() {
        return this.contract(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean contract(boolean bl2) {
        if (this.inFullScreen && !this.animating) {
            View view = this.findViewById(2131493039);
            view.clearAnimation();
            if (bl2) {
                this.startAnimation(this.imageViewContainer, this.savedHeight);
                if (this.savedTopY != 0.0f) {
                    view.animate().y(this.savedTopY);
                }
            } else {
                this.forceHeight(this.imageViewContainer, (int)this.savedHeight);
                this.imageViewContainer.setPadding(this.imageViewContainer.getPaddingLeft(), (int)this.getResources().getDimension(2131165196), this.imageViewContainer.getPaddingRight(), this.imageViewContainer.getPaddingBottom());
                if (this.savedTopY != 0.0f) {
                    view.setY(this.savedTopY);
                }
            }
            this.metadata.setVisibility(0);
            this.revertFromFullScreen.setVisibility(8);
            this.inFullScreen = false;
            userWantsFullScreen = false;
            this.title.setSingleLine(true);
            if (this.onImageScaleListener != null) {
                this.onImageScaleListener.onContract();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean expand() {
        return this.expand(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean expand(boolean bl2) {
        if (!this.inFullScreen && !this.animating) {
            int n2;
            if (this.onImageScaleListener != null) {
                this.onImageScaleListener.onExpand();
            }
            if ((n2 = this.getMeasuredHeight()) != 0) {
                if (this.tutorial != null) {
                    this.tutorial.clearTutorial();
                }
                View view = this.findViewById(2131493039);
                view.clearAnimation();
                if (this.savedTopY == 0.0f) {
                    this.savedTopY = view.getY();
                }
                if (bl2) {
                    this.smoothScrollTo(0, 0);
                    this.startAnimation(this.imageViewContainer, (float)n2 + CrumbyApp.convertDpToPx(this.getResources(), 30.0f));
                    view.animate().y(0.0f);
                } else {
                    this.forceHeight(this.imageViewContainer, n2);
                    this.imageViewContainer.setPadding(this.imageViewContainer.getPaddingLeft(), 0, this.imageViewContainer.getPaddingRight(), this.imageViewContainer.getPaddingBottom());
                    this.metadata.setVisibility(8);
                    view.setY(0.0f);
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

    public int getDistanceFromBottom() {
        return this.getLastChildBottom();
    }

    public int getLastChildBottom() {
        return this.getChildAt(this.getChildCount() - 1).getBottom();
    }

    public boolean isInFullScreen() {
        return this.inFullScreen;
    }

    public void omniSearchIsNotShowingHack() {
        if (!this.inFullScreen && this.omniSearchHackActive) {
            this.omniSearchHackReset();
        }
    }

    public void omniSearchIsShowingHack(boolean bl2) {
        if (!this.inFullScreen && !this.omniSearchHackActive) {
            this.omniSearchHackActive = true;
            this.metadata.setMinimumHeight(this.metadata.getMeasuredHeight());
            View view = this.findViewById(2131493035);
            int n2 = this.getScrollY() - this.metadata.getTop();
            CrDb.d("image scroll view", "" + this.getScrollY() + " " + this.metadata.getTop());
            int n3 = Math.min(view.getHeight(), this.getResources().getDisplayMetrics().heightPixels);
            if (n2 > 0) {
                this.screenCapture.setPadding(0, n2, 0, 0);
            }
            if (bl2) {
                this.screenCapture.captureAndShowScreenAsync(n2, n2 + n3);
            }
            this.findViewById(2131493035).setVisibility(8);
        }
    }

    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.measuredHeightIsDeadWrong = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.onImageScaleListener = null;
        CrDb.d("image scroll view", "detached!");
        if (this.screenCapture != null) {
            this.screenCapture.recycle();
            this.screenCapture = null;
        }
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        this.imageView = this.findViewById(2131493031);
        ((PhotoViewAttacher)((PhotoView)this.imageView).getIPhotoViewImplementation()).setExpandContainerScaleListener(this);
        this.imageViewContainer = this.findViewById(2131493026);
        this.metadata = this.findViewById(2131493033);
        this.revertFromFullScreen = this.findViewById(2131493032);
        this.revertFromFullScreen.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "revert from full screen", null);
                ImageScrollView.this.contract();
            }
        });
        this.imageViewContainer.setClickable(true);
        this.screenCapture = new ScreenCapture(this.findViewById(2131493035), (ImageView)this.findViewById(2131493037));
        this.title = (TextView)this.findViewById(2131493040);
        this.savedHeight = this.getResources().getDimension(2131165193);
        if (userWantsFullScreen) {
            this.expand(false);
        }
    }

    protected void onSizeChanged(int n2, int n3, int n4, int n5) {
        super.onSizeChanged(n2, n3, n4, n5);
        if (this.measuredHeightIsDeadWrong) {
            this.postDelayed(new Runnable(){

                @Override
                public void run() {
                    int n2 = ImageScrollView.this.imageHeight;
                    ImageScrollView.this.imageHeight = 0;
                    ImageScrollView.this.render(n2);
                }
            }, 0);
            this.measuredHeightIsDeadWrong = false;
        }
        if (this.omniSearchHackActive && n2 != n4) {
            this.omniSearchHackReset();
        }
    }

    public void render(int n2) {
        if (n2 == this.imageHeight && userWantsFullScreen == this.inFullScreen) {
            return;
        }
        this.imageHeight = n2;
        float f2 = (float)this.getResources().getDisplayMetrics().heightPixels * 0.7f;
        this.savedHeight = (int)Math.min(f2, Math.max(this.getResources().getDimension(2131165193), Math.min((float)this.imageHeight, f2)));
        if (userWantsFullScreen) {
            this.inFullScreen = false;
            this.expand(false);
            return;
        }
        this.inFullScreen = true;
        this.contract(false);
    }

    public void setOnImageScaleListener(OnImageScaleListener onImageScaleListener) {
        this.onImageScaleListener = onImageScaleListener;
    }

    public void setTutorial(GalleryImageTutorial galleryImageTutorial) {
        this.tutorial = galleryImageTutorial;
    }

    public void toggle() {
        Analytics.INSTANCE.newEvent(AnalyticsCategories.GALLERY_IMAGE, "toggle full screen", null);
        if (this.inFullScreen) {
            this.contract();
            return;
        }
        this.expand();
    }

}

